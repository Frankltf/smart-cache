package smart.cache.core;

import smart.cache.metrics.CacheStats;
import smart.cache.metrics.StatsCounter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Intro
 * @Author liutengfei
 */
public class DefaultStatsCounterFactory implements StatsCounterFactory, StatsCounterRegistry,CacheStatsRegistry, ConfigurableListableStatsCounterFactory {
    private Map<String, StatsCounter> statsCounterMap = new ConcurrentHashMap<>();
    private Map<String, CacheStats> cacheStatsMap = new ConcurrentHashMap<>();

    @Override
    public void registryStatsCounter(String key, StatsCounter statsCounter) {
        statsCounterMap.put(key, statsCounter);
    }

    @Override
    public void registryCacheStats(String key, CacheStats cacheStats) {
        cacheStatsMap.put(key,cacheStats);
    }

    @Override
    public CacheStats getCacheStats(String key) {
        return cacheStatsMap.get(key);
    }


    @Override
    public StatsCounter getStatsCounter(String key) {
        return statsCounterMap.get(key);
    }

    @Override
    public Boolean containStatsCounter(String key) {
        return statsCounterMap.containsKey(key);
    }

    @Override
    public Map<String, StatsCounter> getStatsCounters() {
        return statsCounterMap;
    }
}
