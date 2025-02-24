package com.smurzik.videoplayer.core

import com.smurzik.videoplayer.player.presentation.PlayerInfoUi

interface SharedVideoLiveDataWrapper {

    interface Read : MutableLiveDataWrapper.Read<PlayerInfoUi>

    interface Update : MutableLiveDataWrapper.Update<PlayerInfoUi>

    interface Mutable : Read, Update

    class Base : MutableLiveDataWrapper.Abstract<PlayerInfoUi>(), Mutable
}