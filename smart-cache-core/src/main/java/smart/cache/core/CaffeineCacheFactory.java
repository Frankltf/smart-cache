package smart.cache.core;

import com.github.benmanes.caffeine.cache.LoadingCache;

/**
 * @Intro
 * @Author liutengfei
 */
public interface CaffeineCacheFactory {
    LoadingCache getCaffeineCache(String key);
}
