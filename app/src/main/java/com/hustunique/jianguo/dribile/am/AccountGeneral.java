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
