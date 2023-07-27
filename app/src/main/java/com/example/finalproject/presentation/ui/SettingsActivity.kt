package com.example.finalproject.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.finalproject.R
import com.example.finalproject.databinding.ActivitySettingsBinding
import com.example.finalproject.utils.AppPreferences

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)


//      cek status swtich on off
        val isNightModeOn = AppPreferences.isNightModeOn(this)
        binding.switch1.isChecked = isNightModeOn


        // Di dalam Activity Settings
        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            AppPreferences.setNightMode(this, isChecked)
            applyNightMode(isChecked)
        }



        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }


    // Di dalam MainActivity atau Activity lain yang perlu menerapkan tema
    private fun applyNightMode(isNightModeOn: Boolean) {
        val nightMode =
            if (isNightModeOn) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(nightMode)
        delegate.applyDayNight()
    }

}