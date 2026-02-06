package com.pyomin.cool.interceptor;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@Slf4j
@Component
public class RateLimitProvider {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    private final Map<String, Integer> violationCounts = new ConcurrentHashMap<>();
    private final Map<String, BanInfo> bannedIps = new ConcurrentHashMap<>();

    private static final int MAX_VIOLATIONS = 10;
    private static final int INITIAL_BAN_SECONDS = 60;
    private static final int BAN_MULTIPLIER = 2;
    private static final int MAX_BAN_SECONDS = 3600;

    public boolean tryConsume(String ip, int capacity, int seconds) {
        if (isBanned(ip)) {
            return false;
        }

        Bucket bucket = resolveBucket(ip, capacity, seconds);
        if (bucket.tryConsume(1)) {
            return true;
        }

        handleViolation(ip);
        return false;
    }

    private Bucket resolveBucket(String ip, int capacity, int seconds) {
        String key = ip + "-" + capacity + "-" + seconds;
        return buckets.computeIfAbsent(key, k -> createNewBucket(capacity, seconds));
    }

    private Bucket createNewBucket(int capacity, int seconds) {
        Refill refill = Refill.intervally(capacity, Duration.ofSeconds(seconds));
        Bandwidth limit = Bandwidth.classic(capacity, refill);

        return Bucket.builder().addLimit(limit).build();
    }

    private boolean isBanned(String ip) {
        BanInfo banInfo = bannedIps.get(ip);
        if (banInfo == null) {
            return false;
        }
        return System.currentTimeMillis() <= banInfo.banExpirationTime;
    }

    private void handleViolation(String ip) {
        int violations = violationCounts.merge(ip, 1, Integer::sum);

        if (violations >= MAX_VIOLATIONS) {
            banUser(ip);
            violationCounts.remove(ip);
        }
    }

    private void banUser(String ip) {
        BanInfo banInfo = bannedIps.computeIfAbsent(ip, k -> new BanInfo());

        long now = System.currentTimeMillis();

        int currentBanCount = banInfo.banCount;
        long banDurationSeconds = INITIAL_BAN_SECONDS * (long) Math.pow(BAN_MULTIPLIER, currentBanCount);

        if (banDurationSeconds > MAX_BAN_SECONDS) {
            banDurationSeconds = MAX_BAN_SECONDS;
        }

        banInfo.banExpirationTime = now + (banDurationSeconds * 1000);
        banInfo.banCount++;

        bannedIps.put(ip, banInfo);

        log.warn("User {} banned for {} seconds. (Count: {})", ip, banDurationSeconds, banInfo.banCount);
    }

    private static class BanInfo {
        long banExpirationTime;
        int banCount = 0;
    }
}
