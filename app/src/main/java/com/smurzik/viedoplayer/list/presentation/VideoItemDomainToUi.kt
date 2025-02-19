package com.smurzik.viedoplayer.list.presentation

import com.smurzik.viedoplayer.list.domain.VideoItemDomain

class VideoItemDomainToUi : VideoItemDomain.Mapper<VideoItemUi> {

    override fun map(
        id: Long,
        image: String,
        duration: Int,
        userName: String,
        link: String
    ) = VideoItemUi(id, image, duration, userName, link)
}