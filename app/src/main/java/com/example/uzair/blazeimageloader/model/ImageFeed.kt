data class ImageFeed(
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
)