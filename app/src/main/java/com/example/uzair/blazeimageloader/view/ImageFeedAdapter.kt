package com.example.uzair.blazeimageloader.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.uzair.blaze_downloader.BlazeDownloader
import com.example.uzair.blazeimageloader.R
import com.example.uzair.blazeimageloader.model.ImageFeedModel

open class ImageFeedAdapter(
    private val context: Context,
    private val imageFeeds: List<ImageFeedModel>
) :
    RecyclerView.Adapter<ImageFeedAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image_feed, parent, false)

        val viewHolder = ViewHolder(view)

        viewHolder.image.setOnClickListener {
            val adapterPosition = viewHolder.adapterPosition

            //Set max used cache size
            BlazeDownloader.instance.setLruCache(12)
            BlazeDownloader.instance.cancelDownloadingImage(imageFeeds[adapterPosition].imageUrl)

            Toast.makeText(
                context,
                "Image downloading is cancelled at position $adapterPosition",
                Toast.LENGTH_SHORT
            ).show()
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return imageFeeds.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUrl = imageFeeds[holder.adapterPosition].imageUrl

        BlazeDownloader.instance.downloadImage(imageUrl, holder.image)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image_feed_image)
    }
}