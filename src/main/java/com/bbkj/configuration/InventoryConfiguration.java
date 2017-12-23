package com.bbkj.configuration;
import com.bbkj.threadpool.InventoryRequestProcessorThreadPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class InventoryConfiguration {

    /**
     * 处理库存的后台线程的个数
     */
    @Value("${inventory.project.thread.count}")
    private Integer thredCount;

    @Value("${inventory.project.perInventoryQueueCapacity}")
    private Integer perInventoryQueueCapacity;

    @Value("${inventory.project.inventoryQueuesCount}")
    private Integer inventoryQueuesCount;

    /**
     * 项目启动 初始化内存队列和线程池 并且把内存队列和线程池 纳入到spring中管理起来
     * @return
     */
    @Bean
    InventoryRequestProcessorThreadPool inventoryRequestProcessorThreadPool(){
        return InventoryRequestProcessorThreadPool.init(thredCount,perInventoryQueueCapacity,inventoryQueuesCount);
    }
}
