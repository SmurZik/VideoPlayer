package com.smurzik.viedoplayer.player.presentation

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.smurzik.viedoplayer.core.AbstractFragment
import com.smurzik.viedoplayer.core.VideoPlayerApp
import com.smurzik.viedoplayer.core.ViewModelFactory
import com.smurzik.viedoplayer.databinding.VideoPlayerFragmentBinding

class PlayerFragment : AbstractFragment<VideoPlayerFragmentBinding>() {

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): VideoPlayerFragmentBinding {
        return VideoPlayerFragmentBinding.inflate(inflater, container, false)
    }

    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var playerViewModel: PlayerViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFactory = (requireActivity().application as VideoPlayerApp).viewModelFactory

        playerViewModel = viewModelFactory.create(PlayerViewModel::class.java)

        binding.playerView.player = playerViewModel.player()

        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.layout.setBackgroundColor(Color.BLACK)
            playerViewModel.updateOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        }

        binding.fullScreenButton.setOnClickListener {
            if (playerViewModel.liveData().value == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                playerViewModel.updateOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
            } else {
                playerViewModel.updateOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            }
        }
    }
}