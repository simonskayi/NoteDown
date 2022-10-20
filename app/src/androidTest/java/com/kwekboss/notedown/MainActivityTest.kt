package com.kwekboss.notedown
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/*
* Testing MainActivity
* ie- dependency for espresso
* */

@LargeTest
@RunWith(AndroidJUnit4::class)
internal class MainActivityTest {

    private lateinit var mainActivity:ActivityScenario<MainActivity>
  @Before
    fun setUp() {
        mainActivity = launchActivity()
       mainActivity.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testApplicationIsWorkingAsExpected(){
    onView(withId(R.id.fab_new_note)).perform(click())
        val title = "Expresso Test"
        val body = "Test Case Passed.Great News!"

        onView(withId(R.id.edtv_note_head)).perform(ViewActions.typeText(title))
        Espresso.closeSoftKeyboard()

       onView(withId(R.id.edtv_note_body)).perform(ViewActions.typeText(body))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.fab_save_note)).perform(click())

        onView(withId(R.id.searchView)).perform(ViewActions.typeText(title))

        Espresso.closeSoftKeyboard()
        onView(withText(R.id.textView)).check(matches(isDisplayed()))


       // onView(wi("Note")).check(matches(isDisplayed()))
    }
}