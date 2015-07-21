package com.viseo.talentmap.login;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.login.mock.LoginMockManager;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by jvarin on 23/02/15.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class LoginManagerTest {

    private LoginMockManager loginManager;

    @Before
    public void setUp() {
        loginManager = new LoginMockManager();
    }

    @Test
    public void nullLoginAndPassword()  throws TalentMapException{

        boolean result = loginManager.login(null, null);
        Assert.assertFalse(result);
    }

    @Test
    public void nullLogin()  throws TalentMapException {

        boolean result = loginManager.login(null, LoginMockManager.PASSWORD);
        Assert.assertFalse(result);
    }

    @Test
    public void nullPassword() throws TalentMapException {

        boolean result = loginManager.login(LoginMockManager.LOGIN, null);
        Assert.assertFalse(result);
    }

    @Test
    public void emptyLoginAndPassword() throws TalentMapException {

        boolean result = loginManager.login(new String(), new String());
        Assert.assertFalse(result);
    }

    @Test
    public void emptyLogin() throws TalentMapException{

        boolean result = loginManager.login(new String(), LoginMockManager.PASSWORD);
        Assert.assertFalse(result);
    }

    @Test
    public void emptyPassword()  throws TalentMapException{

        boolean result = loginManager.login(LoginMockManager.LOGIN, new String());
        Assert.assertFalse(result);
    }

    @Test
    public void incorrectLogin()  throws TalentMapException{

        for (String incorrectLogin : LoginMockManager.INCORRECT_LOGINS) {
            boolean result = loginManager.login(incorrectLogin, LoginMockManager.PASSWORD);
            Log.e("bob", "inc : " + incorrectLogin);
            Assert.assertFalse(result);
        }
    }

    @Test
    public void invalidPassword()  throws TalentMapException{

        boolean result = loginManager.login(LoginMockManager.LOGIN, LoginMockManager.INVALID_PASSWORD);
        Assert.assertFalse(result);
    }

    @Test
    public void correctLogin()  throws TalentMapException{

        boolean result = loginManager.login(LoginMockManager.LOGIN, LoginMockManager.PASSWORD);
        Assert.assertTrue(result);
    }


}
