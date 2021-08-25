package smart.cache.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Intro
 * @Author liutengfei
 */
public class DefaultHotKeyRuleFactoryFactory implements HotKeyRuleFactory, HotKeyRuleRegistry, ConfigurableListableHotKeyFindRuleFactory {
    private Map<String, HotKeyFindRule> hotKeyFindRuleMap = new ConcurrentHashMap<>();
    @Override
    public Boolean registryHotKeyFindRule(String key, HotKeyFindRule hotKeyFindRule) {
        hotKeyFindRuleMap.put(key, hotKeyFindRule);
        return true;
    }

    @Override
    public HotKeyFindRule getHotKeyFindRule(String key) {
        return hotKeyFindRuleMap.get(key);
    }

    @Override
    public Boolean containHotKeyFindRule(String key) {
        return hotKeyFindRuleMap.containsKey(key);
    }

    @Override
    public Boolean removeHotKeyFindRule(String key) {
        hotKeyFindRuleMap.remove(key);
        return true;
    }
}
