package com.kenoly.udemy.security;

import com.kenoly.udemy.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
//    public static final String TOKEN_SECRET = "jfgi4jgu83nfl0";
    
    public static String getTokenSecret() {
    	AppProperties props = (AppProperties) SpringApplicationContext.getBean("appProperties");
    	return props.getTokenSecret();
    }

}
