package com.smurzik.videoplayer.di

import android.app.Application
import androidx.media3.exoplayer.ExoPlayer
import com.smurzik.videoplayer.core.PlayerHelper
import com.smurzik.videoplayer.core.VideoItemUiToUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlayerModule {

    @Provides
    @Singleton
    fun provideExoPlayer(context: Application): ExoPlayer =
        ExoPlayer.Builder(context).build()

    @Provides
    @Singleton
    fun providePlayerHelper(
        exoPlayer: ExoPlayer,
        urlMapper: VideoItemUiToUrl
    ): PlayerHelper {
        return PlayerHelper(exoPlayer, urlMapper)
    }
}