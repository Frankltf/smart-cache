package smart.cache.annotation;

import org.springframework.context.annotation.Import;
import smart.cache.spring.SmartCacheComponentScanRegistrar;

import java.lang.annotation.*;

/**
 * @Intro
 * @Author liutengfei
 * @Date 2021-05-11 10:44
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(SmartCacheComponentScanRegistrar.class)
public @interface EnableSmartCache {
    boolean disabledReport () default false;
}
