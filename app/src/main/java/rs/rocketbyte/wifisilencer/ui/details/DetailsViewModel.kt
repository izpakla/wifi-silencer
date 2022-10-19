package rs.rocketbyte.wifisilencer.ui.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import rs.rocketbyte.wifisilencer.core.usecase.UseCase
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel()