package rs.rocketbyte.wifisilencer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import rs.rocketbyte.wifisilencer.databinding.FragmentHomeBinding
import rs.rocketbyte.wifisilencer.ui.commons.BindingFragment
import rs.rocketbyte.wifisilencer.ui.main.MainViewModel

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>() {

    // Example use, remove if not needed
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: HomeViewModel by viewModels()

    override fun onBinderCreate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonDetails.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment())
        }
    }

}