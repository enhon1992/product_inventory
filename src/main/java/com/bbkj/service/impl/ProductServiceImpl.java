package com.bbkj.service.impl;

import com.bbkj.exception.NullParamExcetion;
import com.bbkj.mapper.ProductMapper;
import com.bbkj.pojo.Product;
import com.bbkj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addProduct(Product product){
        if(product!=null){
            this.productMapper.add(product);
        }else{
            throw new NullParamExcetion("添加Porduct,product mode 不能为NULL");
        }
    }
}
