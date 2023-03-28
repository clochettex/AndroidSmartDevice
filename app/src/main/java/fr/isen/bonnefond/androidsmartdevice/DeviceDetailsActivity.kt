package fr.isen.bonnefond.androidsmartdevice

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import fr.isen.bonnefond.androidsmartdevice.databinding.ActivityDeviceDetailsBinding
import java.util.*

class DeviceDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeviceDetailsBinding
    private var compteur1 = 0
    private var compteur2 = 0

    private var bright = false

    private var bulb1Image = R.drawable.light_bulb_grey_icon
    private var bulb2Image = R.drawable.light_bulb_grey_icon
    private var bulb3Image = R.drawable.light_bulb_grey_icon

    private val bluetoothAdapter: BluetoothAdapter? by lazy(LazyThreadSafetyMode.NONE) {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val deviceName = intent.getStringExtra("DEVICE_NAME")
        val deviceAddress =  intent.getStringExtra("DEVICE_MAC")
        binding.deviceName.text = deviceName

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
        binding.bulb3.setOnClickListener(){
            toggleAction(3)
        }
    }

    private fun toggleAction(bulbNumber: Int) {
        when (bulbNumber) {
            1 -> {
                if (!bright) {
                    bulb1Image = R.drawable.light_bulb_blue_icon
                } else {
                    bulb1Image = R.drawable.light_bulb_grey_icon
                }
                binding.bulb1.setImageResource(bulb1Image)
            }
            2 -> {
                if (!bright) {
                    bulb2Image = R.drawable.light_bulb_blue_icon
                } else {
                    bulb2Image = R.drawable.light_bulb_grey_icon
                }
                binding.bulb2.setImageResource(bulb2Image)
            }
            3 -> {
                if (!bright) {
                    bulb3Image = R.drawable.light_bulb_blue_icon
                } else {
                    bulb3Image = R.drawable.light_bulb_grey_icon
                }
                binding.bulb3.setImageResource(bulb3Image)
            }
        }
        bright = !bright
    }
}