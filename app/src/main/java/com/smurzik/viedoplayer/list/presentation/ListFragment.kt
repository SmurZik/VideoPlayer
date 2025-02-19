package com.smurzik.viedoplayer.list.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.smurzik.viedoplayer.core.AbstractFragment
import com.smurzik.viedoplayer.databinding.VideoListFragmentBinding

class ListFragment : AbstractFragment<VideoListFragmentBinding>() {

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): VideoListFragmentBinding {
        return VideoListFragmentBinding.inflate(inflater, container, false)
    }
}