package rs.rocketbyte.wifisilencer.ui.home.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import rs.rocketbyte.wifisilencer.R
import rs.rocketbyte.wifisilencer.core.model.RingerMode
import rs.rocketbyte.wifisilencer.core.model.WifiRingerInfo
import rs.rocketbyte.wifisilencer.databinding.DialogSoundModeBinding
import rs.rocketbyte.wifisilencer.ui.commons.getParcelableOrNull

class SoundModeDialog : DialogFragment(), View.OnClickListener {

    private val listener by lazy {
        parentFragment as? SoundModeChangeListener ?: context as? SoundModeChangeListener
    }

    private val wifiData by lazy {
        arguments?.getParcelableOrNull(ARG_WIFI_DATA, WifiRingerInfo::class.java)
    }

    private var _binding: DialogSoundModeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogSoundModeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewModeNormal.setOnClickListener(this)
        binding.textViewModeVibration.setOnClickListener(this)
        binding.textViewModeSilent.setOnClickListener(this)

        binding.buttonCancel.setOnClickListener(this)

        val wifiData = wifiData
        if (wifiData == null) {
            binding.textViewTitle.setText(R.string.sound_mode_default)
        } else {
            binding.textViewTitle.text = getString(R.string.sound_mode, wifiData.ssid)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textViewModeNormal -> onSoundModeChanged(RingerMode.NORMAL)
            R.id.textViewModeVibration -> onSoundModeChanged(RingerMode.VIBRATE)
            R.id.textViewModeSilent -> onSoundModeChanged(RingerMode.SILENT)
            R.id.buttonCancel -> dismiss()
        }
    }

    private fun onSoundModeChanged(soundMode: RingerMode) {
        val wifiData = wifiData
        if (wifiData == null) {
            listener?.onDefaultSoundModeChanged(soundMode)
        } else {
            listener?.onSoundModeChanged(wifiData, soundMode)
        }
        dismiss()
    }

    companion object {

        private const val ARG_WIFI_DATA = "ARG_WIFI_DATA"

        private fun newInstance(wifiData: WifiRingerInfo?): SoundModeDialog =
            SoundModeDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_WIFI_DATA, wifiData)
                }
            }

        fun showDialog(fragmentManager: FragmentManager, wifiData: WifiRingerInfo? = null) {
            newInstance(wifiData).show(fragmentManager, "change_sound_mode_dialog")
        }
    }
}

interface SoundModeChangeListener {
    fun onSoundModeChanged(wifiData: WifiRingerInfo, soundMode: RingerMode)
    fun onDefaultSoundModeChanged(soundMode: RingerMode)
}
