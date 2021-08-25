package smart.cache.report;

import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import smart.cache.config.InfluxdbConst;
import smart.cache.config.SmartCacheConfig;
import smart.cache.metrics.Metrics;
import smart.cache.metrics.RedisMetrics;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Intro
 * @Author liutengfei
 */
public class DefaultReportService implements ReporterService{
    public static final Logger LOGGER = LoggerFactory.getLogger(DefaultReportService.class);
    @Autowired
    private InfluxdbClient influxdb;

    @Autowired
    private SmartCacheConfig smartCacheConfig;

    @Override
    public void report(Long current, Map<Metrics, Object> points) {
        if (smartCacheConfig.isDisabled()){
            return;
        }

        if (current == null || points == null || points.isEmpty()) {
            return;
        }

        try {
            String measurement = InfluxdbConst.MEASUREMENT;
            Iterator it = points.entrySet().iterator();
            Point.Builder pointBuilder = Point.measurement(measurement).time(current, TimeUnit.MILLISECONDS);
            while (it.hasNext()) {
                Map.Entry<Metrics, Object> entry = (Map.Entry<Metrics, Object>) it.next();
                if (entry.getValue() instanceof Number) {
                    pointBuilder.addField(entry.getKey().getField(), (Number) entry.getValue());
                } else if (entry.getValue() instanceof String) {

                    if (!StringUtils.isEmpty(entry.getKey().getTag())) {
                        pointBuilder.tag(entry.getKey().getTag(), (String) entry.getValue());
                    } else if (!StringUtils.isEmpty(entry.getKey().getField())) {
                        pointBuilder.addField(entry.getKey().getField(), (String) entry.getValue());
                    } else {
                    }
                } else {
                    System.out.print("fef");
                }
            }
            influxdb.getInctance().write(smartCacheConfig.getDbName(), smartCacheConfig.getRetentionPolicy(), pointBuilder.build());
        }catch (Exception e){
            LOGGER.error("report fail", e);
        }
    }

    @Override
    public void reportRedis(Long current, Map<RedisMetrics, Object> point) {
        try {
            String measurement = InfluxdbConst.REDIS_MEASUREMENT;
            Iterator it = point.entrySet().iterator();
            Point.Builder pointBuilder = Point.measurement(measurement).time(current, TimeUnit.MILLISECONDS);
            while (it.hasNext()) {
                Map.Entry<RedisMetrics, Object> entry = (Map.Entry<RedisMetrics, Object>) it.next();
                if (entry.getValue() instanceof Number) {
                    pointBuilder.addField(entry.getKey().getField(), (Number) entry.getValue());
                } else if (entry.getValue() instanceof String) {

                    if (!StringUtils.isEmpty(entry.getKey().getTag())) {
                        pointBuilder.tag(entry.getKey().getTag(), (String) entry.getValue());
                    } else if (!StringUtils.isEmpty(entry.getKey().getField())) {
                        pointBuilder.addField(entry.getKey().getField(), (String) entry.getValue());
                    } else {
                    }
                } else {
                    System.out.print("fef");
                }
            }
            influxdb.getInctance().write(smartCacheConfig.getDbName(), smartCacheConfig.getRetentionPolicy(), pointBuilder.build());
        }catch (Exception e){
            LOGGER.error("reportRedis fail", e);
        }
    }
}
