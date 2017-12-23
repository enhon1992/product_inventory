package com.bbkj.thread;

import com.bbkj.request.InventoryRequest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * 处理请求的线程 每个线程对应一个 自己监控的内存队列
 * 自己监控的内存队列 是阻塞队列
 */
public class InventoryRequestProcessorThread implements Callable<Boolean>{
    /**
     * 自己监控的内存队列
     */
    private ArrayBlockingQueue<InventoryRequest> queue;

    public InventoryRequestProcessorThread(ArrayBlockingQueue<InventoryRequest> queue){
        this.queue=queue;
    }
    @Override
    public Boolean call() throws Exception {
        try {
            while(true) {
                // ArrayBlockingQueue
                // Blocking就是说明，如果队列满了，或者是空的，那么都会在执行操作的时候，阻塞住
                InventoryRequest request = queue.take();
                // 执行这个request操作
                request.process();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
