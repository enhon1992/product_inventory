package com.bbkj.configuration;
import com.bbkj.threadpool.InventoryRequestProcessorThreadPool;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class InventoryConfiguration {

    /**
     * 初始化线程池 并且纳入到spring的容器中管理起来
     * @return
     */
    @Bean
    public InventoryRequestProcessorThreadPool inventoryRequestProcessorThreadPool(){
        return  InventoryRequestProcessorThreadPool.init();
    }


}
