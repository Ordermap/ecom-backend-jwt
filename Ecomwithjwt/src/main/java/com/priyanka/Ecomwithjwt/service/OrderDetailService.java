package com.priyanka.Ecomwithjwt.service;

import com.priyanka.Ecomwithjwt.configuration.JwtRequestFilter;
import com.priyanka.Ecomwithjwt.dao.OrderDetailDao;
import com.priyanka.Ecomwithjwt.dao.ProductDao;
import com.priyanka.Ecomwithjwt.dao.UserDao;
import com.priyanka.Ecomwithjwt.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class OrderDetailService {
    private static final String ORDER_PLACED="placed";
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;
    public void placeOrder(OrderInput orderInput){
        List<OrderProductQuantity>productQuantityList=orderInput.getOrderProductQuantityList();
        for(OrderProductQuantity o:productQuantityList){
            Product product =productDao.findById(o.getProductId()).get();
            String currentUser= JwtRequestFilter.CURRENT_USER;
            User_ user_=userDao.findById(currentUser).get();
            OrderDetail orderDetail=new OrderDetail(
                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    orderInput.getAlternateContactNumber(),
                    ORDER_PLACED,
                    product.getProductActualPrice()*o.getQuantity(),
                    product,
                    user_

            );
            orderDetailDao.save(orderDetail);

        }


    }
}
