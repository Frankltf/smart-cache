package smart.cache.core;

/**
 * @Intro
 * @Author liutengfei
 */
public interface CacheTemplate {
    public String get(String key);

    String get(String key, String server);

    String setex(String key, int seconds, String value);

    String setex(String key, int seconds, String value, String server, CaffeineCache caffeineCache, HotKeyFindRule hotKeyFindRule);
}
