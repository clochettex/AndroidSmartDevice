package fr.isen.bonnefond.androidsmartdevice

import android.annotation.SuppressLint
import android.bluetooth.*
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import fr.isen.bonnefond.androidsmartdevice.databinding.ActivityDeviceDetailsBinding
import java.util.*
class DeviceDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeviceDetailsBinding
    private var compteur = 0

    private var notifications = false

    private var brightBlue = false
    private var brightYellow = false
    private var brightRed = false

    private var bulb1Image = R.drawable.light_bulb_grey_icon
    private var bulb2Image = R.drawable.light_bulb_grey_icon
    private var bulb3Image = R.drawable.light_bulb_grey_icon

    private val bluetoothAdapter: BluetoothAdapter? by lazy(LazyThreadSafetyMode.NONE) {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }
    private var bluetoothGatt: BluetoothGatt? = null

    //allumer les leds
    private val serviceUUID = UUID.fromString("0000feed-cc7a-482a-984a-7f2ed5b3e58f")
    private val characteristicUUID = UUID.fromString("0000abcd-8e22-4541-9d4c-21edae82ed19")

    //boutons cliquables
    private val characteristicButtonUUID = UUID.fromString("00001234-8e22-4541-9d4c-21edae82ed19")
    private val configNotifications = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")

    @SuppressLint("MissingPermission", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val deviceName = intent.getStringExtra("DEVICE_NAME")
        val deviceAddress =  intent.getStringExtra("DEVICE_MAC")
        binding.deviceName.text = deviceName

        val device = bluetoothAdapter!!.getRemoteDevice(deviceAddress)
        bluetoothGatt = device.connectGatt(this, false, gattCallback)

        binding.group.visibility = View.GONE

        binding.bulb1.setImageResource(bulb1Image)
        binding.bulb2.setImageResource(bulb2Image)
        binding.bulb3.setImageResource(bulb3Image)

        binding.bulb1.setOnClickListener(){
            toggleAction(1)
        }
        binding.bulb2.setOnClickListener(){
            toggleAction(2)
        }
        binding.bulb3.setOnClickListener() {
            toggleAction(3)
        }
    }
    private fun toggleAction(bulbNumber: Int) {
        when (bulbNumber) {
            1 -> {
                if(!brightBlue) {
                    bulb1Image = R.drawable.light_bulb_blue_icon
                    bulb2Image = R.drawable.light_bulb_grey_icon
                    bulb3Image = R.drawable.light_bulb_grey_icon
                    binding.bulb2.setImageResource(bulb2Image)
                    binding.bulb3.setImageResource(bulb3Image)
                    brightRed = false
                    brightYellow = false
                    sendCommand(byteArrayOf(0x01))
                }
                else {
                    bulb1Image = R.drawable.light_bulb_grey_icon
                    sendCommand(byteArrayOf(0x00))
                }
                brightBlue = !brightBlue
                binding.bulb1.setImageResource(bulb1Image)
            }
            2 -> {
                if(!brightYellow) {
                    bulb2Image = R.drawable.light_bulb_yellow_icon
                    bulb1Image = R.drawable.light_bulb_grey_icon
                    bulb3Image = R.drawable.light_bulb_grey_icon
                    binding.bulb1.setImageResource(bulb1Image)
                    binding.bulb3.setImageResource(bulb3Image)
                    brightRed = false
                    brightBlue = false
                    sendCommand(byteArrayOf(0x02))
                }
                else {
                    bulb2Image = R.drawable.light_bulb_grey_icon
                    sendCommand(byteArrayOf(0x00))
                }
                brightYellow = !brightYellow
                binding.bulb2.setImageResource(bulb2Image)
            }
            3 -> {
                if(!brightRed) {
                    bulb3Image = R.drawable.light_bulb_red_icon
                    bulb1Image = R.drawable.light_bulb_grey_icon
                    bulb2Image = R.drawable.light_bulb_grey_icon
                    binding.bulb1.setImageResource(bulb1Image)
                    binding.bulb2.setImageResource(bulb2Image)
                    brightBlue = false
                    brightYellow = false
                    sendCommand(byteArrayOf(0x03))
                }
                else {
                    bulb3Image = R.drawable.light_bulb_grey_icon
                    sendCommand(byteArrayOf(0x00))
                }
                binding.bulb3.setImageResource(bulb3Image)
                brightRed = !brightRed
            }
        }
    }
    private fun show(){
        runOnUiThread {
            binding.group.visibility = View.VISIBLE
        }
    }
    private val gattCallback = object : BluetoothGattCallback() {
        @SuppressLint("MissingPermission", "SetTextI18n")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    show()
                    bluetoothGatt?.discoverServices()
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    runOnUiThread {
                        binding.group.visibility = View.GONE

                        val color = Color.parseColor("#F80D1B")
                        binding.connectedTextView.setTextColor(color)
                        binding.connectedTextView.text ="Disconnected"
                        binding.connectedImageView.setImageResource(R.drawable.disconnected_icon)
                    }
                    bluetoothGatt?.close()
                }
                else -> {
                    Log.d("STATUS", "Connection state changed: $newState")
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            when (status) {
                BluetoothGatt.GATT_SUCCESS -> {
                    Log.d("STATUS", "Services discovered successfully.")
                    val service = gatt?.getService(serviceUUID)
                    val characteristicButton = service?.getCharacteristic(characteristicButtonUUID)
                    binding.checkBox.setOnClickListener {
                        if(!notifications) {
                            characteristicButton?.let { enableNotifications(it) }
                        } else {
                            characteristicButton?.let { disableNotifications(it) }
                        }
                    }
                }
                else -> {
                    Log.d("STATUS", "Service discovery failed: $status")
                }
            }
        }

        @Deprecated("Deprecated in Java")
        override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
            if(characteristic?.uuid == characteristicUUID) {
                val value = characteristic?.toString()
                Log.d("Bluetooth", "Received value: $value")
            }
            if(characteristic?.uuid == characteristicButtonUUID) {
                val value = characteristic?.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0)
                runOnUiThread {
                    binding.nombre.text = value.toString()
                }

                Log.d("Bluetooth", "Received value: $value")
            }
        }
    }
    @SuppressLint("MissingPermission")
    fun enableNotifications(characteristic: BluetoothGattCharacteristic) {
        val descriptor = characteristic.getDescriptor(configNotifications)
        descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
        bluetoothGatt?.writeDescriptor(descriptor)
        bluetoothGatt?.setCharacteristicNotification(characteristic, true)
        notifications = true
    }
    @SuppressLint("MissingPermission")
    fun disableNotifications(characteristic: BluetoothGattCharacteristic) {
        bluetoothGatt?.setCharacteristicNotification(characteristic, false)
        notifications = false
    }
    @SuppressLint("MissingPermission")
    fun sendCommand(command: ByteArray) {
        val service = bluetoothGatt?.getService(serviceUUID)
        val characteristic = service?.getCharacteristic(characteristicUUID)
        characteristic?.setValue(command)
        bluetoothGatt?.writeCharacteristic(characteristic)
    }
}