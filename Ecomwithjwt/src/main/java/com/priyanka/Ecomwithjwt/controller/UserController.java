package com.priyanka.Ecomwithjwt.controller;

import com.priyanka.Ecomwithjwt.entity.User_;
import com.priyanka.Ecomwithjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @PostConstruct
    public void initRolesandUsers(){
        userService.initRolesandUsers();
    }

    @PostMapping({"/registerNewUser"})
    public User_ registerNewUser(@RequestBody User_ user_){
        return userService.registerNewUser(user_);

    }
    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "this url is only accessible to the admin";
    }
    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "this url is only accessible to the user";
    }



}
