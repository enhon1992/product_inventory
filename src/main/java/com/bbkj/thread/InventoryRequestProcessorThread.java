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

        while (true){
            break;
        }
        return null;
    }
}
