package com.bbkj.controller;

import com.bbkj.exception.NullParamExcetion;
import com.bbkj.pojo.ProductInventory;
import com.bbkj.request.InventoryRequest;
import com.bbkj.request.impl.ProductInventoryCacheRefreshRequest;
import com.bbkj.request.impl.ProductInventoryDBUpdateRequest;
import com.bbkj.service.ProductInventoryRequestAsyncProcessService;
import com.bbkj.service.ProductInventoryService;
import com.bbkj.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品库存Controller
 * @author Administrator
 *
 */
@Controller
public class ProductInventoryController {

    @Autowired
    private ProductInventoryRequestAsyncProcessService productInventoryRequestAsyncProcessService;
    @Autowired
    private ProductInventoryService productInventoryService;

    /**
     * 更新商品库存  不需要特殊的处理 只需要把封装好的商品库存请求信息 然后添加到内存队列中
     */
    @PostMapping("/product_inventory/update_product_inventory")
    @ResponseBody
    public Response updateProductInventory(ProductInventory productInventory) {
        if(null!=productInventory&&null!=productInventory.getProductId()&&productInventory.getProductId()>0){
            Response response = null;
            try {
                InventoryRequest request = new ProductInventoryDBUpdateRequest(productInventory, productInventoryService);
                productInventoryRequestAsyncProcessService.process(request);
                response = new Response(Response.SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                response = new Response(Response.FAILURE);
            }
            return response;
        }else{
            throw new NullParamExcetion("更新商品库存controller 参数不满足条件");
        }
    }

    /**
     * 获取商品库存  需要特殊的处理 有可能需要hang住一会
     */
    @RequestMapping("/product_inventory/get_product_inventory")
    @ResponseBody
    public ProductInventory getProductInventory(Integer productId) {
        if(null!=productId&&productId>=0){
            ProductInventory productInventory = null;
            try {
                InventoryRequest request = new ProductInventoryCacheRefreshRequest(
                        productId, productInventoryService);
                productInventoryRequestAsyncProcessService.process(request);

                // 将请求扔给service异步去处理以后，就需要while(true)一会儿，在这里hang住
                // 去尝试等待前面有商品库存更新的操作，同时缓存刷新的操作，将最新的数据刷新到缓存中
                long startTime = System.currentTimeMillis();
                long endTime = 0L;
                long waitTime = 0L;

                // 等待超过200ms没有从缓存中获取到结果
                while(true) {
                    if(waitTime > 200) {
                        break;
                    }

                    // 尝试去redis中读取一次商品库存的缓存数据
                    productInventory = productInventoryService.getProductInventoryCache(productId);

                    // 如果读取到了结果，那么就返回
                    if(productInventory != null) {
                        return productInventory;
                    }

                    // 如果没有读取到结果，那么等待一段时间
                    else {
                        Thread.sleep(20);
                        endTime = System.currentTimeMillis();
                        waitTime = endTime - startTime;
                    }
                }

                // 直接尝试从数据库中读取数据
                productInventory = productInventoryService.findProductInventory(productId);
                if(productInventory != null) {
                    return productInventory;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return new ProductInventory(productId, -1L);
        }else {
            throw new NullParamExcetion("获取商品库存controller 参数不满足条件");
        }
    }
}
