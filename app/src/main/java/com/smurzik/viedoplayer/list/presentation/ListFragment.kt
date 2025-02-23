package com.smurzik.viedoplayer.list.presentation

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.smurzik.viedoplayer.core.AbstractFragment
import com.smurzik.viedoplayer.core.VideoPlayerApp
import com.smurzik.viedoplayer.databinding.VideoListFragmentBinding
import com.smurzik.viedoplayer.player.presentation.PlayerScreen

class ListFragment : AbstractFragment<VideoListFragmentBinding>() {

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): VideoListFragmentBinding {
        return VideoListFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = (requireActivity().application as VideoPlayerApp).viewModelFactory

        val viewModel: ListViewModel by viewModels { viewModelFactory }

        val adapter = VideoListAdapter(object : ClickListener {
            override fun click(item: VideoItemUi) {
                viewModel.updateCurrentPlaylist(viewModel.liveData().value ?: listOf(), item)
            }
        })

        if (viewModel.navigation().value == PlayerScreen)
            viewModel.updateNavigation(ListScreen)

        binding.recyclerView.adapter = adapter

        viewModel.init(false)

        viewModel.updateOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            viewModel.init(true)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel.progressLiveData().observe(viewLifecycleOwner) {
            binding.progressBar.visibility = it
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            adapter.update(it)
        }
    }
}