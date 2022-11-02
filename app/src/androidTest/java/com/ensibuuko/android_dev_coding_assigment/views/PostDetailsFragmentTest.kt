package com.ensibuuko.android_dev_coding_assigment.views

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ensibuuko.android_dev_coding_assigment.*
import com.ensibuuko.android_dev_coding_assigment.adapters.PostsAdapter
import org.hamcrest.CoreMatchers.allOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PostDetailsFragmentTest {
    private val navController = TestNavHostController(
        ApplicationProvider.getApplicationContext())

    @Before
    fun setUp() {
        val arguments = Bundle()
        arguments.putParcelable("post", dummyPost)
        arguments.putParcelable("user", dummyUser)
        arguments.putParcelableArray("comments", arrayOf(dummyComment))

        val scenario = launchFragmentInContainer<PostDetailsFragment>(fragmentArgs = arguments)

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.postDetailsFragment, arguments)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @Test
    fun testNavigationToAddComment() {
        onView(withId(R.id.add_comments)).perform(ViewActions.click())
        assertEquals(
            navController.currentDestination?.id,
            R.id.addCommentFragment
        )
    }

    @Test
    fun testPostDetailsLoaded() {
        onView(allOf(withId(R.id.title), withText("id labore ex et quam laborum")))
        onView(allOf(withId(R.id.body), withText("reiciendis et nam sapiente accusantium")))
        onView(withId(R.id.comments_list)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testNavigationToEditComment() {
        onView(withId(R.id.comments_list))
            .perform(
                RecyclerViewActions
                .actionOnItemAtPosition<PostsAdapter.PostItem>(0, clickItemWithId(R.id.editComment)))

        assertEquals(
            navController.currentDestination?.id,
            R.id.addCommentFragment
        )
    }

}