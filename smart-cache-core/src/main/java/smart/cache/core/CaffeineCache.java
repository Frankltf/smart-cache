package smart.cache.core;

import com.github.benmanes.caffeine.cache.LoadingCache;

/**
 * @Intro
 * @Author liutengfei
 */
@FunctionalInterface
public interface CaffeineCache<K,V> {
    LoadingCache<K,V> createCaffeineCache();
}
