package com.smurzik.viedoplayer.list.presentation

data class VideoItemUi(
    private val id: Long,
    private val image: String,
    private val duration: Int,
    private val userName: String,
    private val link: String,
    private val title: String
) {

    interface Mapper<T> {
        fun map(
            id: Long,
            image: String,
            duration: Int,
            userName: String,
            link: String,
            title: String
        ): T
    }

    fun <T> map(mapper: Mapper<T>): T =
        mapper.map(id, image, duration, userName, link, title)

    fun matches(source: VideoItemUi) = source.id == id
}

class DurationMapper() : VideoItemUi.Mapper<Int> {
    override fun map(
        id: Long,
        image: String,
        duration: Int,
        userName: String,
        link: String,
        title: String
    ): Int = duration * 1000
}