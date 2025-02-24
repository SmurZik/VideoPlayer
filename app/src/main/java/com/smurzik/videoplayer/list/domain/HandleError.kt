package com.smurzik.videoplayer.list.domain

import javax.inject.Inject

interface HandleError {

    fun handle(e: Exception): String

    class Base @Inject constructor() : HandleError {
        override fun handle(e: Exception): String =
            when (e) {
                is NoInternetConnectionException -> "No internet connection"
                else -> "Service is unavailable"
            }
    }
}