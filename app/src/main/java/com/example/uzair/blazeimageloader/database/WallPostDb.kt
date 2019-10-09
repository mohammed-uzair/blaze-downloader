package com.example.uzair.blazeimageloader.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.uzair.blazeimageloader.database.dao.WallPostDao
import com.example.uzair.blazeimageloader.models.*
import com.example.uzair.blazeimageloader.utils.DbConverters

/**
 * Singleton database object. Note that for a real app, you should probably use a Dependency
 * Injection framework or Service Locator to create the singleton database.
 */
@Database(
    entities = [WallPost::class, User::class, Urls::class, Links::class, Categories::class],
    version = 1
)
@TypeConverters(DbConverters::class)
abstract class WallPostDb : RoomDatabase() {
    abstract fun wallPostDao(): WallPostDao

    companion object {
        private var instance: WallPostDb? = null
        @Synchronized
        fun get(context: Context): WallPostDb {
            if (instance == null) {
                instance = Room.inMemoryDatabaseBuilder(
                    context.applicationContext,
                    WallPostDb::class.java
                ).build()
            }
            return instance!!
        }
    }
}