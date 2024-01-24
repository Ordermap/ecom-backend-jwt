package com.priyanka.Ecomwithjwt.service;

import com.priyanka.Ecomwithjwt.dao.UserDao;
import com.priyanka.Ecomwithjwt.entity.JwtRequest;
import com.priyanka.Ecomwithjwt.entity.JwtResponse;
import com.priyanka.Ecomwithjwt.entity.User_;
import com.priyanka.Ecomwithjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service

public class JwtService implements UserDetailsService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthenticationManager authenticationManager;
    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception{
        String userName=jwtRequest.getUserName();
        String userPassword=jwtRequest.getUserPassword();
        authenticate(userName,userPassword);
        final UserDetails userDetails=loadUserByUsername(userName);
       String jwtToken= jwtUtil.generateToken(userDetails);
        User_ user=userDao.findById(userName).get();
        return new JwtResponse(user,jwtToken);

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User_ user =userDao.findById(username).orElse(null);
        if(user!=null) {
            return new User(user.getUserName(), user.getUserPassword(),
                    getAuthorities(user)
            );
        }
        else{
            throw new UsernameNotFoundException("Username is not valid");
        }
    }
    private void authenticate(String userName,String userPassword) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,userPassword));
        }
        catch(DisabledException e){
            throw new Exception("user is disabled");

        }
        catch(BadCredentialsException e){
            throw new Exception("bad credentials from user");

        }

    }
    private Set getAuthorities(User_ user_){
        Set authorities=new HashSet();
        user_.getRole().forEach(role->
                {
                  authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
                }
        );
       return authorities;

    }
}
