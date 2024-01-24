package com.priyanka.Ecomwithjwt.entity;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtResponse {

    private User_ user;
    private String jwtToken;

}
