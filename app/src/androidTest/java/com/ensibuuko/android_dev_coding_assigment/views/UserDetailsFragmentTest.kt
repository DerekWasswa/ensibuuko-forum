package com.ensibuuko.android_dev_coding_assigment.views

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.dummyUser
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDetailsFragmentTest {

    private val navController = TestNavHostController(
        ApplicationProvider.getApplicationContext())

    @Before
    fun setUp() {
        val arguments = Bundle()
        arguments.putParcelable("user", dummyUser)
        val scenario = launchFragmentInContainer<UserDetailsFragment>(fragmentArgs = arguments)

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.userDetailsFragment, arguments)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @Test
    fun testUserDetailsLoaded() {
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.name),
                ViewMatchers.withText("Leanne Graham")
            )
        )
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.username),
                ViewMatchers.withText("Bret")
            )
        )
    }

}