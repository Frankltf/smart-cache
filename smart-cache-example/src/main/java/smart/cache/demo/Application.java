package smart.cache.demo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import smart.cache.annotation.EnableSmartCache;

/**
 * @Intro
 * @Author liutengfei
 */

public class Application {
    public static void main(String[] args) {
        System.setProperty("app.name","smart-cache");
        System.setProperty("app.env","test");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SmartCacheDemo.class);
        context.start();
        DemoService service = context.getBean("demoService", DemoService.class);
        for (int i = 0;i<1000000;i++){
            service.test();
        }
        System.out.println(service.getClass());
    }

    @EnableSmartCache
    @ComponentScan(value = {"smart.cache.demo"})
    static class SmartCacheDemo {

    }
}
