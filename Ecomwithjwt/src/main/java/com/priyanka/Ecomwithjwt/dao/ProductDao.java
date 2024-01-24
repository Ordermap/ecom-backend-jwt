package com.priyanka.Ecomwithjwt.dao;

import com.priyanka.Ecomwithjwt.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ProductDao extends CrudRepository<Product,Integer> {

    List<Product> findAll(Pageable pageable);
}
