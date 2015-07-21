package com.viseo.talentmap.login.manager;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.login.retriever.LoginRetriever;


/**
 * Created by patiencebayogha on 09/02/15.
 * modification 1.0: 11/02/2015
 * Modification: 26 and 27.02.2015
 * manages the display et la DTO, Test verification
 */

public class LoginManager {

    protected LoginRetriever loginRetriever;

    public LoginManager() {
        loginRetriever = new LoginRetriever();
    }

    //method use to register a endpoint
    public boolean login(String login, String password) throws TalentMapException {


        //test if we do not enter  login or/and password
        if (login == null || password == null) {
            return false;
        }


        if (login.isEmpty() || password.isEmpty()) {
            return false;
        }

        if (!login.toUpperCase().matches("[A-Z0-9]+([.%+-]?[A-Z0-9-]+)*@[A-Z0-9.-]+\\.[A-Z]{2,4}")) {
            return false;
        }


        return loginRetriever.login(login, password);


    }


}
