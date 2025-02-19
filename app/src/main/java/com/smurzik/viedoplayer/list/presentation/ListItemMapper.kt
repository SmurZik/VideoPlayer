package com.smurzik.viedoplayer.list.presentation

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.smurzik.viedoplayer.R

class ListItemMapper(
    private val image: ImageView,
    private val title: TextView,
    private val duration: TextView
) : VideoItemUi.Mapper<Unit> {

    override fun map(
        id: Long,
        image: String,
        duration: String,
        userName: String,
        link: String,
        title: String
    ) {
        this.title.text = title
        this.duration.text = duration
        Glide.with(this.image).load(image).placeholder(R.drawable.ic_movie).into(this.image)
    }
}