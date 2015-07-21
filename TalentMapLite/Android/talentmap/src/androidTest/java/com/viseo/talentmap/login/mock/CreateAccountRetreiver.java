package com.viseo.talentmap.login.mock;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.createaccount.retriever.AccountRetriever;

/**
 * Created by patiencebayogha on 28/04/15.
 */
public class CreateAccountRetreiver extends AccountRetriever{
    public boolean create(String email, String password, String name, String surname) throws TalentMapException {
        return password.equals(CreateAccountManager.INCORRECT_LOGINS);
    }
}
