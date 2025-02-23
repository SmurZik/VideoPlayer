package com.smurzik.viedoplayer.list.presentation

data class VideoItemUi(
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

    fun matches(source: VideoItemUi) = source.id == id
}

class DurationMapper() : VideoItemUi.Mapper<Int> {
    override fun map(
        id: Long,
        image: String,
        duration: Int,
        userName: String,
        link: String,
        title: String,
        index: Int
    ): Int = duration * 1000
}

class IndexMapper() : VideoItemUi.Mapper<Int> {
    override fun map(
        id: Long,
        image: String,
        duration: Int,
        userName: String,
        link: String,
        title: String,
        index: Int
    ): Int = index

}