package com.ensibuuko.android_dev_coding_assigment.views

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.adapters.PostsAdapter
import com.ensibuuko.android_dev_coding_assigment.clickItemWithId
import org.hamcrest.Matchers
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class PostsFragmentTest {

    @Test
    fun testNavigationToAddPost() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        val scenario = launchFragmentInContainer<PostsFragment>()

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.add_post_btn)).perform(ViewActions.click())

        assertEquals(
            navController.currentDestination?.id,
            R.id.addPostFragment
        )
    }

    @Test
    fun testPostsLoaded() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        val scenario = launchFragmentInContainer<PostsFragment>()

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.shimmer_view_container)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
        onView(withId(R.id.posts_list)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testPostsItemClick() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        val scenario = launchFragmentInContainer<PostsFragment>()

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.posts_list))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<PostsAdapter.PostItem>(0, clickItemWithId(R.id.postCard)))

        assertEquals(
            navController.currentDestination?.id,
            R.id.postDetailsFragment
        )
    }

    @Test
    fun testNavigationPostsToEdit() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        val scenario = launchFragmentInContainer<PostsFragment>()

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.posts_list))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<PostsAdapter.PostItem>(0, clickItemWithId(R.id.editPost)))

        assertEquals(
            navController.currentDestination?.id,
            R.id.addPostFragment
        )
    }

}