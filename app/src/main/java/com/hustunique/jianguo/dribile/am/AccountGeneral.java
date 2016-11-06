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

package com.hustunique.jianguo.dribile.am;

/**
 * Created by JianGuo on 4/2/16.
 *
 */
public class AccountGeneral {
    /**
     * Account type id
     */
    public static final String ACCOUNT_TYPE = "com.hustunique.auth_dribile";

    /**
     * Account name
     */
    public static final String ACCOUNT_NAME = "Dribile";

    /**
     * Auth token types
     */
    public static final String AUTHTOKEN_TYPE_READ_ONLY = "public";
    public static final String AUTHTOKEN_TYPE_READ_ONLY_LABEL = "Grants read-only access to public information.";

    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "public write comment upload";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS_LABEL = "Full access to an Dribbble account";
}
