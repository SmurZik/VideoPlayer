package com.smurzik.viedoplayer.list.data

import com.smurzik.viedoplayer.list.domain.VideoItemDomain

class VideoItemDataToDomain : VideoItemData.Mapper<VideoItemDomain> {

    override fun map(
        id: Long,
        image: String,
        duration: Int,
        userName: String,
        link: String
    ) = VideoItemDomain(id, image, duration, userName, link)
}