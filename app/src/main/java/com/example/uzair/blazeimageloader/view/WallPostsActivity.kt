package com.example.uzair.blazeimageloader.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uzair.blazeimageloader.R
import com.example.uzair.blazeimageloader.adapter.WallPostsAdapter
import com.example.uzair.blazeimageloader.models.WallPost
import com.example.uzair.blazeimageloader.util.NetworkState
import com.example.uzair.blazeimageloader.viewmodel.WallPostsViewModel
import kotlinx.android.synthetic.main.activity_main.*

class WallPostsActivity : AppCompatActivity() {
    private lateinit var wallPostsViewModel: WallPostsViewModel
    private lateinit var adapter: WallPostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set our layout file
        setContentView(R.layout.activity_main)

        //Initialization method
        init()
    }

    private fun init() {
        //Get the view model
        wallPostsViewModel = ViewModelProviders.of(this).get(WallPostsViewModel::class.java)

        //Set the recycler view
        main_recycler_view.layoutManager = LinearLayoutManager(this)
        main_recycler_view.itemAnimator = null
        main_recycler_view.setHasFixedSize(true)

        //Set the adapter
        adapter = WallPostsAdapter(this)

        wallPostsViewModel.getAllWallPosts()
            .observe(this, Observer<PagedList<WallPost>> { pagedList ->
                adapter.submitList(pagedList)
            })

        wallPostsViewModel.getNetworkState()
            .observe(this, Observer<NetworkState> { networkState ->
                adapter.setNetworkState(networkState)
            })

        main_recycler_view.adapter = adapter
    }
}