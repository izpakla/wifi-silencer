package rs.rocketbyte.wifisilencer.ui.home

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import rs.rocketbyte.wifisilencer.R
import rs.rocketbyte.wifisilencer.core.model.RingerMode
import rs.rocketbyte.wifisilencer.core.model.WifiRingerInfo
import rs.rocketbyte.wifisilencer.databinding.ListItemWifiBinding
import rs.rocketbyte.wifisilencer.databinding.ListItemWifiFooterBinding
import rs.rocketbyte.wifisilencer.databinding.ListItemWifiHeaderBinding
import java.text.DateFormat


class WifiAdapter(
    wifiDataList: List<WifiRingerInfo>,
    private var defaultRingerMode: RingerMode,
    private val wiFiAdapterClickListener: WiFiAdapterClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = ArrayList<WifiRingerInfo>().apply {
        addAll(wifiDataList)
    }

    fun remove(ssid: String) {
        var i = 0
        val iterator = items.iterator()
        while (iterator.hasNext()) {
            val (ssid1) = iterator.next()
            if (TextUtils.equals(ssid1, ssid)) {
                iterator.remove()
                notifyItemRemoved(i + 1)
            }
            i++
        }
    }

    fun addItem(data: WifiRingerInfo) {
        this.items.add(data)
        notifyItemInserted(items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HEADER_ITEM -> ViewHolderHeader(
                ListItemWifiHeaderBinding.inflate(inflater, parent, false),
                wiFiAdapterClickListener
            )
            FOOTER_ITEM -> ViewHolderFooter(
                ListItemWifiFooterBinding.inflate(inflater, parent, false)
            )
            else -> ViewHolderDefault(
                ListItemWifiBinding.inflate(inflater, parent, false),
                wiFiAdapterClickListener
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HEADER_ITEM
            itemCount - 1 -> FOOTER_ITEM
            else -> DEFAULT_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(items[position - 1]) {
            when (holder) {
                is ViewHolderDefault -> {

                    holder.binding.textViewTitle.text = ssid

                    val date = DateFormat.getDateInstance(DateFormat.SHORT).format(dateAdded)
                    holder.binding.textViewDate.text =
                        holder.itemView.resources.getString(R.string.ssid_added, date)

                    if (TextUtils.isEmpty(description)) {
                        holder.binding.textViewSubTitle.visibility = View.GONE
                    } else {
                        holder.binding.textViewSubTitle.text = description
                        holder.binding.textViewSubTitle.visibility = View.VISIBLE
                    }

                    setSoundMode(holder.binding.imageButtonSelection, ringerMode)

                    holder.binding.imageButtonSelection.tag = this
                    holder.binding.imageViewMenu.tag = this
                }
                is ViewHolderHeader -> {
                    setSoundMode(
                        holder.binding.imageButtonSelection,
                        defaultRingerMode
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size + 2
    }

    fun notifyDefaultModeUpdated(ringerMode: RingerMode) {
        defaultRingerMode = ringerMode
        notifyItemChanged(0)
    }

    fun notifyItemModeUpdated(ssid: String) {
        var i = 0
        val itemsSize = items.size
        while (i < itemsSize) {
            val (ssid1) = items[i]
            if (TextUtils.equals(ssid1, ssid)) {
                notifyItemChanged(i + 1)
                return
            }
            i++
        }
    }

    fun notifyItemUpdated(oldWifiData: WifiRingerInfo, newWiFiData: WifiRingerInfo) {
        var i = 0
        val itemsSize = items.size
        while (i < itemsSize) {
            val ssid = items[i].ssid
            if (TextUtils.equals(ssid, oldWifiData.ssid)) {
                items.removeAt(i)
                items.add(i, newWiFiData)
                notifyItemChanged(i + 1)
                return
            }
            i++
        }
    }

    fun onItemsUpdated(it: List<WifiRingerInfo>?) {
        items.clear()
        items.addAll(it ?: emptyList())
        notifyDataSetChanged()
    }

    private class ViewHolderDefault constructor(
        val binding: ListItemWifiBinding,
        wiFiAdapterClickListener: WiFiAdapterClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imageButtonSelection.setOnClickListener { v ->
                val wifiData = v.tag as? WifiRingerInfo
                if (wifiData != null) {
                    wiFiAdapterClickListener.onItemClicked(wifiData)
                }
            }

            binding.imageViewMenu.setOnClickListener {
                val wifiData = it.tag as? WifiRingerInfo
                if (wifiData != null) {
                    wiFiAdapterClickListener.onItemMenuClicked(it, wifiData)
                }
            }
        }
    }

    private class ViewHolderHeader constructor(
        val binding: ListItemWifiHeaderBinding,
        wiFiAdapterClickListener: WiFiAdapterClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imageButtonSelection.setOnClickListener { wiFiAdapterClickListener.onHeaderClicked() }
        }
    }

    private class ViewHolderFooter constructor(val binding: ListItemWifiFooterBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val DEFAULT_ITEM = 0
        private const val HEADER_ITEM = 1
        private const val FOOTER_ITEM = 2

        private fun setSoundMode(imageButton: ImageView, mode: RingerMode) {
            when (mode) {
                RingerMode.NORMAL -> {
                    imageButton.setImageResource(R.drawable.baseline_notifications_active_24)
                    imageButton.setBackgroundResource(R.drawable.sound_mode_normal)
                }
                RingerMode.SILENT -> {
                    imageButton.setImageResource(R.drawable.baseline_notifications_off_24)
                    imageButton.setBackgroundResource(R.drawable.sound_mode_silent)
                }
                RingerMode.VIBRATE -> {
                    imageButton.setImageResource(R.drawable.baseline_vibration_24)
                    imageButton.setBackgroundResource(R.drawable.sound_mode_vibrate)
                }
            }
        }
    }
}

interface WiFiAdapterClickListener {
    fun onItemClicked(wifiData: WifiRingerInfo)
    fun onItemMenuClicked(view: View, wifiData: WifiRingerInfo)
    fun onHeaderClicked()
}
