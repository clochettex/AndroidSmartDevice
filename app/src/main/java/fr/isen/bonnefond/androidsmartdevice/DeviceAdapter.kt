package fr.isen.bonnefond.androidsmartdevice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.bonnefond.androidsmartdevice.databinding.DeviceCellBinding

class DeviceAdapter(private val devices: ScanActivity.Devices, private val onItemClick: (String, String) -> Unit) :
    RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DeviceCellBinding.inflate(inflater,parent,false)
        return DeviceViewHolder(binding)
    }
    class DeviceViewHolder(binding: DeviceCellBinding):RecyclerView.ViewHolder(binding.root){
        val deviceName = binding.deviceName
        val macAddress = binding.macAddress
        val distanceNumber = binding.distanceNumber
    }

    override fun getItemCount(): Int = devices.size

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        //val device = devices[position]
        holder.deviceName.text = devices.device_name[position]
        holder.macAddress.text = devices.MAC[position]
        holder.distanceNumber.text = devices.distance[position].toString()

        holder.itemView.setOnClickListener {
            onItemClick(devices.device_name[position], devices.MAC[position])
        }
    }
}