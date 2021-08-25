package smart.cache.report;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

/**
 * @Intro
 * @Author liutengfei
 * @Date 2021-04-21 17:39
 */
public class ProbeScheduledAnnotationBeanPostProcessor extends ScheduledAnnotationBeanPostProcessor {
    private BeanDefinitionRegistry registry;

    public ProbeScheduledAnnotationBeanPostProcessor(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Object o = super.postProcessAfterInitialization(bean, beanName);
        return o;
    }
}
