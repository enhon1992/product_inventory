package com.bbkj.mapper;

import com.bbkj.pojo.ProductInventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 库存数量Mapper
 * @author Administrator
 *
 */
@Mapper
public interface ProductInventoryMapper {

	/**
	 * 更新库存数量
	 * @param inventoryCnt 商品库存
	 */
	@Update("update product_inventory set inventory_cnt=#{inventoryCnt} where product_id=#{productId}")
	void updateProductInventory(ProductInventory productInventory);
	
	/**
	 * 根据商品id查询商品库存信息
	 * @param productId 商品id
	 * @return 商品库存信息
	 */
	@Select("select product_id as \"productId\",inventory_cnt as \"inventoryCnt\" from product_inventory where product_id=#{productId}")
	ProductInventory findProductInventory(@Param("productId") Integer productId);
	
}
