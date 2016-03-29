package com.hustunique.jianguo.driclient.bean;

/**
 * Created by JianGuo on 3/29/16.
 * POJO for Api_key
 */
public class ApiKey {
    private String access_token;

    private String token_type;

    private String scope;


    public ApiKey(){}

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
