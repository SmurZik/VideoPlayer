package com.smurzik.viedoplayer.player.presentation

import androidx.lifecycle.ViewModel
import com.smurzik.viedoplayer.core.PlayerHelper
import com.smurzik.viedoplayer.list.presentation.VideoItemUi

class PlayerViewModel(
    private val playerHelper: PlayerHelper
) : ViewModel() {

    fun player() = playerHelper.player()
}