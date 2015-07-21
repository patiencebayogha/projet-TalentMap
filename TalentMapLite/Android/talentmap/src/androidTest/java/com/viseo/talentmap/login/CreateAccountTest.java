package com.viseo.talentmap.login;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;

import com.viseo.talentmap.R;
import com.viseo.talentmap.createaccount.activity.AccountActivity;
import com.viseo.talentmap.login.mock.CreateAccountManager;

import org.junit.Before;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by patiencebayogha on 28/04/15.
 */
public class CreateAccountTest extends ActivityInstrumentationTestCase2<AccountActivity> {



    private AccountActivity mActivity;

    public CreateAccountTest() {
        super(AccountActivity.class);
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



    @Test
    public void testErrorMessage() {
        getActivity();
        Espresso.onView(withText("L'email doit être Viseo ou Novedia"));
    }

    @Test
    public void inscrire() {
        Espresso.onView(withId(R.id.activity_new_account_email)).perform(ViewActions.typeText(String.valueOf(CreateAccountManager.INCORRECT_LOGINS)));
        Espresso.onView(withId(R.id.activity_new_account_password)).perform(ViewActions.typeText(CreateAccountManager.PASSWORD));
        Espresso.onView(withId(R.id.activity_new_account_next_step_button)).perform(click());
        //  Espresso.onView(withId(R.id.activity_new_login_email)).check(matches(isDisplayed()));
        //verifier qu on va sur une nouvelle activité



    }
    @Test
    public void testListGoesOverTheFold() {
        //text in display
        Espresso.onView(withText("Email")).check(matches(isDisplayed()));
        Espresso.onView(withText("Mot de passe")).check(matches(isDisplayed()));
        Espresso.onView(withText("Confirmer mot de passe")).check(matches(isDisplayed()));
        Espresso.onView(withText("name")).check(matches(isDisplayed()));
        Espresso.onView(withText("surname")).check(matches(isDisplayed()));

    }

    @Test
    public void buttonClicked() {

        Espresso.onView(withId(R.id.activity_new_account_next_step_button)).perform(click());}
}
