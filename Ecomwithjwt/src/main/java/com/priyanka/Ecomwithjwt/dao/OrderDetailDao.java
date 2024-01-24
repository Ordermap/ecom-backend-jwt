package com.priyanka.Ecomwithjwt.dao;

import com.priyanka.Ecomwithjwt.entity.OrderDetail;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailDao extends CrudRepository<OrderDetail,Integer> {
}
