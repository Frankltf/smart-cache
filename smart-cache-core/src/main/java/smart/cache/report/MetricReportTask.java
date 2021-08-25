package smart.cache.report;

import com.github.benmanes.caffeine.cache.stats.CacheStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import smart.cache.core.CaffeineCacheHolder;
import smart.cache.core.SmartCacheContext;
import smart.cache.metrics.Metrics;
import smart.cache.metrics.RedisMetrics;
import smart.cache.metrics.StatsCounter;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @Intro
 * @Author liutengfei
 */
public class MetricReportTask implements InitializingBean {
    public static final Logger LOGGER = LoggerFactory.getLogger(MetricReportTask.class);
    public static final String BEAN_NAME = "metricReportTask";
    private ReporterService reporter;
    private SmartCacheContext smartCacheContext;
    private Boolean disabled;
    private String appName = "unknown";
    private String env = "test";
    private InetAddress ip;


    public void setReporter(ReporterService reporter) {
        this.reporter = reporter;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public void setSmartCacheContext(SmartCacheContext smartCacheContext) {
        this.smartCacheContext = smartCacheContext;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void report(){
        if (!disabled) {
            Map<String, CaffeineCacheHolder> caffeineCacheHolders = smartCacheContext.getCaffeineCacheHolders();
            Map<String, StatsCounter> statsCounterMap = smartCacheContext.getStatsCounters();
            this.collectCommandData(caffeineCacheHolders, statsCounterMap);
        }
    }

    private void collectCommandData( Map<String, CaffeineCacheHolder> caffeineCacheHolders,
                                     Map<String, StatsCounter> statsCounterMap) {
        caffeineCacheHolders.forEach((key,value) -> {
            doCaffeineReport(key,value);
        });

        statsCounterMap.forEach((key,value) -> {
            doRedisReport(key,value);
        });
    }

    private void doRedisReport(String key, StatsCounter statsCounter){
        try {
            smart.cache.metrics.CacheStats stats = statsCounter.snapshot();
            smart.cache.metrics.CacheStats preStats = smartCacheContext.getCacheStats(key);
            Long current = System.currentTimeMillis();
            Map<RedisMetrics, Object> points = new HashMap();
            points.put(RedisMetrics.APP_NAME, appName);
            points.put(RedisMetrics.SERVER, ip.toString());
            points.put(RedisMetrics.ENV, env);
            points.put(RedisMetrics.CACHE_KEY, key);
            points.put(RedisMetrics.TOTAL_HIT_COUNT, stats.hitCount());
            points.put(RedisMetrics.TOTAL_MISS_COUNT, stats.missCount());
            points.put(RedisMetrics.TOTAL_REQUEST_COUNT, stats.requestCount());
            points.put(RedisMetrics.HIT_COUNT, null == preStats ? stats.hitCount() : (stats.hitCount() - preStats.hitCount()));
            points.put(RedisMetrics.MISS_COUNT, null == preStats ? stats.missCount() : (stats.missCount() - preStats.missCount()));
            points.put(RedisMetrics.REQUEST_COUNT, null == preStats ? stats.requestCount() : (stats.requestCount() - preStats.requestCount()));
            Long requestCount = (Long) points.get(RedisMetrics.REQUEST_COUNT);
            if(!requestCount.toString().equals("0") && null != preStats){
                points.put(RedisMetrics.QUERY_TIME, requestCount.equals(0)? 0 : (stats.totalLoadTime() - preStats.totalLoadTime())/requestCount);
            }
            Long hitCount = (Long) points.get(RedisMetrics.HIT_COUNT);
            points.put(RedisMetrics.HIT_RATE,getRate(hitCount,requestCount));
            this.reporter.reportRedis(current, points);
            smartCacheContext.registryCacheStats(key, stats);
        }catch (Exception e){
            LOGGER.error("doCaffeineReport error",e);
        }
    }

    private Double getRate(Long numerator , Long denominator){
        if(denominator.toString().equals("0")){
            return 0D;
        }
        Double hitRate = (numerator.doubleValue()/denominator.doubleValue()) * 100;
        BigDecimal b= new BigDecimal(hitRate);
        hitRate = b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
        return hitRate;
    }

    private void doCaffeineReport(String key, CaffeineCacheHolder caffeineCacheHolder){
        try {
            CacheStats stats = caffeineCacheHolder.getCaffeineCache().stats();
            CacheStats preStats = caffeineCacheHolder.getPreCacheStatsSnapshot();
            Long current = System.currentTimeMillis();
            Map<Metrics, Object> points = new HashMap();
            points.put(Metrics.APP_NAME, appName);
            points.put(Metrics.SERVER, ip.toString());
            points.put(Metrics.ENV, env);
            points.put(Metrics.CACHE_KEY, key);
            points.put(Metrics.TOTAL_EVICTION_COUNT, stats.evictionCount());
            points.put(Metrics.TOTAL_HIT_COUNT, stats.hitCount());
            points.put(Metrics.TOTAL_LOAD_TIME, stats.totalLoadTime());
            points.put(Metrics.TOTAL_MISS_COUNT, stats.missCount());
            points.put(Metrics.TOTAL_REQUEST_COUNT, stats.requestCount());
            points.put(Metrics.TOTAL_LOAD_COUNT, stats.loadCount());
            points.put(Metrics.TOTAL_LOAD_SUCCESS_COUNT, stats.loadSuccessCount());
            points.put(Metrics.TOTAL_LOAD_FAILURE_COUNT, stats.loadFailureRate());
            points.put(Metrics.EVICTION_COUNT, null == preStats ? stats.evictionCount() : (stats.evictionCount() - preStats.evictionCount()));
            points.put(Metrics.LOAD_TIME, null == preStats ? stats.totalLoadTime() : (stats.totalLoadTime() - preStats.totalLoadTime()));
            points.put(Metrics.HIT_COUNT, null == preStats ? stats.hitCount() : (stats.hitCount() - preStats.hitCount()));
            points.put(Metrics.MISS_COUNT, null == preStats ? stats.missCount() : (stats.missCount() - preStats.missCount()));
            points.put(Metrics.LOAD_SUCCESS_COUNT, null == preStats ? stats.loadSuccessCount() : (stats.loadSuccessCount() - preStats.loadSuccessCount()));
            points.put(Metrics.LOAD_FAILURE_COUNT, null == preStats ? stats.loadFailureRate() : (stats.loadFailureRate() - preStats.loadFailureRate()));
            points.put(Metrics.REQUEST_COUNT, null == preStats ? stats.requestCount() : (stats.requestCount() - preStats.requestCount()));
            Long requestCount = (Long) points.get(Metrics.REQUEST_COUNT);
            Long hitCount = (Long) points.get(Metrics.HIT_COUNT);
            points.put(Metrics.HIT_RATE,getRate(hitCount,requestCount));
            this.reporter.report(current, points);
            caffeineCacheHolder.setPreCacheStatsSnapshot(stats);
        }catch (Exception e){
            LOGGER.error("doCaffeineReport error",e);
        }
    }

    @Override
    public void afterPropertiesSet() {
        try {
            ip = InetAddress.getLocalHost();
        } catch (Exception e) {
        }
    }

}
