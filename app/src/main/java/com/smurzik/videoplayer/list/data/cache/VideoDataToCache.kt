package com.smurzik.videoplayer.list.data.cache

import com.smurzik.videoplayer.list.data.VideoItemData

class VideoDataToCache : VideoItemData.Mapper<VideoCache> {
    override fun map(
        id: Long,
        image: String,
        duration: Int,
        userName: String,
        link: String,
        title: String,
        index: Int
    ): VideoCache = VideoCache(id, image, duration, userName, link, title, index)
}