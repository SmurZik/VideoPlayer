package com.smurzik.viedoplayer.list.presentation

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.smurzik.viedoplayer.R
import java.util.Locale

class ListItemMapper(
    private val image: ImageView,
    private val title: TextView,
    private val duration: TextView
) : VideoItemUi.Mapper<Unit> {

    override fun map(
        id: Long,
        image: String,
        duration: Int,
        userName: String,
        link: String,
        title: String,
        index: Int
    ) {
        this.title.text = title
        this.duration.text = formatDuration(duration)
        Glide.with(this.image).load(image).placeholder(R.drawable.ic_movie).into(this.image)
    }

    private fun formatDuration(seconds: Int): String {
        val minutes = seconds / 60
        val secondsRemain = seconds % 60
        return String.format(Locale.ROOT, "%02d:%02d", minutes, secondsRemain)
    }
}