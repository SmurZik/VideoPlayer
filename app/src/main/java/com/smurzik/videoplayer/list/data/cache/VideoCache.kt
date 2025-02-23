package com.smurzik.videoplayer.list.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videos_table")
data class VideoCache(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "duration") val duration: Int,
    @ColumnInfo(name = "userName") val userName: String,
    @ColumnInfo(name = "link") val link: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "index") val index: Int
)