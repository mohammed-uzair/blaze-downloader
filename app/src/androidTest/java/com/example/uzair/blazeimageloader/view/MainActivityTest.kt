package com.example.uzair.blazeimageloader.view

import androidx.recyclerview.widget.RecyclerView
import androidx.test.rule.ActivityTestRule
import com.example.uzair.blazeimageloader.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    private var mainActivity: MainActivity? = null

    /**
     * Setup all the testing resources used for this class to perform testing
     */
    @Before
    fun setUp() {
        mainActivity = activityTestRule.activity
    }

    /**
     * Test if the activity is created
     */
    @Test
    fun testIfActivityIsLaunched() {
        val rootRecyclerView = mainActivity?.findViewById<RecyclerView>(R.id.main_recycler_view)

        //Test if this view is created
        assertNotNull(rootRecyclerView)
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
            //Wait for 3 seconds
            delay(3000)

            //Check if the recycler view has any data
            val rootRecyclerView: RecyclerView? =
                mainActivity?.findViewById(R.id.main_recycler_view)

            val adapter = rootRecyclerView?.adapter as ImageFeedAdapter

            //Test if the recycler view list is greater than 0
            assert(adapter.itemCount > 0)
        }
    }

    /**
     * Tear down all the testing resources used for this class to perform testing
     */
    @After
    fun tearDown() {
        mainActivity = null
    }
}