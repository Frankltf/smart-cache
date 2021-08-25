package smart.cache.report;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import smart.cache.config.SmartCacheConfig;

import java.util.concurrent.TimeUnit;

public class InfluxdbClient implements InitializingBean {
    private static final int BATCH = 50;
    private static final int FLUSH_DURATION = 1000;

    @Autowired
    private SmartCacheConfig smartCacheConfig;

    private InfluxDB influxDB;

    public InfluxDB getInctance() {
        return influxDB;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!StringUtils.isEmpty(smartCacheConfig.getUsername())) {
            this.influxDB = InfluxDBFactory.connect(smartCacheConfig.getUrl(),
                    smartCacheConfig.getUsername(), smartCacheConfig.getPassword());
        } else {
            this.influxDB = InfluxDBFactory.connect(smartCacheConfig.getUrl());
        }
        this.influxDB.enableBatch(BATCH, FLUSH_DURATION, TimeUnit.MILLISECONDS);
    }
}
