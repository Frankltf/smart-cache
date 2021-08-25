package smart.cache.metrics;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * @Intro
 * @Author liutengfei
 */
public class CacheStats {
    private static final CacheStats EMPTY_STATS = new CacheStats( 0, 0, 0);
    private final long hitCount;
    private final long missCount;
    private final long totalLoadTime;

    public CacheStats(@NonNegative long hitCount, @NonNegative long missCount,
                      @NonNegative long totalLoadTime) {

        this.hitCount = hitCount;
        this.missCount = missCount;
        this.totalLoadTime = totalLoadTime;
    }

    @NonNull
    public static CacheStats empty() {
        return EMPTY_STATS;
    }


    private static long saturatedAdd(long a, long b) {
        long naiveSum = a + b;
        if ((a ^ b) < 0 | (a ^ naiveSum) >= 0) {
            return naiveSum;
        }
        return Long.MAX_VALUE + ((naiveSum >>> (Long.SIZE - 1)) ^ 1);
    }


    @NonNegative
    public long requestCount() {
        return saturatedAdd(hitCount, missCount);
    }


    @NonNegative
    public long hitCount() {
        return hitCount;
    }


    @NonNegative
    public double hitRate() {
        long requestCount = requestCount();
        return (requestCount == 0) ? 1.0 : (double) hitCount / requestCount;
    }


    @NonNegative
    public long missCount() {
        return missCount;
    }


    @NonNegative
    public double missRate() {
        long requestCount = requestCount();
        return (requestCount == 0) ? 0.0 : (double) missCount / requestCount;
    }

    @NonNegative
    public long totalLoadTime() {
        return totalLoadTime;
    }
}
