package com.bbkj.request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 请求内存队列
 * @author Administrator
 *
 */
public class RequestQueue {

	/**
	 * 内存队列
	 */
	private List<ArrayBlockingQueue<InventoryRequest>> queues = new ArrayList<ArrayBlockingQueue<InventoryRequest>>();
	
	/**
	 * 单例有很多种方式去实现：我采取绝对线程安全的一种方式
	 * 
	 * 静态内部类的方式，去初始化单例
	 * 
	 * @author Administrator
	 *
	 */
	private static class RequestQueueSingletonHandler {
		private  final static  RequestQueue instance=new RequestQueue();
	}

	/**
	 * 构造函数私有化
	 */
	private RequestQueue(){
	}
	public static RequestQueue getInstance(){
		return RequestQueueSingletonHandler.instance;
	}
	/**
	 * 添加一个内存队列
	 * @param queue
	 */
	public void addQueue(ArrayBlockingQueue<InventoryRequest> queue) {
		this.queues.add(queue);
	}
	
}
