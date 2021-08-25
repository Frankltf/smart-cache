package smart.cache.core;

/**
 * @Intro
 * @Author liutengfei
 */
@FunctionalInterface
public interface HotKeyFindRule {
    Boolean judgeHotKey(int times);
}
