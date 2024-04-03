package com.example.githubuser.data.ui.userinterface

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.R
import com.example.githubuser.data.ui.helper.ModeSettingModelFactory
import com.example.githubuser.data.ui.helper.SettingPreferences
import com.example.githubuser.data.ui.helper.dataStore
import com.example.githubuser.data.ui.modelView.ModeSettingViewModel
import com.google.android.material.switchmaterial.SwitchMaterial

class ModeSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode_setting)
        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val modeSettingViewModel = ViewModelProvider(this, ModeSettingModelFactory(pref)).get(
            ModeSettingViewModel::class.java
        )

        modeSettingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            modeSettingViewModel.saveThemeSetting(isChecked)
        }
    }
}