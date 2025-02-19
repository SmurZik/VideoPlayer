package com.smurzik.viedoplayer.core

import android.app.Application

class VideoPlayerApp : Application() {

    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate() {
        super.onCreate()
        viewModelFactory = ViewModelFactory(this)
    }
}