package com.priyanka.Ecomwithjwt.dao;

import com.priyanka.Ecomwithjwt.entity.User_;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User_,String> {
}
