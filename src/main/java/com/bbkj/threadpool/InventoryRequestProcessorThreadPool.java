package com.bbkj.threadpool;

import com.bbkj.request.InventoryRequest;
import com.bbkj.request.RequestQueue;
import com.bbkj.thread.InventoryRequestProcessorThread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这个线程池是单例的 内部静态类单例模式
 */
public class InventoryRequestProcessorThreadPool {


    // 在实际项目中，你设置线程池大小是多少，每个线程监控的那个内存队列的大小是多少
    // 都可以做到一个外部的配置文件中
    // 我们这了就给简化了，直接写死了
    /**
     * 线程池
     */
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    private RequestQueue requestQueue;

    private InventoryRequestProcessorThreadPool(){
        RequestQueue requestQueues = RequestQueue.getInstance();
        this.requestQueue=requestQueues;
        for(int i = 0; i < 10; i++) {
            ArrayBlockingQueue<InventoryRequest> queue = new ArrayBlockingQueue<InventoryRequest>(100);
            requestQueues.addQueue(queue);
            threadPool.submit(new InventoryRequestProcessorThread(queue));
        }
    }
    private static class RequestProcessorThreadPoolSingletonHandler{
        private final static InventoryRequestProcessorThreadPool instance= new InventoryRequestProcessorThreadPool();
    }
    private static InventoryRequestProcessorThreadPool getInstance(){
        return RequestProcessorThreadPoolSingletonHandler.instance;
    }

    /**
     * 初始化的便捷方法
     */
    public static InventoryRequestProcessorThreadPool init() {
        return getInstance();
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
