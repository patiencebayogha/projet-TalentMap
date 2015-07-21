package com.viseo.talentmap.login;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.test.ActivityInstrumentationTestCase2;

import com.viseo.talentmap.R;
import com.viseo.talentmap.lite.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by patiencebayogha on 04/03/15.
 */
public class FragmentTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mActivity;
    String email;
    public static String EMAIL = "email";
    public FragmentTest() {

        super(MainActivity.class);

    }



    @Before
    public void setUp() throws Exception {
        super.setUp();

        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        // Espresso will not launch our activity for us, we must launch it via getActivity().
        mActivity = getActivity();
    }


    @Test
    public void checkPreconditions() {
        assertThat(mActivity, notNullValue());
        // Check that Instrumentation was correctly injected in setUp()
        assertThat(getInstrumentation(), notNullValue());
    }

    @Test
    public void testDrawerClick() {
        Espresso.onView(withId(R.id.drawer_layout)).perform(click());   //Find drawer_layout and click
        Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());//Open menu
        Espresso.onView(withText("Profil"));   //Find the text
        //Espresso.onData(instanceOf(MainActivity.class))
        //   .inAdapterView(allOf(withId(R.id.list_item)))
        //  .atPosition(0)
        // .perform(click());

    }




    //@Test
    public void closeKeyboard() {
        //Close Keyboard after type
        Espresso.onView(withId(R.id.lastName_users))
                .perform(typeText("Patience"), closeSoftKeyboard());

        Espresso.onView(withId(R.id.name_users))
                .perform(typeText("Bayogha"), closeSoftKeyboard());

    }

    public void testClickShowFragmentButtonTestFragmentShown() {
        //get the text which the fragment shows
        ViewInteraction fragmentText = Espresso.onView(withId(R.id.fragment_profil));

        //click the button to show the fragment
        Espresso.onView((withId(R.id.fragment_profil))).perform(click());

        ViewInteraction fragmentText1 = Espresso.onView(withId(R.id.fragment_search));

        //check the fragments text is now visible in the activity
        fragmentText.check(matches(isDisplayed()));
    }

    @Test
    public void clicButtonAddSkill() {
        // Clicking launches a new activity that shows the text entered above.
        Espresso.onView(withId(R.id.activity_skills_image_button_plus)).perform(click());
        Espresso.onView(withText("Notion"));
        Espresso.onView(withId(R.id.activity_sign_in_rating_bar_lvl_1_block))
                .perform(click());

    }

    @Test
    public void addInProfil() {
        Espresso.onView(withId(R.id.activity_skills_image_button_add)).perform(click());
        Espresso.onView(withText("Ajouter une compétence"));

    }


    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
