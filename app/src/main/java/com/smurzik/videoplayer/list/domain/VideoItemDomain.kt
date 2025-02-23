package com.smurzik.videoplayer.list.domain

data class VideoItemDomain(
    private val id: Long,
    private val image: String,
    private val duration: Int,
    private val userName: String,
    private val link: String,
    private val title: String,
    private val index: Int
) {
    interface Mapper<T> {
        fun map(
            id: Long,
            image: String,
            duration: Int,
            userName: String,
            link: String,
            title: String,
            index: Int
        ): T
    }

    fun <T> map(mapper: Mapper<T>): T =
        mapper.map(id, image, duration, userName, link, title, index)
}