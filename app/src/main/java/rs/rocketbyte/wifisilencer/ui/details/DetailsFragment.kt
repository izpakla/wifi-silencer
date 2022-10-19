package rs.rocketbyte.wifisilencer.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import rs.rocketbyte.wifisilencer.databinding.FragmentDetailsBinding
import rs.rocketbyte.wifisilencer.ui.commons.BindingFragment
import rs.rocketbyte.wifisilencer.ui.main.MainViewModel

@AndroidEntryPoint
class DetailsFragment : BindingFragment<FragmentDetailsBinding>() {

    // Example use, remove if not needed
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: DetailsViewModel by viewModels()

    override fun onBinderCreate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Init views here
    }

}