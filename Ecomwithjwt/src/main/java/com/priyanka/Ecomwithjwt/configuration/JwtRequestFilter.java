package com.priyanka.Ecomwithjwt.configuration;

import com.priyanka.Ecomwithjwt.service.JwtService;
import com.priyanka.Ecomwithjwt.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    public static String CURRENT_USER="";
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header=request.getHeader("Authorization" );
        System.out.println(header);
        String jwtToken=null;
        String userName=null;
        if(header!=null&&header.startsWith("Bearer ")){
            jwtToken=header.substring(7);
            try{
               userName=jwtUtil.getUserNameFromToken(jwtToken);
               CURRENT_USER=userName;

            }
            catch(IllegalArgumentException ex){
                System.out.println("unable to get jwt token");

            }
            catch(ExpiredJwtException ex){
                System.out.println("jwt token is expired");

            }
        }
        else{
            System.out.println("jwt token does not start with bearer");
        }
        try {
            if(userName!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails=jwtService.loadUserByUsername(userName);
                if(jwtUtil.validateToken(jwtToken,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,
                            null,
                            userDetails.getAuthorities()

                    );
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(request,response);

    }
}
