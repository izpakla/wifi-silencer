package rs.rocketbyte.wifisilencer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import rs.rocketbyte.wifisilencer.R
import rs.rocketbyte.wifisilencer.core.model.RingerMode
import rs.rocketbyte.wifisilencer.core.model.WifiRingerInfo
import rs.rocketbyte.wifisilencer.databinding.FragmentHomeBinding
import rs.rocketbyte.wifisilencer.ui.commons.BindingFragment
import rs.rocketbyte.wifisilencer.ui.home.dialogs.AddWifiDialog
import rs.rocketbyte.wifisilencer.ui.home.dialogs.SoundModeChangeListener
import rs.rocketbyte.wifisilencer.ui.home.dialogs.SoundModeDialog
import rs.rocketbyte.wifisilencer.ui.main.MainViewModel

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(), SoundModeChangeListener {

    private val mainViewModel: MainViewModel by activityViewModels()

    private val adapter: WifiAdapter by lazy {
        WifiAdapter(
            mainViewModel.wifiDataList.value ?: emptyList(),
            mainViewModel.getDefaultMode(),
            object : WiFiAdapterClickListener {
                override fun onItemClicked(wifiData: WifiRingerInfo) {
                    SoundModeDialog.showDialog(childFragmentManager, wifiData)
                }

                override fun onItemMenuClicked(view: View, wifiData: WifiRingerInfo) {
                    createWiFiItemMenu(view, {
                        mainViewModel.removeWifiData(wifiData)
                        adapter.remove(wifiData.ssid)
                    }, {
                        AddWifiDialog.showEditDialog(wifiData, childFragmentManager)
                    })
                }

                override fun onHeaderClicked() {
                    SoundModeDialog.showDialog(childFragmentManager)
                }

            }
        )
    }

    override fun onBinderCreate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        mainViewModel.wifiDataList.observe(viewLifecycleOwner) {
            adapter.onItemsUpdated(it)
        }

        mainViewModel.defaultMode.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            adapter.notifyDefaultModeUpdated(it)
        }
    }

    override fun onSoundModeChanged(wifiData: WifiRingerInfo, soundMode: RingerMode) {
        mainViewModel.onSoundModeChanged(wifiData, soundMode)
    }

    override fun onDefaultSoundModeChanged(soundMode: RingerMode) {
        adapter.notifyDefaultModeUpdated(soundMode)
    }

    private fun createWiFiItemMenu(view: View, onRemoved: () -> Unit, onEdited: () -> Unit) {
        val popup = PopupMenu(view.context, view)
        popup.inflate(R.menu.list_item_menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_remove -> {
                    onRemoved.invoke()
                    true
                }
                R.id.action_edit -> {
                    onEdited.invoke()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

}