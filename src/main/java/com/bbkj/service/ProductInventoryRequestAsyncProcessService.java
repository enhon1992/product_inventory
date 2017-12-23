package com.bbkj.service;

import com.bbkj.request.InventoryRequest;

/**
 * 请求异步执行的service
 * @author Administrator
 *
 */
public interface ProductInventoryRequestAsyncProcessService {

	void process(InventoryRequest request);
}
