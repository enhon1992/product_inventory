package com.bbkj.service;

import com.bbkj.mapper.ProductMapper;
import com.bbkj.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface ProductService {
    public void addProduct(Product product);
}
