package smart.cache.core;

import smart.cache.metrics.StatsCounter;

import java.util.Map;

/**
 * @Intro
 * @Author liutengfei
 */
public interface StatsCounterRegistry {
    void registryStatsCounter(String key, StatsCounter statsCounter);
    StatsCounter getStatsCounter(String key);
    Boolean containStatsCounter(String key);
    Map<String, StatsCounter> getStatsCounters();
}
