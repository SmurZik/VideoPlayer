package com.smurzik.videoplayer.di

import android.app.Application
import androidx.room.Room
import com.smurzik.videoplayer.list.data.cache.VideoDao
import com.smurzik.videoplayer.list.data.cache.VideoRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRoom(context: Application): VideoRoomDatabase =
        Room.databaseBuilder(
            context,
            VideoRoomDatabase::class.java,
            "videos_database"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDao(room: VideoRoomDatabase): VideoDao =
        room.videoDao()
}