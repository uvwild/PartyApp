package org.funtime.partyapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by uv on 16/01/2016.
 */

//@LargeTest
@RunWith(AndroidJUnit4.class)
public class ClickFlowTest {

    private String mAccesscode = "doedel@mail.com";
    private String mPassword  = "12345678" ;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void doLogin() {
        // Type text and then press the button.
        onView(withId(R.id.email)).perform(typeText(mAccesscode), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(mPassword), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());

        // Check that the text was changed.
        //onView(withId(R.id.textToBeChanged)).check(matches(withText(mAccesscode)));
    }


}
