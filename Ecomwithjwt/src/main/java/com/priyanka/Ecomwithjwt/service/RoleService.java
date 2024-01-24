package com.priyanka.Ecomwithjwt.service;

import com.priyanka.Ecomwithjwt.dao.RoleDao;
import com.priyanka.Ecomwithjwt.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class RoleService {
    @Autowired
    private RoleDao roleDao;
    public Role createNewRole(Role role){
        return roleDao.save(role);

    }
}
