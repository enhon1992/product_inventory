package com.bbkj.service.impl;
import com.bbkj.dao.RedisDao;
import com.bbkj.exception.NullParamExcetion;
import com.bbkj.mapper.ProductInventoryMapper;
import com.bbkj.pojo.ProductInventory;
import com.bbkj.service.ProductInventoryService;
import com.bbkj.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 商品库存Service实现类
 * @author Administrator
 *
 */
@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {

	@Autowired
	private ProductInventoryMapper productInventoryMapper;
	@Autowired
	private RedisDao redisDAO;

	@Value("${inventory.project.inventory.redis.key.prefix}")
	private String inventory_key_prefix;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateProductInventory(ProductInventory productInventory) {
		if(productInventory!=null&&productInventory.getProductId()!=null){
			productInventoryMapper.updateProductInventory(productInventory);
		}else{
			throw new NullParamExcetion("修改商品库存信息,productInventory和productid不能为NULL");
		}
	}

	/**
	 * 根据商品id查询商品库存
	 * @param productId 商品id
	 * @return 商品库存
	 */
	@Override
	@Transactional(rollbackFor = Exception.class,readOnly = true)
	public ProductInventory findProductInventory(Integer productId) {
		if(productId!=null){
			return productInventoryMapper.findProductInventory(productId);
		}else{
			return null;
		}

	}
	@Override
	public void removeProductInventoryCache(ProductInventory productInventory) {
		if(productInventory!=null&&productInventory.getProductId()!=null){
			String key = inventory_key_prefix + productInventory.getProductId();
			redisDAO.del(key);
		}else{
			throw new NullParamExcetion("清除缓存中商品库存信息,productInventory和productid不能为NULL");
		}

	}
	
	/**
	 * 设置商品库存的缓存
	 * @param productInventory 商品库存
	 */
	@Override
	public void setProductInventoryCache(ProductInventory productInventory) {
		if(productInventory!=null&&productInventory.getProductId()!=null){
			String key = inventory_key_prefix + productInventory.getProductId();
			redisDAO.set(key, JsonUtils.objectToJson(productInventory));
		}else{
			throw new NullParamExcetion("往缓存中添加商品库存信息,productInventory和productid不能为NULL");
		}
	}

	@Override
	public ProductInventory getProductInventoryCache(Integer productId) {
		if(productId!=null){
			String res=redisDAO.get(inventory_key_prefix +productId);
			if(res!=null){
				return JsonUtils.jsonToPojo(res,ProductInventory.class);
			}else{
				return null;
			}
		}else{
			return  null;
		}
	}


}
