package com.smurzik.viedoplayer.list.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [VideoCache::class], version = 1)
abstract class VideoRoomDatabase : RoomDatabase() {

    abstract fun videoDao(): VideoDao
}