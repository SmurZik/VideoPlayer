package com.smurzik.videoplayer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.smurzik.videoplayer.core.VideoPlayerApp
import com.smurzik.videoplayer.core.ViewModelFactory
import com.smurzik.videoplayer.databinding.ActivityMainBinding
import com.smurzik.videoplayer.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        viewModelFactory = (application as VideoPlayerApp).viewModelFactory

        viewModel.init(savedInstanceState == null)

        viewModel.liveData().observe(this) {
            it.show(supportFragmentManager, binding.containerView.id)
        }
    }
}