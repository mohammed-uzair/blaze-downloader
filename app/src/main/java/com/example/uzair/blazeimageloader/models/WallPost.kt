package com.example.uzair.blazeimageloader.models

import androidx.recyclerview.widget.DiffUtil

data class WallPost(
    val id: String = "",
    val created_at: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val color: String = "",
    val likes: Int = 0,
    val liked_by_user: Boolean = true,
    val user: User? = null,
    val current_user_collections: List<String>? = null,
    val urls: Urls? = null,
    val categories: List<Categories>? = null,
    val links: Links? = null
) {
    companion object {
        public val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WallPost>() {
            // The ID property identifies when items are the same.
            override fun areItemsTheSame(oldItem: WallPost, newItem: WallPost) =
                oldItem.id == newItem.id

            // If you use the "==" operator, make sure that the object implements
            // .equals(). Alternatively, write custom data comparison logic here.
            override fun areContentsTheSame(
                oldItem: WallPost, newItem: WallPost
            ) = oldItem == newItem
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == this)
            return true

        val obj = other as WallPost
        return obj.id == this.id
    }
}