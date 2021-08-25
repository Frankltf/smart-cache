package smart.cache.core;

/**
 * @Intro
 * @Author liutengfei
 */
public class SmartCacheContext extends AbstraceSmartCacheContext {
    private final ConfigurableListableCaffeineCacheFactory caffeineCacheFactory;
    private final ConfigurableListableHotKeyFindRuleFactory hotKeyFindRuleFactory;
    private final ConfigurableListableStatsCounterFactory statsCounterFactory;

    public SmartCacheContext() {
        this.caffeineCacheFactory = new DefaultCaffeineCacheFactory();
        this.hotKeyFindRuleFactory = new DefaultHotKeyRuleFactoryFactory();
        this.statsCounterFactory = new DefaultStatsCounterFactory();
    }



    @Override
    ConfigurableListableCaffeineCacheFactory getCaffeineCacheFactory() {
        return caffeineCacheFactory;
    }

    @Override
    ConfigurableListableHotKeyFindRuleFactory getHotKeyRuleFactory() {
        return hotKeyFindRuleFactory;
    }

    @Override
    ConfigurableListableStatsCounterFactory getStatsCounterFactory() {
        return statsCounterFactory;
    }
}
