package com.smurzik.videoplayer.list.presentation

import com.smurzik.videoplayer.list.domain.VideoItemDomain

class VideoItemDomainToUi : VideoItemDomain.Mapper<VideoItemUi> {

    override fun map(
        id: Long,
        image: String,
        duration: Int,
        userName: String,
        link: String,
        title: String,
        index: Int
    ) = VideoItemUi(id, image, duration, userName, link, title, index)
}