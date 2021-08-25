package smart.cache.core;

import smart.cache.metrics.CacheStats;
import smart.cache.metrics.StatsCounter;

import java.util.Map;

/**
 * @Intro
 * @Author liutengfei
 */
public abstract class AbstraceSmartCacheContext implements CaffeineCacheRegistry, HotKeyRuleRegistry , StatsCounterRegistry, CacheStatsRegistry{

    abstract ConfigurableListableCaffeineCacheFactory getCaffeineCacheFactory();

    abstract ConfigurableListableHotKeyFindRuleFactory getHotKeyRuleFactory();

    abstract ConfigurableListableStatsCounterFactory getStatsCounterFactory();

    @Override
    public Boolean registryCaffeineCacheHolder(String key, CaffeineCacheHolder caffeineCacheHolder) {
        if(!containCaffeineCacheHolder(key)){
            return getCaffeineCacheFactory().registryCaffeineCacheHolder(key, caffeineCacheHolder);
        }
        return false;
    }

    @Override
    public CaffeineCacheHolder getCaffeineCacheHolder(String key) {
        return getCaffeineCacheFactory().getCaffeineCacheHolder(key);
    }

    @Override
    public Boolean containCaffeineCacheHolder(String key) {
        return getCaffeineCacheFactory().containCaffeineCacheHolder(key);
    }

    @Override
    public Boolean removeCaffeineCacheHolder(String key) {
        getCaffeineCacheFactory().removeCaffeineCacheHolder(key);
        return true;
    }

    @Override
    public Boolean registryHotKeyFindRule(String key, HotKeyFindRule hotKeyFindRule) {
        if(!containCaffeineCacheHolder(key)){
            return getHotKeyRuleFactory().registryHotKeyFindRule(key, hotKeyFindRule);
        }
        return false;
    }

    @Override
    public Map<String, CaffeineCacheHolder> getCaffeineCacheHolders() {
        return getCaffeineCacheFactory().getCaffeineCacheHolders();
    }

    @Override
    public HotKeyFindRule getHotKeyFindRule(String key) {
        return getHotKeyRuleFactory().getHotKeyFindRule(key);
    }

    @Override
    public Boolean containHotKeyFindRule(String key) {
        return getHotKeyRuleFactory().containHotKeyFindRule(key);
    }

    @Override
    public Boolean removeHotKeyFindRule(String key) {
        return getHotKeyRuleFactory().removeHotKeyFindRule(key);
    }

    @Override
    public void registryStatsCounter(String key, StatsCounter statsCounter) {
        getStatsCounterFactory().registryStatsCounter(key, statsCounter);
    }

    @Override
    public StatsCounter getStatsCounter(String key) {
        return getStatsCounterFactory().getStatsCounter(key);
    }

    @Override
    public Boolean containStatsCounter(String key) {
        return getStatsCounterFactory().containStatsCounter(key);
    }

    @Override
    public Map<String, StatsCounter> getStatsCounters() {
        return getStatsCounterFactory().getStatsCounters();
    }

    @Override
    public void registryCacheStats(String key, CacheStats cacheStats) {
        getStatsCounterFactory().registryCacheStats(key,cacheStats);
    }

    @Override
    public CacheStats getCacheStats(String key) {
        return getStatsCounterFactory().getCacheStats(key);
    }
}
