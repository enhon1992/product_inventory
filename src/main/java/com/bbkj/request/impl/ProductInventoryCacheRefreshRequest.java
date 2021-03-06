package com.bbkj.request.impl;

import com.bbkj.pojo.ProductInventory;
import com.bbkj.request.InventoryRequest;
import com.bbkj.service.ProductInventoryService;

/**
 * 重新加载商品库存的缓存
 * @author Administrator
 *
 */
public class ProductInventoryCacheRefreshRequest implements InventoryRequest {

	/**
	 * 商品id
	 */
	private Integer productId;
	/**
	 * 商品库存Service
	 */
	private ProductInventoryService productInventoryService;
	
	public ProductInventoryCacheRefreshRequest(Integer productId,
			ProductInventoryService productInventoryService) {
		this.productId = productId;
		this.productInventoryService = productInventoryService;
	}
	
	@Override
	public void process() {
		// 从数据库中查询最新的商品库存数量
		ProductInventory productInventory = productInventoryService.findProductInventory(productId);
		// 将最新的商品库存数量，刷新到redis缓存中去
		productInventoryService.setProductInventoryCache(productInventory); 
	}

	@Override
	public Integer getProductId() {
		return productId;
	}

}
