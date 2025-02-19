package com.smurzik.viedoplayer.list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.smurzik.viedoplayer.core.AbstractFragment
import com.smurzik.viedoplayer.core.ViewModelFactory
import com.smurzik.viedoplayer.databinding.VideoListFragmentBinding

class ListFragment : AbstractFragment<VideoListFragmentBinding>() {

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): VideoListFragmentBinding {
        return VideoListFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: ListViewModel by viewModels { ViewModelFactory() }
    }
}