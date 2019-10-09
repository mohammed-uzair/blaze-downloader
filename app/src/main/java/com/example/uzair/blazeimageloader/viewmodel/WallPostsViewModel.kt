package com.example.uzair.blazeimageloader.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.Config
import androidx.paging.toLiveData
import com.example.uzair.blazeimageloader.database.WallPostDb
import com.example.uzair.blazeimageloader.repository.WallPostsRepository

/**
 * A simple ViewModel that provides a paged list of all posts.
 */
class WallPostsViewModel(val app: Application) : AndroidViewModel(app) {
    private val dao = WallPostDb.get(app).wallPostDao()

    /**
     * We use -ktx Kotlin extension functions here, otherwise you would use LivePagedListBuilder(),
     * and PagedList.Config.Builder()
     */
    val allPosts = dao.allWallPosts().toLiveData(
        Config(
            /**
             * A good page size is a value that fills at least a screen worth of content on a large
             * device so the User is unlikely to see a null item.
             * You can play with this constant to observe the paging behavior.
             * <p>
             * It's possible to vary this with list device size, but often unnecessary, unless a
             * user scrolling on a large device is expected to scroll through items more quickly
             * than a small device, such as when the large device uses a grid layout of items.
             */
            pageSize = 10,

            /**
             * If placeholders are enabled, PagedList will report the full size but some items might
             * be null in onBind method (PagedListAdapter triggers a rebind when data is loaded).
             * <p>
             * If placeholders are disabled, onBind will never receive null but as more pages are
             * loaded, the scrollbars will jitter as new pages are loaded. You should probably
             * disable scrollbars if you disable placeholders.
             */
            enablePlaceholders = true,

            /**
             * Maximum number of items a PagedList should hold in memory at once.
             * <p>
             * This number triggers the PagedList to start dropping distant pages as more are loaded.
             */
            maxSize = 100
        )
    )

    /**
     * Fetch all the data from the web service
     */
    fun fetchAllData() {
        WallPostsRepository(app).getAllWallPosts()
    }
}
