package com.example.uzair.blazeimageloader.view

import ImageFeed
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.uzair.blaze_downloader.BlazeDownloader
import com.example.uzair.blazeimageloader.R

open class ImageFeedAdapter(
    private val context: Context,
    private val imageFeeds: List<ImageFeed>
) :
    RecyclerView.Adapter<ImageFeedAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)

        val viewHolder = ViewHolder(view)

        viewHolder.image.setOnClickListener {
            val adapterPosition = viewHolder.adapterPosition

            BlazeDownloader.instance.cancelDownloadingImage(viewHolder.image)

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
        val userData = imageFeeds[position]

        holder.text.text = userData.user?.name

        //Place a dummy place holder until the actual images gets download from the data source
        holder.image.setImageDrawable(
            ResourcesCompat.getDrawable
                (context.resources, R.drawable.ic_user, null)
        )

        //Set max used cache size
        BlazeDownloader.instance.lruCacheSize = 30
        val imageUrl = userData.user?.profile_image?.large

        if (imageUrl != null)
            BlazeDownloader.instance.downloadImage(imageUrl, holder.image)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.user_profile_image)
        val text: TextView = view.findViewById(R.id.user_name)
    }
}