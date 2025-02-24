package com.smurzik.videoplayer.player.presentation

data class PlayerInfoUi(
    val title: String,
    val duration: Int,
    val author: String
) {
    interface Mapper<T> {
        fun map(
            title: String,
            duration: Int,
            author: String
        ): T
    }

    fun <T> map(mapper: Mapper<T>): T =
        mapper.map(title, duration, author)
}