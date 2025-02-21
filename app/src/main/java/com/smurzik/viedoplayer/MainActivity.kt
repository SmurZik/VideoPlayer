package com.smurzik.viedoplayer

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.OrientationEventListener
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.Player
import com.smurzik.viedoplayer.core.VideoPlayerApp
import com.smurzik.viedoplayer.core.ViewModelFactory
import com.smurzik.viedoplayer.databinding.ActivityMainBinding
import com.smurzik.viedoplayer.main.MainViewModel
import com.smurzik.viedoplayer.main.Screen
import com.smurzik.viedoplayer.player.presentation.PlayerScreen

class MainActivity : AppCompatActivity() {

    private lateinit var viewModelFactory: ViewModelFactory
    val viewModel: MainViewModel by viewModels { viewModelFactory }
    private lateinit var orientationEventListener: OrientationEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        orientationEventListener = object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                if ((orientation in 80..100 || orientation in 260..280) && viewModel.liveData().value == PlayerScreen) {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                    disable()
                }
            }
        }

        viewModelFactory = (application as VideoPlayerApp).viewModelFactory

        viewModel.init(savedInstanceState == null)

        viewModel.liveData().observe(this) {
            it.show(supportFragmentManager, binding.containerView.id)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.containerView) { view, insets ->
            viewModel.orientation().observe(this) {
                if (it == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE && viewModel.liveData().value == PlayerScreen) {
                    enterFullScreen(windowInsetsController, view)
                } else {
                    exitFullScreen(windowInsetsController, insets, view)
                }
            }
            if (viewModel.orientation().value != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE || viewModel.liveData().value != PlayerScreen) {
                exitFullScreen(windowInsetsController, insets, view)
            } else {
                enterFullScreen(windowInsetsController, view)
            }
            ViewCompat.onApplyWindowInsets(view, insets)
        }
    }

    override fun onStart() {
        super.onStart()
        val orientation =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            else
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        viewModel.updateOrientation(orientation)
    }

    private fun enterFullScreen(
        windowInsetsController: WindowInsetsControllerCompat,
        view: View
    ) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        view.setPadding(0, 0, 0, 0)
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
        if (orientationEventListener.canDetectOrientation()) {
            orientationEventListener.enable()
        }
    }

    private fun exitFullScreen(
        windowInsetsController: WindowInsetsControllerCompat,
        insets: WindowInsetsCompat,
        view: View
    ) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        val systemBars =
            insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
        view.setPadding(
            systemBars.left,
            systemBars.top,
            systemBars.right,
            systemBars.bottom
        )
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
    }
}