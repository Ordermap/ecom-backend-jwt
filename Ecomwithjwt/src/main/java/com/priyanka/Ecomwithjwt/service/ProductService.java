package com.priyanka.Ecomwithjwt.service;

import com.priyanka.Ecomwithjwt.dao.ProductDao;
import com.priyanka.Ecomwithjwt.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductDao productDao;
    public Product addNewProduct(Product product){
        log.debug(product.toString());
        return productDao.save(product);

    }
    public List<Product> getAllProducts(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber,2 );


            return (List<Product>) productDao.findAll(pageable);


    }
    public void deleteProductDetails(Integer productId){
        productDao.deleteById(productId);
    }
    public Product getProductDetailsById(Integer id){
        return productDao.findById(id).get();

    }
    public List<Product> getProductDetails(boolean isSingleProductCheckout,Integer productId){
        if(isSingleProductCheckout){
            List<Product> list=new ArrayList<>();
            Product product=productDao.findById(productId).get();
            list.add(product);
            return list;
        }
        else {

        }
        return new ArrayList<>();

    }



}
