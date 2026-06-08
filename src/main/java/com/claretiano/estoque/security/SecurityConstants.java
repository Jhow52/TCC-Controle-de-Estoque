package com.claretiano.estoque.security;

public class SecurityConstants {

    static final String SECRET = "z0fGMOBNECCSxGBGaXI11XkYENrg9U3jSgBn82AYY7pLNgWNbMf446JLIwZ6a5jXx4rda+eOj8IDMiEnsIWpwg==";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final String SIGN_UP_URL = "/auth/register";
    static final long EXPIRATION_TIME = 86400000L;
}
