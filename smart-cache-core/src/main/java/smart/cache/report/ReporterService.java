package smart.cache.report;

import smart.cache.metrics.Metrics;
import smart.cache.metrics.RedisMetrics;

import java.util.Map;

/**
 * @Intro
 * @Author liutengfei
 * @Date 2021-04-23 13:09
 */
public interface ReporterService {
    void report(Long current, Map<Metrics, Object> point);
    void reportRedis(Long current, Map<RedisMetrics, Object> point);
}
