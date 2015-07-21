package com.viseo.talentmap.login.retriever;

import com.viseo.talentmap.common.exception.TalentMapAuthenticateException;
import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.common.retriever.AbstractRetriever;
import com.viseo.talentmap.common.service.AuthService;
import com.viseo.talentmap.login.data.LoginUser;

/**
 * Created by patiencebayogha on 12/02/15.
 * modification: 13/02/15.
 * Manage passing POSO to JSON
 * This class download JSON
 */
public class LoginRetriever extends AbstractRetriever {

    protected AuthService authService;

    public LoginRetriever() {
        authService = new AuthService();
    }


    public boolean login(String login, String password) throws TalentMapException {

        LoginUser loginUser = new LoginUser();
        loginUser.setEmail(login);
        loginUser.setPassword(password);
        String param = jsonStream.getJSON(loginUser);

        try {
            authService.login(param);
        } catch (TalentMapAuthenticateException e) {
            return false;
        }
        return true;
    }


}

