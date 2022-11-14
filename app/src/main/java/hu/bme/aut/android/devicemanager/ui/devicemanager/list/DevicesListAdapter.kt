package hu.bme.aut.android.devicemanager.ui.devicemanager.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.devicemanager.databinding.ItemDeviceBinding
import hu.bme.aut.android.devicemanager.domain.model.Device

class DevicesListAdapter : ListAdapter<Device, DevicesListAdapter.DeviceViewHolder>(ItemCallBack) {
    var devices = emptyList<Device>()

    inner class DeviceViewHolder(binding: ItemDeviceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvDeviceName: TextView = binding.deviceName

        var device: Device? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DeviceViewHolder(
        ItemDeviceBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )


    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = devices[position]

        holder.device = device
        holder.tvDeviceName.text = device.name
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    fun addAllDevices(device: List<Device>) {
        devices -= devices
        devices += device
        submitList(devices)
    }

    companion object {

        object ItemCallBack : DiffUtil.ItemCallback<Device>() {
            override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
                return oldItem == newItem
            }
        }
    }
}