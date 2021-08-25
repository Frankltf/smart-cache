package smart.cache.core;

import smart.cache.metrics.CacheStats;

/**
 * @Intro
 * @Author liutengfei
 */
public interface CacheStatsRegistry {
    void registryCacheStats(String key, CacheStats cacheStats);
    CacheStats getCacheStats(String key);
}
