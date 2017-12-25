package com.bbkj.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

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
	 * 标识位map  可以就是商品的id 因为是在高并发的场景下所以这里要使用线程安全的Map  ConcurrentHashMap
	 *flagMap只要是用来优化请求去重的  表面一看去重应该是具体的某个内存队列对路由的自己某个具体的商品的请求去重
	 *所以这个flagMap不应该定义在这里，但是实际上 这样定义是没有错。愿意就是因为个商品的请求肯定都会被路由的同一个内存队列中
	 * 而这里的flagMap的key就是商品的id，那么同一个key肯定对应内存队列也是确定的
	 */
	private Map<Integer, Boolean> flagMap = new ConcurrentHashMap<Integer, Boolean>();

	public ArrayBlockingQueue<InventoryRequest> getQueue(int index) {
		return this.queues.get(index);
	}

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


	/**
	 * 获取内存队列的个数
	 * @return
	 */
	public int getQueueSize(){
		return this.queues.size();
	}


	public Map<Integer, Boolean> getFlagMap() {
		return flagMap;
	}
}
