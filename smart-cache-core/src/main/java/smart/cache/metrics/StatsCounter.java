package smart.cache.metrics;

import org.checkerframework.checker.index.qual.NonNegative;

/**
 * @Intro
 * @Author liutengfei
 */
public interface StatsCounter {
    void recordHits(@NonNegative int count);
    void recordMiss(@NonNegative int count);
    CacheStats snapshot();
    void recordQueryTime(@NonNegative Long loadTime);

}
