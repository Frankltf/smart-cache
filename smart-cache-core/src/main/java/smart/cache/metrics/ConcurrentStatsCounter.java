package smart.cache.metrics;

import org.checkerframework.checker.index.qual.NonNegative;

import java.util.concurrent.atomic.LongAdder;

/**
 * @Intro
 * @Author liutengfei
 */
public class ConcurrentStatsCounter implements StatsCounter {
    private final LongAdder hitCount;
    private final LongAdder totalLoadTime;
    private final LongAdder missCount;

    public ConcurrentStatsCounter() {
        hitCount = new LongAdder();
        missCount = new LongAdder();
        totalLoadTime = new LongAdder();
    }

    @Override
    public void recordHits(@NonNegative int count) {
        hitCount.add(count);
    }

    @Override
    public void recordMiss(@NonNegative int count) {
        missCount.add(count);
    }

    @Override
    public void recordQueryTime(@NonNegative Long loadTime) {
        totalLoadTime.add(loadTime);
    }

    @Override
    public CacheStats snapshot() {
        return new CacheStats(
                negativeToMaxValue(hitCount.sum()),
                negativeToMaxValue(missCount.sum()),
                negativeToMaxValue(totalLoadTime.sum())
        );
    }

    private static long negativeToMaxValue(long value) {
        return (value >= 0) ? value : Long.MAX_VALUE;
    }
}
