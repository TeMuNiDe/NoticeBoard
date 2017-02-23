package in.ac.jntuace.noticeboard;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class StaffTest {

    @Rule
    public ActivityTestRule<Navigator> mActivityTestRule = new ActivityTestRule<>(Navigator.class);

    @Test
    public void staffTest() {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.text_frame_bottom), withText("Staff"),
                        withParent(allOf(withId(R.id.activity_setup),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.engineering_ece), withText("Electronics And Communications Engineering"),
                        withParent(allOf(withId(R.id.choice_department),
                                withParent(withId(R.id.setup_bottom_constraint)))),
                        isDisplayed()));
        appCompatRadioButton.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.next), withText("Next"),
                        withParent(allOf(withId(R.id.activity_setup),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatRadioButton2 = onView(
                allOf(withId(R.id.position_adhoc), withText("Adhoc Teaching"),
                        withParent(allOf(withId(R.id.choice_position),
                                withParent(withId(R.id.setup_bottom_constraint)))),
                        isDisplayed()));
        appCompatRadioButton2.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.next), withText("Next"),
                        withParent(allOf(withId(R.id.activity_setup),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.main_board_list),
                        withParent(allOf(withId(R.id.swipe_layout),
                                withParent(withId(R.id.activity_notice_board)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        pressBack();

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.main_board_list),
                        withParent(allOf(withId(R.id.swipe_layout),
                                withParent(withId(R.id.activity_notice_board)))),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(1, click()));


        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton.perform(click());

    }

}
