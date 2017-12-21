package com.bbkj.controller;

import com.bbkj.pojo.Product;
import com.bbkj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/product/add")
    public String addProduct(@RequestParam(value = "pname",required = true)String pname,@RequestParam(value = "type",required = true)String type,@RequestParam(value = "price",required = true)Double price){
        Product pro=new Product();
        pro.setCreateTime(new Timestamp(System.currentTimeMillis()));
        pro.setPname(pname);
        pro.setPrice(price);
        pro.setType(type);
        this.productService.addProduct(pro);
        return  "SUCCESS";
    }
}
