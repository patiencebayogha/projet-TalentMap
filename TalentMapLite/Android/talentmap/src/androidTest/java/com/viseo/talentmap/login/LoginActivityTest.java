package com.viseo.talentmap.login;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.viseo.talentmap.R;
import com.viseo.talentmap.login.activity.LoginActivity;
import com.viseo.talentmap.login.mock.LoginMockManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by jvarin on 26/02/15.
 * modification by pbayogha 27/02/2015-00/03/2015
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginActivity mActivity;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    @Test
    public void checkPreconditions() {
        assertThat(mActivity, notNullValue());
        // Check that Instrumentation was correctly injected in setUp()
        assertThat(getInstrumentation(), notNullValue());
    }

    //@Test
    public void testErrorMessage() {
        getActivity();
        Espresso.onView(withText("Email et/ou mot de passe invalide(s)"));
    }

    //@Test
    public void connect() {
        Espresso.onView(withId(R.id.activity_new_login_email)).perform(ViewActions.typeText(LoginMockManager.LOGIN));
        Espresso.onView(withId(R.id.activity_new_login_password)).perform(ViewActions.typeText(LoginMockManager.INVALID_PASSWORD));
        Espresso.onView(withId(R.id.activity_new_login_connect)).perform(click());
        //  Espresso.onView(withId(R.id.activity_new_login_email)).check(matches(isDisplayed()));
        //verifier qu on va sur une nouvelle activité
        Espresso.onView(withText("Profil"));


    }


    @Test
    public void testClickConnectButton() {
        Espresso.onView(withId(R.id.activity_new_login_connect))
                .perform(click());


    }

    @Test
    public void testClickCreateAccountButton() {
        Espresso.onView(withId(R.id.activity_new_login_create_account))
                .check(matches(withText("S'inscrire")));
    }


    @Test
    public void testCheckBox() {
        //Check that the toggle button is checked.
        Espresso.onView(withId(R.id.activity_new_login_remember_me))
                .check(matches(isNotChecked()));


    }


    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
