package com.priyanka.Ecomwithjwt.service;

import com.priyanka.Ecomwithjwt.dao.RoleDao;
import com.priyanka.Ecomwithjwt.dao.UserDao;
import com.priyanka.Ecomwithjwt.entity.Role;
import com.priyanka.Ecomwithjwt.entity.User_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.Set;

@Service

public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User_ registerNewUser(User_ user_){
        Role role=roleDao.findById("User").get();
        Set<Role>roles=new HashSet<>();
        roles.add(role);
        user_.setUserPassword(getEncodedPassword(user_.getUserPassword()));
        user_.setRole(roles);
        return userDao.save(user_);

    }
    public void initRolesandUsers(){
        Role adminRole=new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);



        Role userRole=new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);


        User_ adminUser=new User_();
        adminUser.setUserName("admin123");
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        Set<Role> adminroles=new HashSet<>();
        adminroles.add(adminRole);
        adminUser.setRole(adminroles);
        userDao.save(adminUser);


//        User_ user=new User_();
//        user.setUserName("raj123");
//        user.setUserFirstName("raj");
//        user.setUserLastName("sharma");
//        user.setUserPassword("raj@pass");
//        Set<Role> userroles=new HashSet<>();
//        userroles.add(userRole);
//        user.setRole(userroles);
//        userDao.save(user);

    }
    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }
}
