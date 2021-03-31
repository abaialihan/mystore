package com.abai.mystore.security.jwt.Exeptions;


import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationExeptions extends AuthenticationException {

    public JwtAuthenticationExeptions(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtAuthenticationExeptions(String msg) {
        super(msg);
    }
}
