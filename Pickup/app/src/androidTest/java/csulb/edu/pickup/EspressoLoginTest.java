package csulb.edu.pickup;

/**
 * Created by Brain on 5/11/2016.
 */

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.core.deps.guava.base.Optional;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.KeyEvent;
import android.widget.EditText;
import android.app.FragmentManager;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoLoginTest {


    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void myTest()
    {
        login();
        map();
        createEvent();
    }

    public void login() {
        // Type text and then press the button.
        onView(withId(R.id.username)).perform(ViewActions.typeText("b"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(ViewActions.typeText("b"), closeSoftKeyboard());
        onView(withId(R.id.login_btn)).perform(scrollTo(), click());
    }


    public void map()
    {
        onView(withId(R.id.map_container)).check(matches(isDisplayed()));
        onView(withId(R.id.map_plus_button)).perform(click());
    }

    public void createEvent()
    {
        String eventName = "Event1";
        String sport = "Basketball";
        String location = "1616 E Anaheim St, Long Beach, CA 90813";
        String date = "03-10-2016";
        String time = "06:00 PM";
        String gender = "Male";
        String ageMin = "5";
        String ageMax = "50";
        String maxPpl = "10";
        String minRating = "-1";

        onView(withId(R.id.create_event_name)).perform(ViewActions.typeText(eventName), closeSoftKeyboard());

        onView(withId(R.id.create_event_sport_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(sport))).perform(click());

        onView(withId(R.id.create_event_location)).perform(ViewActions.typeText(location), closeSoftKeyboard());

        onView(withId(R.id.create_event_date)).perform(ViewActions.replaceText(date), closeSoftKeyboard());

        onView(withId(R.id.create_event_time)).perform(ViewActions.replaceText(time), closeSoftKeyboard());

        onView(withId(R.id.create_event_gender_spinner)).perform(click());
//        onData(allOf(is(instanceOf(String.class)), is(gender))).perform(click());

//        onView(withId(R.id.age_min_spinner)).perform(click());
//        onData(allOf(is(instanceOf(String.class)), is(ageMin))).perform(click());
//
//        onView(withId(R.id.age_max_spinner)).perform(click());
//        onData(allOf(is(instanceOf(String.class)), is(ageMax))).perform(click());
//
//        onView(withId(R.id.max_num_ppl_spinner)).perform(click());
//        onData(allOf(is(instanceOf(String.class)), is(maxPpl))).perform(click());
//
//        onView(withId(R.id.min_user_rating_spinner)).perform(click());
//        onData(allOf(is(instanceOf(String.class)), is(minRating))).perform(click());
//
//        onView(withId(R.id.event_create_btn)).perform(click());
    }
}




// SAMPLE CODE TO GO OFF OF

// check that the user was passed through the bundle
//        intended(toPackage("csulb.edu.pickup.MapsFragment"));
//        intended(hasExtra("USER", User.class));

// check to see if the map was loaded
//        onView(withId(R.id.map_container)).check(matches(isDisplayed()));

// search for an event
//        onView(withId(R.id.menu_search)).perform(click());
//        onView(isAssignableFrom(EditText.class)).perform(typeText("abcxyz"), pressKey(KeyEvent.KEYCODE_ENTER));
//        // check if the correct event was retrieved
//        onView(withId(R.id.viewLinearLayout1)).check(matches(isDisplayed()));
//        onView(withId(R.id.event_view_name)).check(matches(withText("abcxyz")));

// go back to map
//        pressBack();
//        FragmentManager fm = getActivity().getSupportFragmentManager();
//        fm.popBackStack();

// create an event
//
// onView(withId(R.id.map_plus_button)).perform(click());
//onView((withId(R.id.map_plus_button))).perform(click());
//onView(withId(R.id.eventScrollView1)).check(matches(isDisplayed()));
// fill out the create event form