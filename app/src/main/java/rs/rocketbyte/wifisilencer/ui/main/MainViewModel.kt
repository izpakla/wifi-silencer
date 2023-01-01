package rs.rocketbyte.wifisilencer.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import rs.rocketbyte.wifisilencer.core.manager.WifiSilencer
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val wifiSilencer: WifiSilencer
) : ViewModel()
