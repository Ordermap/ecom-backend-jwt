package com.priyanka.Ecomwithjwt.entity;

import lombok.Data;

@Data

public class JwtRequest {
    private String userName;
    private String userPassword;
}
