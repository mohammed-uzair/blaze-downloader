package com.example.uzair.blazeimageloader.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.uzair.blaze_downloader.BlazeDownloader
import com.example.uzair.blazeimageloader.R
import com.example.uzair.blazeimageloader.models.WallPost

/**
 * A simple ViewHolder that can bind a post item. It also accepts null items since the data may
 * not have been fetched before it is bound.
 */
class WallPostViewHolder(private val parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
) {

    private val image: ImageView = itemView.findViewById(R.id.user_profile_image)
    private val text: TextView = itemView.findViewById(R.id.user_name)
    private var wallPost: WallPost? = null

    //Add a on click listener to the image view, and give a functionality to cancel the
    //downloading of the image
    private val listener = image.setOnClickListener {
        BlazeDownloader.instance.cancelDownloadingImage(image)
    }

    /**
     * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
     * ViewHolder when Item is loaded.
     */
    fun bindTo(wallPost: WallPost?) {
        this.wallPost = wallPost
        text.text = wallPost?.user?.username
        //Place a dummy place holder until the actual images gets download from the data source
        image.setImageDrawable(
            ResourcesCompat.getDrawable
                (parent.context.resources, R.drawable.ic_user, null)
        )

        //Set max used cache size
        BlazeDownloader.instance.lruCacheSize = 30
        val imageUrl = wallPost?.user?.profile_image?.large

        if (imageUrl != null)
            BlazeDownloader.instance.downloadImage(imageUrl, image)
    }
}