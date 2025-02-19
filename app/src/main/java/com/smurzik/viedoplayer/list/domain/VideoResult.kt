package com.smurzik.viedoplayer.list.domain

sealed class VideoResult {

    interface Mapper<T> {
        fun map(list: List<VideoItemDomain>, errorMessage: String): T
    }

    abstract fun <T> map(mapper: Mapper<T>): T

    data class Success(private val list: List<VideoItemDomain>) : VideoResult() {
        override fun <T> map(mapper: Mapper<T>) = mapper.map(list, "")
    }

    data class Failure(private val message: String) : VideoResult() {
        override fun <T> map(mapper: Mapper<T>) = mapper.map(emptyList(), message)
    }
}