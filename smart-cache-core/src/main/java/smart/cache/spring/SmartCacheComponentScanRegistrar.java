package smart.cache.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import smart.cache.annotation.EnableSmartCache;
import smart.cache.core.SmartCacheContext;
import smart.cache.core.SmartCacheTemplate;
import smart.cache.report.*;

import static com.alibaba.spring.util.BeanRegistrar.registerInfrastructureBean;
import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;

/**
 * @Intro
 * @Author liutengfei
 */
public class SmartCacheComponentScanRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registerCommonBeans(registry);
        registryScheduleAnnotationBeanProcessor(importingClassMetadata, registry);
    }

    private void registerCommonBeans(BeanDefinitionRegistry registry){
        registerInfrastructureBean(registry, SmartCacheTemplate.class.getName(), SmartCacheTemplate.class);
        registerInfrastructureBean(registry, SmartCacheContext.class.getName(), SmartCacheContext.class);
        registerInfrastructureBean(registry, ReporterService.class.getName(), DefaultReportService.class);
        registerInfrastructureBean(registry, InfluxdbClient.class.getName(), InfluxdbClient.class);
    }

    private void registryScheduleAnnotationBeanProcessor(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry){
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnableSmartCache.class.getName()));
        if(!attributes.getBoolean("disabledReport")){
            BeanDefinitionBuilder builder = rootBeanDefinition(ProbeScheduledAnnotationBeanPostProcessor.class);
            builder.addConstructorArgValue(registry);
            builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);
            registerScheduleBeanDefinition(registry,attributes);
        }
    }

    private void registerScheduleBeanDefinition(BeanDefinitionRegistry registry,AnnotationAttributes attributes){
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(MetricReportTask.class);
        beanDefinitionBuilder.getBeanDefinition().getPropertyValues().add("disabled",attributes.getBoolean("disabledReport"));
        beanDefinitionBuilder.getBeanDefinition().getPropertyValues().add("appName",environment.getProperty("app.name","unknown"));
        beanDefinitionBuilder.getBeanDefinition().getPropertyValues().add("env",environment.getProperty("app.env","unknown"));
        beanDefinitionBuilder.getBeanDefinition().getPropertyValues().add("reporter",new RuntimeBeanReference(ReporterService.class.getName()));
        beanDefinitionBuilder.getBeanDefinition().getPropertyValues().add("smartCacheContext",new RuntimeBeanReference(SmartCacheContext.class.getName()));
        registry.registerBeanDefinition(MetricReportTask.BEAN_NAME,beanDefinitionBuilder.getBeanDefinition());
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
