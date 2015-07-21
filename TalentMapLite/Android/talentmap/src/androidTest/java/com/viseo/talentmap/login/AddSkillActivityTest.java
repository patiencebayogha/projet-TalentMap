package com.viseo.talentmap.login;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.test.ActivityInstrumentationTestCase2;

import com.viseo.talentmap.R;
import com.viseo.talentmap.modifications_skills.activity.SkillsActivity;

import org.junit.Before;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by patiencebayogha on 24/04/15.
 */
public class AddSkillActivityTest extends ActivityInstrumentationTestCase2<SkillsActivity> {

    private SkillsActivity mActivity;

    public AddSkillActivityTest() {
        super(SkillsActivity.class);
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
    public void testListGoesOverTheFold() {
        //text in display
        Espresso.onView(withText("Choisir une catégorie")).check(matches(isDisplayed()));
        Espresso.onView(withText("Choisir une compétence")).check(matches(isDisplayed()));
        Espresso.onView(withText("Notions")).check(matches(isDisplayed()));
        Espresso.onView(withText("Bases")).check(matches(isDisplayed()));
        Espresso.onView(withText("Maîtrise")).check(matches(isDisplayed()));
        Espresso.onView(withText("Bonne maîtrise")).check(matches(isDisplayed()));
        Espresso.onView(withText("Expert")).check(matches(isDisplayed()));
        Espresso.onView(allOf(withId(R.id.spinner), withText("Choisissez une catégorie")));
        Espresso.onView(withId(R.id.activity_skills_expertises)).check(matches(withHint("Choisissez une compétence")));
    }

    @Test
    public void buttonClicked() {

        Espresso.onView(withId(R.id.activity_sign_in_rating_bar_lvl_1_block)).perform(click());
        Espresso.onView(withId(R.id.activity_sign_in_rating_bar_lvl_2_block)).perform(click());
        Espresso.onView(withId(R.id.activity_sign_in_rating_bar_lvl_3_block)).perform(click());
        Espresso.onView(withId(R.id.activity_sign_in_rating_bar_lvl_4_block)).perform(click());
        Espresso.onView(withId(R.id.activity_skills_image_button_add)).perform(click());
        Espresso.onView(withText("Profil"));
    }


    @Test
    public void spinnerItemSelect() {
        Espresso.onView(withId(R.id.spinner)).perform(click());     // Click on the Spinner to open the item selection
        /*
        *Click on the item
         */
        Espresso.onData(allOf(is(instanceOf(String.class)), is("Management"))).perform(click());
        Espresso.onData(allOf(is(instanceOf(String.class)), is("Langues"))).perform(click());
        Espresso.onData(allOf(is(instanceOf(String.class)), is("Loisirs"))).perform(click());
        Espresso.onData(allOf(is(instanceOf(String.class)), is("Bureautique"))).perform(click());
        Espresso.onData(allOf(is(instanceOf(String.class)), is("Developpement"))).perform(click());


    }

    @Test
    public void autocompleteText() {
        Espresso.onView(withId(R.id.activity_skills_expertises)).perform(click());
        /*
            *Verify that the TextView contains the String
         */

      Espresso.onView(withId(R.id.activity_skills_expertises))
                .check(matches(withText(containsString("Anglais"))));
        Espresso.onView(withId(R.id.activity_skills_expertises))
                .perform(typeText("Anglais"), closeSoftKeyboard());
    }
}