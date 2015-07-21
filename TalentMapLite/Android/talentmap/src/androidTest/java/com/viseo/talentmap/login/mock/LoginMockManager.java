package com.viseo.talentmap.login.mock;

import com.viseo.talentmap.login.manager.LoginManager;

/**
 * Created by jvarin on 26/02/15.
 */
public class LoginMockManager extends LoginManager {

    public static String[] INCORRECT_LOGINS = {"test", "test@test", "test@.com", "@test.com", "test@test.", "e..test@test.com"};

    public static String LOGIN = "patience.bayogha@viseo.com";

    public static String PASSWORD = "pass";

    public static String INVALID_PASSWORD = "invalid_password";


    public LoginMockManager() {
        loginRetriever = new LoginMockRetriever();
    }
}
