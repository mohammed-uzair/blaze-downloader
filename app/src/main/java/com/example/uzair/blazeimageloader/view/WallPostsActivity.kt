/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.uzair.blazeimageloader.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uzair.blazeimageloader.R
import com.example.uzair.blazeimageloader.adapter.WallPostsAdapter
import com.example.uzair.blazeimageloader.viewmodel.WallPostsViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Shows a list of posts.
 * <p>
 * Posts are stored in a database, so swipes and additions edit the database directly, and the UI
 * is updated automatically using paging components.
 */
class WallPostsActivity : AppCompatActivity() {
    private lateinit var viewModel: WallPostsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create adapter for the RecyclerView
        val adapter = WallPostsAdapter()
        main_recycler_view.animation = null
        main_recycler_view.setHasFixedSize(true)
        main_recycler_view.layoutManager = LinearLayoutManager(this)
        main_recycler_view.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(WallPostsViewModel::class.java)

        // Subscribe the adapter to the ViewModel, so the items in the adapter are refreshed
        // when the list changes
        viewModel.allPosts.observe(this, Observer { list ->
            adapter.submitList(list)
        })

        //Fetch the data from the webservice
        viewModel.fetchAllData()
    }
}
