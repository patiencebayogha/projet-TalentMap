package com.viseo.talentmap.login.mock;

import com.viseo.talentmap.createaccount.manager.AccountManager;

/**
 * Created by patiencebayogha on 28/04/15.
 */
public class CreateAccountManager extends AccountManager {

    public static String[] INCORRECT_LOGINS = {"you", "you@you", "test@.com", "@you.com", "you@gmail.", "e..gmail@voila.com"};

    public static String LOGIN = "patience.bayo@viseo.com";

    public static String PASSWORD = "pass";


    public CreateAccountManager() {
        accountRetriever = new CreateAccountRetreiver();
    }
}
