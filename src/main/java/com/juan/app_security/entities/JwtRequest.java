package com.juan.app_security.entities;

import lombok.Data;

@Data
public class JwtRequest {

    private String username;
    private String password;

}
