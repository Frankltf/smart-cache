package smart.cache.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import smart.cache.config.SmartCacheConfig;

/**
 * @Intro
 * @Author liutengfei
 * @Date 2021-04-28 16:45
 */
@Configuration
public class Config {

    @Bean
    public Jedis jedis (){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(3);
        config.setMaxIdle(3);
        config.setMaxWaitMillis(20000);
        config.setTestOnBorrow(true);
        JedisPool jedisPool = new JedisPool(config, "127.0.0.1", 6379, 20000);
        return jedisPool.getResource();
    }

    @Bean
    public SmartCacheConfig hystrixMonitorConfig(){
        SmartCacheConfig smartCacheConfig = new SmartCacheConfig.Builder()
                .setDbName("pic")
                .setUrl("http://127.0.0.1:8086")
                .build();
        return smartCacheConfig;
    }


}
