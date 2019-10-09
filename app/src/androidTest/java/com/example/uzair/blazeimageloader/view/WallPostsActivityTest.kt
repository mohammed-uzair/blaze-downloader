package com.example.uzair.blazeimageloader.view


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.uzair.blazeimageloader.R
import com.example.uzair.blazeimageloader.adapter.WallPostsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@LargeTest
class WallPostsActivityTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(WallPostsActivity::class.java)

    private var wallPostsActivity: WallPostsActivity? = null

    /**
     * Setup all the testing resources used for this class to perform testing
     */
    @Before
    fun setUp() {
        wallPostsActivity = activityTestRule.activity
    }

    /**
     * Test if the activity is created
     */
    @Test
    fun testIfActivityIsLaunched() {
        val rootRecyclerView =
            wallPostsActivity?.findViewById<RecyclerView>(R.id.main_recycler_view)

        //Test if this view is created
        Assert.assertNotNull(rootRecyclerView)
    }

    /**
     * Test if the recycler view is created after a delay,
     * will also checks if the data is/was loaded from the data source
     */
    @Test
    fun testIfRecyclerListViewIsFilledWithData() {
        /*Wait for some time until the data is fetched from the data source,
        and then check if the recycler view is filled with the data*/
        CoroutineScope(Dispatchers.Main).launch {
            //Wait for 10 seconds
            delay(10_000)

            //Check if the recycler view has any data
            val rootRecyclerView: RecyclerView? =
                wallPostsActivity?.findViewById(R.id.main_recycler_view)

            val adapter = rootRecyclerView?.adapter as WallPostsAdapter

            //Test if the recycler view list is greater than 0
            assert(adapter.itemCount > 0)
        }
    }

    /**
     * Test if the data is fetched from the data source as intended and filled with the
     * right data
     */
    @Test
    fun testIfFilledWithRightData() {
        /*Wait for some time until the data is fetched from the data source,
        and then check if the recycler view is filled with the data*/
        Thread.sleep(3000)

        val textView = onView(
            allOf(
                withId(R.id.user_name), withText("llyn"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("llyn")))

        /*val imageView = onView(
            allOf(
                withId(R.id.user_profile_image),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))*/
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
