package com.smurzik.videoplayer.core

import com.smurzik.videoplayer.player.presentation.PlayerInfoUi
import javax.inject.Inject

interface SharedVideoLiveDataWrapper {

    interface Read : MutableLiveDataWrapper.Read<PlayerInfoUi>

    interface Update : MutableLiveDataWrapper.Update<PlayerInfoUi>

    interface Mutable : Read, Update

    class Base @Inject constructor() : MutableLiveDataWrapper.Abstract<PlayerInfoUi>(), Mutable
}