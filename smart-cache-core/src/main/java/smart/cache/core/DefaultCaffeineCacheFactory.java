package smart.cache.core;

import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Intro
 * @Author liutengfei
 */
public class DefaultCaffeineCacheFactory implements CaffeineCacheFactory, CaffeineCacheRegistry, ConfigurableListableCaffeineCacheFactory {
    private Map<String, CaffeineCacheHolder> caffeineCacheHolderMap = new ConcurrentHashMap<>();

    @Override
    public LoadingCache getCaffeineCache(String key) {
        return caffeineCacheHolderMap.get(key).getCaffeineCache();
    }

    @Override
    public Boolean registryCaffeineCacheHolder(String key, CaffeineCacheHolder caffeineCacheHolder) {
        caffeineCacheHolderMap.put(key, caffeineCacheHolder);
        return true;
    }

    @Override
    public CaffeineCacheHolder getCaffeineCacheHolder(String key) {
        return caffeineCacheHolderMap.get(key);
    }

    @Override
    public Boolean containCaffeineCacheHolder(String key) {
        return caffeineCacheHolderMap.containsKey(key);
    }

    @Override
    public Boolean removeCaffeineCacheHolder(String key) {
        caffeineCacheHolderMap.remove(key);
        return true;
    }

    @Override
    public Map<String, CaffeineCacheHolder> getCaffeineCacheHolders() {
        return caffeineCacheHolderMap;
    }
}
