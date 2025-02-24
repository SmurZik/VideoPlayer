package com.smurzik.videoplayer.player.presentation

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.smurzik.videoplayer.R
import com.smurzik.videoplayer.core.AbstractFragment
import com.smurzik.videoplayer.databinding.VideoPlayerFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class PlayerFragment : AbstractFragment<VideoPlayerFragmentBinding>() {

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): VideoPlayerFragmentBinding {
        return VideoPlayerFragmentBinding.inflate(inflater, container, false)
    }

    private val playerViewModel by viewModels<PlayerViewModel>()
    private lateinit var orientationEnterFullscreenEventListener: OrientationEventListener
    private lateinit var orientationExitFullscreenEventListener: OrientationEventListener
    private lateinit var controlLayout: ControlLayout

    private val gestureDetector by lazy {
        GestureDetector(requireContext(), object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                handleDoubleTap(e)
                return true
            }

            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                if (controlLayout.visibility == View.VISIBLE)
                    controlLayout.visibility = View.GONE
                return true
            }
        })
    }

    private fun handleDoubleTap(e: MotionEvent) {

        if (e.x < binding.playerView.width / 2)
            playerViewModel.seekBack()
        else
            playerViewModel.seekForward()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        val seekBar = view.findViewById<SeekBar>(R.id.seekBar)
        val progressTextView = view.findViewById<TextView>(R.id.progress)
        val durationTextView = view.findViewById<TextView>(R.id.duration)
        val skipNextButton = view.findViewById<ImageButton>(R.id.buttonNext)
        val skipPreviousButton = view.findViewById<ImageButton>(R.id.buttonPrevious)
        val titleTextView = view.findViewById<TextView>(R.id.videoTitleTextView)
        val authorTextView = view.findViewById<TextView>(R.id.authorTextView)
        controlLayout = view.findViewById(R.id.controlLayout)

        btnPlayPause.setOnClickListener {
            if (playerViewModel.isPlaying()) {
                playerViewModel.pause()
                btnPlayPause.setImageResource(R.drawable.ic_play)
            } else {
                playerViewModel.play()
                btnPlayPause.setImageResource(R.drawable.ic_pause)
            }
        }

        controlLayout.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            if (event.action == MotionEvent.ACTION_UP) {
                controlLayout.performClick()
            }
            true
        }

        binding.playerView.setOnClickListener {
            if (controlLayout.visibility != View.VISIBLE)
                controlLayout.visibility = View.VISIBLE
        }

        skipPreviousButton.setOnClickListener {
            playerViewModel.player().seekToPrevious()
        }

        skipNextButton.setOnClickListener {
            playerViewModel.player().seekToNext()
        }

        playerViewModel.currentVideo().observe(viewLifecycleOwner) {
            seekBar.max = it.duration
            durationTextView.text = playerViewModel.formatDuration(it.duration)
            titleTextView.text = it.title
            authorTextView.text =
                getString(R.string.video_by_s_on_pexels).format(Locale.ROOT, it.author)
        }

        with(btnPlayPause) {
            if (playerViewModel.isPlaying())
                setImageResource(R.drawable.ic_pause)
            else
                setImageResource(R.drawable.ic_play)
        }

        playerViewModel.player().addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                if (playbackState == Player.STATE_READY) {
                    playerViewModel.updateSeekBar()
                    playerViewModel.updateCurrentVideo(playerViewModel.player().currentMediaItemIndex)
                }
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)
                playerViewModel.updateCurrentVideo(playerViewModel.player().currentMediaItemIndex)
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                if (isPlaying) playerViewModel.updateSeekBar()
                with(btnPlayPause) {
                    if (isPlaying)
                        setImageResource(R.drawable.ic_pause)
                    else
                        setImageResource(R.drawable.ic_play)
                }
            }
        })

        playerViewModel.seekBar().observe(viewLifecycleOwner) {
            seekBar.progress = it
            progressTextView.text = playerViewModel.formatDuration(it)
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) =
                Unit

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                playerViewModel.changeVideoProgress(seekBar?.progress ?: 0)
            }

        })

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
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
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