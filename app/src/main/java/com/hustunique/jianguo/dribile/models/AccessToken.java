/*
 * Copyright (c) 2016.  TedaLIEz <aliezted@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hustunique.jianguo.dribile.models;

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

    /**
     * Scopes let you specify exactly what type of access you need. Scopes limit access for OAuth tokens. They do not grant any additional permission beyond that which the user already has.
     * Your application can request the scopes in the initial redirection. You can specify multiple scopes by separating them with a space,
     * default is public
     * @return the scopes
     */
    public String getScope() {
        return scope;
    }

    /**
     * Get the accessToken with the tokenType
     * example : Bearer ACCESS_TOKEN
     * @return the accessToken with the tokenType
     */
    @Override
    public String toString() {
        return getToken_type() + " " + getAccess_token();
    }
}
