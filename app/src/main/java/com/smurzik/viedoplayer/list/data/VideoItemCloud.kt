package com.smurzik.viedoplayer.list.data

import com.google.gson.annotations.SerializedName

data class VideoItemCloud(
    @SerializedName("videos")
    val videos: List<Content>
)

data class Content(
    @SerializedName("id")
    val id: Long,
    @SerializedName("url")
    val title: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("user")
    val user: User,
    @SerializedName("video_files")
    val files: List<VideoFiles>
)

data class User(
    @SerializedName("name")
    val name: String
)

data class VideoFiles(
    @SerializedName("link")
    val link: String
)