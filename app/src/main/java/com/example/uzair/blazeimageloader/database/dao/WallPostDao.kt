package com.example.uzair.blazeimageloader.database.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.uzair.blazeimageloader.models.WallPost

/**
 * Database Access Object for the wallposts database.
 */
@Dao
interface WallPostDao {
    /**
     * Room knows how to return a LivePagedListProvider, from which we can get a LiveData and serve
     * it back to UI via ViewModel.
     */
    @Query("SELECT * FROM WallPost")
    fun allWallPosts(): DataSource.Factory<Int, WallPost>

    @Insert
    fun insert(wallPosts: List<WallPost>)
}