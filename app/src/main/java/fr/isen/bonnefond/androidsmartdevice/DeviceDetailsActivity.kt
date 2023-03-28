package fr.isen.bonnefond.androidsmartdevice

import android.annotation.SuppressLint
import android.bluetooth.*
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import fr.isen.bonnefond.androidsmartdevice.databinding.ActivityDeviceDetailsBinding
import java.util.*

class DeviceDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeviceDetailsBinding
    private var compteur1 = 0
    private var compteur2 = 0

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

    private val serviceUUID = UUID.fromString("0000feed-cc7a-482a-984a-7f2ed5b3e58f")
    private val characteristicUUID = UUID.fromString("0000abcd-8e22-4541-9d4c-21edae82ed19")

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

        binding.nombreTextView1.setOnClickListener(){
            compteur1++
            binding.nombreTextView1.text = "Nombre de clics sur le bouton principal : "
            binding.nombreTextView1.append(SpannableString("$compteur1").apply { setSpan(
                ForegroundColorSpan(Color.parseColor("#5293FD")), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) })
        }
        binding.nombreTextView2.setOnClickListener() {
            compteur2++
            binding.nombreTextView2.text = "Nombre de clics sur le 3e bouton : "
            binding.nombreTextView2.append(SpannableString("$compteur2").apply { setSpan(
                ForegroundColorSpan(Color.parseColor("#5293FD")), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) })
        }



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
                    sendCommand(byteArrayOf(0x01))
                    bulb2Image = R.drawable.light_bulb_grey_icon
                    bulb3Image = R.drawable.light_bulb_grey_icon
                    binding.bulb2.setImageResource(bulb2Image)
                    binding.bulb3.setImageResource(bulb3Image)
                    brightRed = false
                    brightYellow = false
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
                    sendCommand(byteArrayOf(0x02))
                    bulb1Image = R.drawable.light_bulb_grey_icon
                    bulb3Image = R.drawable.light_bulb_grey_icon
                    binding.bulb1.setImageResource(bulb1Image)
                    binding.bulb3.setImageResource(bulb3Image)
                    brightRed = false
                    brightBlue = false
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
                    sendCommand(byteArrayOf(0x03))
                    bulb1Image = R.drawable.light_bulb_grey_icon
                    bulb2Image = R.drawable.light_bulb_grey_icon
                    binding.bulb1.setImageResource(bulb1Image)
                    binding.bulb2.setImageResource(bulb2Image)
                    brightBlue = false
                    brightYellow = false
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
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    show()
                    bluetoothGatt?.discoverServices()
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    runOnUiThread {
                        binding.group.visibility = View.GONE
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
                    val characteristic = service?.getCharacteristic(characteristicUUID)
                    characteristic?.let { enableNotifications(it) }
                }
                else -> {
                    Log.d("STATUS", "Service discovery failed: $status")
                }
            }
        }
        override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
            val value = characteristic?.toString()
            Log.d("Bluetooth", "Received value: $value")
        }
    }

    @SuppressLint("MissingPermission")
    fun enableNotifications(characteristic: BluetoothGattCharacteristic) {
        bluetoothGatt?.setCharacteristicNotification(characteristic, true)
    }
    @SuppressLint("MissingPermission")
    fun sendCommand(command: ByteArray) {
        val service = bluetoothGatt?.getService(serviceUUID)
        val characteristic = service?.getCharacteristic(characteristicUUID)
        characteristic?.setValue(command)
        bluetoothGatt?.writeCharacteristic(characteristic)
    }
}