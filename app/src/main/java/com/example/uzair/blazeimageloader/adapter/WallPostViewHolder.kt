package com.example.uzair.blazeimageloader.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uzair.blaze_downloader.BlazeDownloader
import com.example.uzair.blazeimageloader.R
import com.example.uzair.blazeimageloader.models.WallPost

/**
 * A simple ViewHolder that can bind a post item. It also accepts null items since the data may
 * not have been fetched before it is bound.
 */
class WallPostViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
) {

    private val image: ImageView = itemView.findViewById(R.id.user_profile_image)
    private val text: TextView = itemView.findViewById(R.id.user_name)
    private var wallPost: WallPost? = null

    /**
     * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
     * ViewHolder when Item is loaded.
     */
    fun bindTo(wallPost: WallPost?) {
        this.wallPost = wallPost
        text.text = wallPost?.user?.username
        //Place a dummy place holder until the actual images gets download from the data source
//        image.setImageDrawable(
//            ResourcesCompat.getDrawable
//                (context.resources, R.drawable.ic_user, null)
//        )

        //Set max used cache size
        BlazeDownloader.instance.lruCacheSize = 30
        val imageUrl = wallPost?.user?.profile_image?.large

        if (imageUrl != null)
            BlazeDownloader.instance.downloadImage(imageUrl, image)
    }
}