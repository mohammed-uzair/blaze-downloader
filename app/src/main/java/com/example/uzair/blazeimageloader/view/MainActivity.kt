package com.example.uzair.blazeimageloader.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uzair.blazeimageloader.R
import com.example.uzair.blazeimageloader.repository.IImageFeedRepository
import com.example.uzair.blazeimageloader.repository.ImageFeedRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set our layout file
        setContentView(R.layout.activity_main)

        //Get all the image feeds from the data source
        val imageFeedRepository: IImageFeedRepository = ImageFeedRepository()

        //Set the recycler view adapter
        val recyclerView: RecyclerView = findViewById(R.id.main_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(baseContext, 2)
        recyclerView.animation = null
        recyclerView.setHasFixedSize(true)

        //Set the recycler view adapter
        recyclerView.adapter = ImageFeedAdapter(this, imageFeedRepository.getAllImageFeeds())
    }
}