package fr.isen.bonnefond.androidsmartdevice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.bonnefond.androidsmartdevice.databinding.ActivityDeviceDetailsBinding

class DeviceDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeviceDetailsBinding
    private var compteur1 = 0
    private var compteur2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val deviceName = intent.getStringExtra("DEVICE_NAME")
        binding.deviceName.text = deviceName

        binding.nombreTextView1.setOnClickListener(){
            compteur1++
            binding.nombreTextView1.text = "Nombre de clique sur le bouton principal : $compteur1"
        }
        binding.nombreTextView2.setOnClickListener() {
            compteur2++
            binding.nombreTextView2.text = "Nombre de clique sur le bouton principal : $compteur2"
        }

        binding.bulb1.setOnClickListener(){
            binding.bulb1.setImageResource(R.drawable.light_bulb_blue_icon)
        }
        binding.bulb2.setOnClickListener(){
            binding.bulb2.setImageResource(R.drawable.light_bulb_blue_icon)
        }
        binding.bulb3.setOnClickListener(){
            binding.bulb3.setImageResource(R.drawable.light_bulb_blue_icon)
        }
    }
}