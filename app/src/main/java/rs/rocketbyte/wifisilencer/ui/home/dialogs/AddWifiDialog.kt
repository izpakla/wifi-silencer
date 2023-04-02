package rs.rocketbyte.wifisilencer.ui.home.dialogs

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import rs.rocketbyte.wifisilencer.R
import rs.rocketbyte.wifisilencer.core.model.WifiRingerInfo
import rs.rocketbyte.wifisilencer.databinding.DialogAddWifiBinding
import rs.rocketbyte.wifisilencer.ui.commons.getParcelableOrNull
import rs.rocketbyte.wifisilencer.ui.main.MainViewModel

class AddWifiDialog : DialogFragment(), View.OnClickListener {

    private val listener by lazy {
        parentFragment as? AddWifiListener ?: context as? AddWifiListener
    }

    private val wifiData by lazy {
        arguments?.getParcelableOrNull(ARG_WIFI_DATA, WifiRingerInfo::class.java)
    }

    private val mainViewModel: MainViewModel by activityViewModels()

    private var _binding: DialogAddWifiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogAddWifiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextSSID.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.textInputSSID.error = null
                binding.textInputSSID.isErrorEnabled = false
            }

        })

        binding.buttonCurrentSSID.setOnClickListener(this)
        binding.buttonAdd.setOnClickListener(this)
        binding.buttonCancel.setOnClickListener(this)

        wifiData?.let {
            binding.editTextSSID.setText(it.ssid)
            binding.editTextDescription.setText(it.description)

            binding.textViewTitle.setText(R.string.edit_network)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onLoadCurrentSSID() {
        val ssid = mainViewModel.getCurrentSsid()
        if (ssid == null) {
            binding.textInputSSID.error = getString(R.string.unable_to_get_ssid)
        } else {
            binding.editTextSSID.setText(ssid)
        }
    }

    private fun onAddSSID() {
        val context = context ?: return
        val ssid = binding.editTextSSID.text.toString()
        when {
            ssid.isBlank() -> binding.textInputSSID.error = getString(R.string.enter_ssid_error)
            isSSIDAdded(ssid) -> binding.textInputSSID.error =
                getString(R.string.already_added_ssid_error)
            else -> {
                val description =
                    binding.editTextDescription.text.toString().takeIf { it.isNotBlank() }
                val oldWifiData = wifiData
                if (oldWifiData != null) {
                    listener?.onWifiEdited(oldWifiData, ssid, description)
                } else {
                    listener?.onWifiAdded(ssid, description)
                }
                dismiss()
            }
        }
    }

    private fun isSSIDAdded(ssid: String): Boolean {
        val wifiData = wifiData
        val inList = mainViewModel.isSsidAdded(ssid)
        return if (wifiData != null) {
            inList && ssid != wifiData.ssid
        } else {
            inList
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonCurrentSSID -> {
                onLoadCurrentSSID()
            }
            R.id.buttonAdd -> {
                onAddSSID()
            }
            R.id.buttonCancel -> {
                dismiss()
            }
        }
    }

    companion object {

        private const val ARG_WIFI_DATA = "ARG_WIFI_DATA"

        private fun newInstance(wifiData: WifiRingerInfo?): AddWifiDialog = AddWifiDialog().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_WIFI_DATA, wifiData)
            }
        }

        fun showEditDialog(wifiData: WifiRingerInfo, fragmentManager: FragmentManager) {
            newInstance(wifiData).show(fragmentManager, "add_wifi_dialog")
        }

        fun showAddDialog(fragmentManager: FragmentManager) {
            newInstance(null).show(fragmentManager, "edit_wifi_dialog")
        }
    }

}

interface AddWifiListener {
    fun onWifiAdded(ssid: String, description: String?)
    fun onWifiEdited(oldWifiData: WifiRingerInfo, ssid: String, description: String?)
}