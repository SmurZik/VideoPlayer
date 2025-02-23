package com.smurzik.viedoplayer.list.data.cloud

import androidx.core.text.isDigitsOnly
import com.smurzik.viedoplayer.list.data.VideoItemData
import java.util.Locale
import kotlin.random.Random

interface VideoCloudDataSource {

    suspend fun getVideos(): List<VideoItemData>

    class Base(
        private val service: VideoService
    ) : VideoCloudDataSource {

        override suspend fun getVideos(): List<VideoItemData> {
            val result = service.getVideos(Random.nextInt(1, 11)).videos
            return result.mapIndexed { index, content ->
                VideoItemData(
                    content.id,
                    content.image,
                    content.duration,
                    content.user.name,
                    content.files.first().link,
                    formatTitle(content.title),
                    index
                )
            }
        }

        private fun formatTitle(titleUrl: String): String {
            val splitTitle = titleUrl.split('/')
            val title = splitTitle[splitTitle.size - 2]
            val result = StringBuilder()
            title.split("-").forEachIndexed { index, s ->
                if (!s.isDigitsOnly()) {
                    if (index == 0)
                        result.append(
                            "${
                                s.replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(
                                        Locale.ROOT
                                    ) else it.toString()
                                }
                            } "
                        )
                    else
                        result.append("$s ")
                }
            }
            return result.toString()
        }
    }
}