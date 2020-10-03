package com.devsunilkumar.datastoreandroidkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var settingsManager: SettingsManager
    private var isDarkMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingsManager = SettingsManager(applicationContext)

        observeUiPreferences()
        initViews()



    }


    private fun initViews() {
        imageButton.setOnClickListener {
            lifecycleScope.launch {
                when (isDarkMode) {
                    true -> settingsManager.setUiMode(UiMode.LIGHT)
                    false -> settingsManager.setUiMode(UiMode.DARK)
                }
            }
        }
    }

    private fun observeUiPreferences() {
        settingsManager.uiModeFlow.asLiveData().observe(this) { uiMode ->
            when (uiMode) {
                UiMode.LIGHT -> onLightMode()
                UiMode.DARK -> onDarkMode()
            }
        }
    }

    private fun onLightMode() {
        isDarkMode = false
        day_night.text ="NIGHT"


        imageButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.black))
        imageButton.setImageResource(R.drawable.ic_moon)

        // Actually turn on Light mode using AppCompatDelegate.setDefaultNightMode() here
    }

    private fun onDarkMode() {
        isDarkMode = true
        day_night.text ="DAY"


        imageButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))
        imageButton.setImageResource(R.drawable.ic_sun)

        // Actually turn on Dark mode using AppCompatDelegate.setDefaultNightMode() here
    }


}