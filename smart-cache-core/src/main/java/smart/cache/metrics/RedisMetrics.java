package smart.cache.metrics;

/**
 * @Intro
 * @Author liutengfei
 */
public enum  RedisMetrics {
    APP_NAME("", "app_name"),
    SERVER("", "server"),
    CACHE_KEY("", "key"),
    ENV("", "env"),
    EVICTION_COUNT("evictionCount", ""),
    TOTAL_EVICTION_COUNT("totalEvictionCount", ""),
    HIT_COUNT("hitCount", ""),
    TOTAL_HIT_COUNT("totalHitCount", ""),
    LOAD_TIME("loadTime", ""),
    TOTAL_LOAD_TIME("totalLoadTime", ""),
    MISS_COUNT("missCount", ""),
    TOTAL_MISS_COUNT("totalmissCount", ""),
    TOTAL_LOAD_COUNT("totalLoadCount", ""),
    TOTAL_LOAD_SUCCESS_COUNT("totalLoadSuccessCount", ""),
    LOAD_SUCCESS_COUNT("loadSuccessCount", ""),
    TOTAL_LOAD_FAILURE_COUNT("totalLoadFAILURECount", ""),
    QUERY_TIME("query_time", ""),
    HIT_RATE("hitRate", ""),
    REQUEST_COUNT("requestCount", ""),
    TOTAL_REQUEST_COUNT("totalRequestCount", "");

    private String field;
    private String tag;

    RedisMetrics(String field, String tag) {
        this.field = field;
        this.tag = tag;
    }

    public String getField() {
        return field;
    }


    public String getTag() {
        return tag;
    }
}
