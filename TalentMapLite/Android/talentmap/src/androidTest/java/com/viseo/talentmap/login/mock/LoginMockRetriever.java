package com.viseo.talentmap.login.mock;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.login.retriever.LoginRetriever;

/**
 * Created by jvarin on 26/02/15.
 */
public class LoginMockRetriever extends LoginRetriever {

    @Override
    public boolean login(String login, String password) throws TalentMapException {
        return password.equals(LoginMockManager.PASSWORD);
    }
}
