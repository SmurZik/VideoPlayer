package com.smurzik.viedoplayer.list.data

data class VideoItemData(
    private val id: Long,
    private val image: String,
    private val duration: Int,
    private val userName: String,
    private val link: String
) {

    interface Mapper<T> {
        fun map(id: Long, image: String, duration: Int, userName: String, link: String): T
    }

    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, image, duration, userName, link)
}