package com.smurzik.viedoplayer.player.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.smurzik.viedoplayer.core.AbstractFragment
import com.smurzik.viedoplayer.core.VideoPlayerApp
import com.smurzik.viedoplayer.core.ViewModelFactory
import com.smurzik.viedoplayer.databinding.VideoPlayerFragmentBinding

class PlayerFragment : AbstractFragment<VideoPlayerFragmentBinding>() {

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): VideoPlayerFragmentBinding {
        return VideoPlayerFragmentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = (requireActivity().application as VideoPlayerApp).viewModelFactory

        val playerViewModel: PlayerViewModel by viewModels { viewModelFactory }

        binding.playerView.player = playerViewModel.player()
    }

}