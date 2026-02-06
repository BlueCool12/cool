package com.pyomin.cool.interceptor;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class RateLimitProviderTest {

  @Test
  void testBanLogic() {
    RateLimitProvider provider = new RateLimitProvider();
    String ip = "127.0.0.1";
    int capacity = 10;
    int seconds = 60;

    capacity = 1;
    assertTrue(provider.tryConsume(ip, capacity, seconds));
    assertFalse(provider.tryConsume(ip, capacity, seconds));

    for (int i = 0; i < 9; i++) {
      assertFalse(provider.tryConsume(ip, capacity, seconds));
    }

    try {
      Field bannedIpsField = RateLimitProvider.class.getDeclaredField("bannedIps");
      bannedIpsField.setAccessible(true);
      @SuppressWarnings("unchecked")
      Map<String, ?> bannedIps = (Map<String, ?>) bannedIpsField.get(provider);

      assertTrue(bannedIps.containsKey(ip), "User should be in banned list");

      assertFalse(provider.tryConsume(ip, capacity, seconds));

      Object banInfo = bannedIps.get(ip);
      Field expirationField = banInfo.getClass().getDeclaredField("banExpirationTime");
      expirationField.setAccessible(true);
      long expirationTime = (long) expirationField.get(banInfo);
      long now = System.currentTimeMillis();

      assertTrue(expirationTime > now + 50000 && expirationTime < now + 70000, "First ban should be ~60s");

      expirationField.set(banInfo, now - 1000);

      for (int i = 0; i < 10; i++) {
        assertFalse(provider.tryConsume(ip, capacity, seconds));
      }

      banInfo = bannedIps.get(ip);
      expirationTime = (long) expirationField.get(banInfo);
      now = System.currentTimeMillis();

      assertTrue(expirationTime > now + 110000 && expirationTime < now + 130000, "Second ban should be ~120s");

    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
}
