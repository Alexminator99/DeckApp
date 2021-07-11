package com.everything.deckapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Test
import org.junit.runner.RunWith


@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun urlPassedAndCardViewDisplayed_DisplayedInUi() {
        // GIVEN - A new URL to check
        val URL = "Ans5 (private)(\"secret-key: \$2b\$10\$ppaKYa7yQfoGUU1hMQXfwevzkNbovRWLWbV4O0NKiTGUcRgcUJAaG\")\n" +
                "https://api.jsonbin.io/b/60e342b39328b059d7b7801e"

        // WHEN - Details fragment launched to display task
        ActivityScenario.launch(MainActivity::class.java)

        // THEN - Task details are displayed on the screen
        // make sure that the title/description are both shown and correct
        onView(withId(R.id.text_input_edit_text_url))
            .perform(ViewActions.typeText(URL))
        onView(withId(R.id.materialButtonBeginTest))
            .perform(ViewActions.click()).perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.answerCardLayout))
            .check(matches(isDisplayed()))
    }

    @Test
    fun urlMalformedPassedAndCardViewIsNotDisplayed_DisplayedInUi() {
        // GIVEN - A new URL to check
        val URL = "Ans5 (private)(\"secret-key: \$2b\$10\$ppaKYa7yQfoGUU1hMQXfwevzkNbovRWLWbV4O0NKiTGUcRgcUJAaG\")\n" +
                "haefaefttps:api.jsonbin.io//60e342b39328b059d7b7801e"

        // WHEN - Details fragment launched to display task
        ActivityScenario.launch(MainActivity::class.java)

        // THEN - Task details are displayed on the screen
        // make sure that the title/description are both shown and correct
        onView(withId(R.id.text_input_edit_text_url))
            .perform(ViewActions.typeText(URL))
        onView(withId(R.id.materialButtonBeginTest))
            .perform(ViewActions.click()).perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.answerCardLayout))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }
}
