package com.smurzik.viedoplayer

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.setPadding
import androidx.media3.exoplayer.mediacodec.MediaCodecAdapter.Configuration
import com.smurzik.viedoplayer.core.VideoPlayerApp
import com.smurzik.viedoplayer.core.ViewModelFactory
import com.smurzik.viedoplayer.databinding.ActivityMainBinding
import com.smurzik.viedoplayer.main.MainViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        ViewCompat.setOnApplyWindowInsetsListener(binding.containerView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewModelFactory = (application as VideoPlayerApp).viewModelFactory

        val viewModel: MainViewModel by viewModels { viewModelFactory }

        viewModel.init(savedInstanceState == null)

        viewModel.liveData().observe(this) {
            it.show(supportFragmentManager, binding.containerView.id)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.containerView) { view, insets ->
            viewModel.orientation().observe(this) {
                if (it == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
                    view.setPadding(0, 0, 0, 0)
                } else {
                    exitFullScreen(windowInsetsController, insets, view)
                }
            }
            if (viewModel.orientation().value != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                exitFullScreen(windowInsetsController, insets, view)
            }
            ViewCompat.onApplyWindowInsets(view, insets)
        }
    }

    private fun exitFullScreen(
        windowInsetsController: WindowInsetsControllerCompat,
        insets: WindowInsetsCompat,
        view: View
    ) {
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.setPadding(
            systemBars.left,
            systemBars.top,
            systemBars.right,
            systemBars.bottom
        )
    }
}