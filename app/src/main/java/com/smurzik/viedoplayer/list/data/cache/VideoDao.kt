package com.smurzik.viedoplayer.list.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface VideoDao {

    @Query("DELETE FROM videos_table")
    fun clearVideos()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideos(videos: List<VideoCache>)

    @Transaction
    fun replaceVideo(videos: List<VideoCache>) {
        clearVideos()
        insertVideos(videos)
    }

    @Query("SELECT * FROM videos_table ORDER BY `index`")
    fun getVideos(): List<VideoCache>
}