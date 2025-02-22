package com.smurzik.viedoplayer.player.presentation

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.viewModels
import com.smurzik.viedoplayer.R
import com.smurzik.viedoplayer.core.AbstractFragment
import com.smurzik.viedoplayer.core.VideoPlayerApp
import com.smurzik.viedoplayer.core.ViewModelFactory
import com.smurzik.viedoplayer.databinding.CustomPlayerControlsBinding
import com.smurzik.viedoplayer.databinding.VideoPlayerFragmentBinding

class PlayerFragment : AbstractFragment<VideoPlayerFragmentBinding>() {

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): VideoPlayerFragmentBinding {
        return VideoPlayerFragmentBinding.inflate(inflater, container, false)
    }

    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var playerViewModel: PlayerViewModel
    private lateinit var orientationEnterFullscreenEventListener: OrientationEventListener
    private lateinit var orientationExitFullscreenEventListener: OrientationEventListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFactory = (requireActivity().application as VideoPlayerApp).viewModelFactory

        playerViewModel = viewModelFactory.create(PlayerViewModel::class.java)

        binding.playerView.player = playerViewModel.player()

        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.layout.setBackgroundColor(Color.BLACK)
        }

        orientationEnterFullscreenEventListener =
            object : OrientationEventListener(requireActivity()) {
                override fun onOrientationChanged(orientation: Int) {
                    if ((orientation in 80..100 || orientation in 260..280)
                    ) {
                        requireActivity().requestedOrientation =
                            ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                        playerViewModel.updateOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        disable()
                    }
                }
            }

        orientationExitFullscreenEventListener =
            object : OrientationEventListener(requireActivity()) {
                override fun onOrientationChanged(orientation: Int) {
                    if (orientation in 0..10 || orientation in 170..190) {
                        requireActivity().requestedOrientation =
                            ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                        playerViewModel.updateOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        disable()
                    }
                }
            }

        val btnPlayPause = view.findViewById<ImageButton>(R.id.buttonPlayPause)
        val fullscreenButton = view.findViewById<ImageButton>(R.id.fullscreenButton)

        btnPlayPause.setOnClickListener {
            if (playerViewModel.isPlaying()) {
                playerViewModel.pause()
                btnPlayPause.setImageResource(R.drawable.ic_play)
            } else {
                playerViewModel.play()
                btnPlayPause.setImageResource(R.drawable.ic_pause)
            }
        }

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE && playerViewModel.liveData().value != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            fullscreenButton.setImageResource(R.drawable.fullscreen_exit)
            enterFullscreen()
        } else if (resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE && playerViewModel.liveData().value != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            fullscreenButton.setImageResource(R.drawable.ic_fullscreen)
            exitFullscreen()
        }

        fullscreenButton.setOnClickListener {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                playerViewModel.updateOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                exitFullscreen()
            } else {
                playerViewModel.updateOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                enterFullscreen()
            }
        }
    }

    private fun enterFullscreen() {
        val window = requireActivity().window
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        if (orientationEnterFullscreenEventListener.canDetectOrientation()) {
            orientationEnterFullscreenEventListener.enable()
        }
    }

    private fun exitFullscreen() {
        val window = requireActivity().window
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        controller.show(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        if (orientationExitFullscreenEventListener.canDetectOrientation()) {
            orientationExitFullscreenEventListener.enable()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        orientationEnterFullscreenEventListener.disable()
        orientationExitFullscreenEventListener.disable()
    }
}