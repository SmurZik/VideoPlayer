package com.smurzik.videoplayer.list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import com.smurzik.videoplayer.core.AbstractFragment
import com.smurzik.videoplayer.databinding.VideoListFragmentBinding
import com.smurzik.videoplayer.player.presentation.PlayerScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : AbstractFragment<VideoListFragmentBinding>() {

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): VideoListFragmentBinding {
        return VideoListFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewModel: ListViewModel by viewModels()

        val adapter = VideoListAdapter(object : ClickListener {
            override fun click(item: VideoItemUi) {
                viewModel.updateCurrentPlaylist(viewModel.liveData().value ?: listOf(), item)
            }
        })

        if (viewModel.navigation().value == PlayerScreen)
            viewModel.updateNavigation(ListScreen)

        binding.recyclerView.adapter = adapter

        viewModel.init(false)

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            viewModel.init(true)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel.progressLiveData().observe(viewLifecycleOwner) {
            binding.progressBar.visibility = it
        }

        viewModel.errorLiveDataWrapper().observe(viewLifecycleOwner) {
            if (it.isEmpty())
                binding.errorMessage.visibility = View.GONE
            else {
                binding.errorMessage.text = it
                binding.errorMessage.visibility = View.VISIBLE
            }
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            adapter.update(it)
        }
    }
}