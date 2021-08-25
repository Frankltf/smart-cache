package smart.cache.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCommands;
import smart.cache.exception.SmartCacheTemplateException;
import smart.cache.metrics.ConcurrentStatsCounter;

/**
 * @Intro
 * @Author liutengfei
 */
public class SmartCacheTemplate implements CacheTemplate  {
    public static final Logger LOGGER = LoggerFactory.getLogger(SmartCacheTemplate.class);
    @Autowired
    private JedisCommands jedisCommands;

    @Autowired
    private SmartCacheContext smartCacheContext;

    @Override
    public String get(String key) {
        return get(key, null);
    }

    @Override
    public String get(String key, String server) {
        try {
            Object o = smartCacheContext.getCaffeineCacheHolder(server).getCaffeineCache().get(key);
            if(null == o){
                Long startTime = System.currentTimeMillis();
                String res = jedisCommands.get(key);
                if(null != smartCacheContext.getStatsCounter(server)){
                    if(StringUtils.isEmpty(res)){
                        smartCacheContext.getStatsCounter(server).recordMiss(1);
                    }else {
                        smartCacheContext.getStatsCounter(server).recordHits(1);
                    }
                    smartCacheContext.getStatsCounter(server).recordQueryTime(System.currentTimeMillis() - startTime);
                }
                return res;
            }
            return (String) o;
        }catch (Exception e){
            LOGGER.error("cache get fail",e);
            throw new SmartCacheTemplateException("cache get fail");
        }
    }

    @Override
    public String setex(String key, int seconds, String value) {
        return setex(key, seconds, value, null, null, null);
    }

    @Override
    public String setex(String key, int seconds, String value, String server, CaffeineCache caffeineCache, HotKeyFindRule hotKeyFindRule) {
        try {
            if(!smartCacheContext.containStatsCounter(server)){
                smartCacheContext.registryStatsCounter(server , new ConcurrentStatsCounter());
            }
            jedisCommands.setex(key, seconds, value);
            smartCacheContext.registryHotKeyFindRule(server, hotKeyFindRule);
            if(!smartCacheContext.containCaffeineCacheHolder(server)){
                smartCacheContext.registryCaffeineCacheHolder(server, new CaffeineCacheHolder(caffeineCache));
            }
            smartCacheContext.getCaffeineCacheHolder(server).getCaffeineCache().put(key, value);
            return jedisCommands.setex(key, seconds, value);
        }catch (Exception e){
            LOGGER.error("cache set fail",e);
            throw new SmartCacheTemplateException("cache set fail");
        }
    }
}
