package smart.cache.core;

/**
 * @Intro
 * @Author liutengfei
 */
public interface HotKeyRuleRegistry {
    Boolean registryHotKeyFindRule(String key, HotKeyFindRule hotKeyFindRule);
    HotKeyFindRule getHotKeyFindRule(String key);
    Boolean containHotKeyFindRule(String key);
    Boolean removeHotKeyFindRule(String key);
}
