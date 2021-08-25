package smart.cache.demo;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import smart.cache.core.SmartCacheTemplate;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Intro
 * @Author liutengfei
 * @Date 2021-05-11 10:39
 */
@Component
public class DemoService {

    @Autowired
    private SmartCacheTemplate smartCacheTemplate;

    public void test(){
        try {
            Thread.sleep((int)(Math.random()*100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String key1 = String.valueOf(((int)(Math.random()*10)));
        smartCacheTemplate.setex(key1,20, "key1-value", "server-test" , () -> {
            return Caffeine.newBuilder()
                    .expireAfterWrite(20, TimeUnit.SECONDS)
                    .initialCapacity(10)
                    .maximumSize(10)
                    .recordStats()
                    .build(new CacheLoader<String, CacheModel>() {
                        @Override
                        @Nullable
                        public CacheModel load(@NonNull String key) throws Exception {
                            return null;
                        }
                    });
        } , (times)-> {
            if(times > 3){
                return true;
            }
            return false;
        });
        String key2 = String.valueOf(((int)(Math.random()*10)));
        smartCacheTemplate.get(key2,"server-test"  );
        System.out.print("test========= \n");



    }
}
