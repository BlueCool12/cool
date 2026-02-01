package com.pyomin.cool.interceptor;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@Component
public class RateLimitProvider {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String ip, int capacity, int seconds) {
        String key = ip + "-" + capacity + "-" + seconds;
        return buckets.computeIfAbsent(key, k -> createNewBucket(capacity, seconds));
    }

    private Bucket createNewBucket(int capacity, int seconds) {
        Refill refill = Refill.intervally(capacity, Duration.ofSeconds(seconds));
        Bandwidth limit = Bandwidth.classic(capacity, refill);

        return Bucket.builder().addLimit(limit).build();
    }
}
