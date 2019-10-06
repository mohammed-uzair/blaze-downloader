package com.example.uzair.blazeimageloader.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.res.ResourcesCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uzair.blaze_downloader.BlazeDownloader
import com.example.uzair.blazeimageloader.R
import com.example.uzair.blazeimageloader.models.WallPost
import com.example.uzair.blazeimageloader.util.NetworkState

class WallPostsAdapter(private val context: Context) :
    PagedListAdapter<WallPost, RecyclerView.ViewHolder>(WallPost.DIFF_CALLBACK) {
    companion object {
        private const val TYPE_PROGRESS = 0
        private const val TYPE_ITEM = 1
    }

    private var networkState: NetworkState? = null

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_PROGRESS) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_network_state, parent, false)

            NetworkStateItemViewHolder(view)

        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_feed, parent, false)

            WallPostItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is WallPostItemViewHolder) {
            getItem(position)?.let {
                holder.text.text = it.user?.name

                //Place a dummy place holder until the actual images gets download from the data source
                holder.image.setImageDrawable(
                    ResourcesCompat.getDrawable
                        (context.resources, R.drawable.ic_user, null)
                )

                //Set max used cache size
                BlazeDownloader.instance.lruCacheSize = 30
                val imageUrl = it.user?.profile_image?.large

                if (imageUrl != null)
                    BlazeDownloader.instance.downloadImage(imageUrl, holder.image)
            }
        } else {
            (holder as NetworkStateItemViewHolder).bindView(networkState)
        }
    }


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState !== NetworkState.LOADED
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            TYPE_PROGRESS
        } else {
            TYPE_ITEM
        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val previousExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val newExtraRow = hasExtraRow()
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRow && previousState !== newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }


    inner class WallPostItemViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.user_profile_image)
        val text: TextView = view.findViewById(R.id.user_name)
    }

    inner class NetworkStateItemViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
        private val errorMessage: TextView = view.findViewById(R.id.error_msg)

        fun bindView(networkState: NetworkState?) {
            if (networkState != null && networkState.status === NetworkState.Status.RUNNING) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }

            if (networkState != null && networkState.status === NetworkState.Status.FAILED) {
                errorMessage.visibility = View.VISIBLE
                errorMessage.text = networkState.msg
            } else {
                errorMessage.visibility = View.GONE
            }
        }
    }
}
