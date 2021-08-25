package smart.cache.core;

import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;

/**
 * @Intro
 * @Author liutengfei
 */
public class CaffeineCacheHolder {
    private LoadingCache loadingCache;
    private CacheStats preCacheStatsSnapshot;
    private Long preTime;

    public CaffeineCacheHolder(CaffeineCache caffeineCache) {
        this.loadingCache = caffeineCache.createCaffeineCache();
    }

    public LoadingCache getCaffeineCache() {
        return loadingCache;
    }

    public LoadingCache getLoadingCache() {
        return loadingCache;
    }

    public void setLoadingCache(LoadingCache loadingCache) {
        this.loadingCache = loadingCache;
    }

    public CacheStats getPreCacheStatsSnapshot() {
        return preCacheStatsSnapshot;
    }

    public void setPreCacheStatsSnapshot(CacheStats preCacheStatsSnapshot) {
        this.preCacheStatsSnapshot = preCacheStatsSnapshot;
        this.preTime = System.currentTimeMillis();
    }

    public Long getPreTime() {
        return preTime;
    }


}
