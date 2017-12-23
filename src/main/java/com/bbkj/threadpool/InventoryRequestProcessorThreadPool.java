package com.bbkj.threadpool;

import com.bbkj.request.InventoryRequest;
import com.bbkj.request.RequestQueue;
import com.bbkj.thread.InventoryRequestProcessorThread;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这个线程池是单例的 内部静态类单例模式
 */
public class InventoryRequestProcessorThreadPool {

    /**
     * 线程池
     */
    private ExecutorService threadPool;

    private RequestQueue requestQueues;

    /**
     * 构造函数私有化，如果将该类通过@Component管理  就不能保证单例了
     */
    private InventoryRequestProcessorThreadPool(){
    }
    private static class RequestProcessorThreadPoolSingletonHandler{
        private final static InventoryRequestProcessorThreadPool instance= new InventoryRequestProcessorThreadPool();
    }
    private static InventoryRequestProcessorThreadPool getInstance(){
        return RequestProcessorThreadPoolSingletonHandler.instance;
    }

    /**
     * 初始化的线程池和内存队列
     */
    public static  InventoryRequestProcessorThreadPool init(int threadCount,int perInventoryQueueCapacity,int inventoryQueuesCount) {
        getInstance().threadPool = Executors.newFixedThreadPool(threadCount);
        getInstance().requestQueues = RequestQueue.getInstance();
        for(int i = 0; i < inventoryQueuesCount; i++) {
            ArrayBlockingQueue<InventoryRequest> queue = new ArrayBlockingQueue<InventoryRequest>(perInventoryQueueCapacity);
            getInstance().requestQueues.addQueue(queue);
            getInstance().threadPool.submit(new InventoryRequestProcessorThread(queue));
        }
        return  getInstance();
    }
    public RequestQueue getRequestQueue() {
        return requestQueues;
    }

    public ExecutorService getThreadPool() {
        return threadPool;
    }
}
