package com.smurzik.viedoplayer.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smurzik.viedoplayer.main.MainViewModel
import com.smurzik.viedoplayer.main.Navigation

class ViewModelFactory : ViewModelProvider.Factory {

    private val navigation = Navigation.Base()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(navigation = navigation) as T
    }
}