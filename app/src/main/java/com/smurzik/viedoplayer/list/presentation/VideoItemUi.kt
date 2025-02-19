package com.smurzik.viedoplayer.list.presentation

data class VideoItemUi(
    private val id: Long,
    private val image: String,
    private val duration: Int,
    private val userName: String,
    private val link: String
)