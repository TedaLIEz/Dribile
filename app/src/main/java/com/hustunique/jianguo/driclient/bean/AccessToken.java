package com.hustunique.jianguo.driclient.bean;

/**
 * Created by JianGuo on 3/30/16.
 * Bean for access token, note that the first letter of <t>tokenType</t> should be uppercase
 */
public class AccessToken extends BaseBean {
    private String access_token;
    private String token_type;
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        if (!Character.isUpperCase(token_type.charAt(0))) {
            token_type = Character.toString(token_type.charAt(0)).toUpperCase()  + token_type.substring(1);
        }
        return token_type;
    }

    public String getScope() {
        return scope;
    }

    @Override
    public String toString() {
        return getToken_type() + " " + getAccess_token();
    }
}
