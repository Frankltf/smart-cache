package smart.cache.core;

import java.util.Map;

/**
 * @Intro
 * @Author liutengfei
 */
public interface CaffeineCacheRegistry {
    Boolean registryCaffeineCacheHolder(String key, CaffeineCacheHolder caffeineCacheHolder);
    CaffeineCacheHolder getCaffeineCacheHolder(String key);
    Boolean containCaffeineCacheHolder(String key);
    Boolean removeCaffeineCacheHolder(String key);
    Map<String, CaffeineCacheHolder> getCaffeineCacheHolders();
}
