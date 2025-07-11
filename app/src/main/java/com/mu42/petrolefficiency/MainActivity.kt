package com.mu42.petrolefficiency

import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mu42.petrolefficiency.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var isPress=true
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(
            PreferenceManager.getDefaultSharedPreferences(this)
                .getInt("theme_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        )
        setContentView(binding.root)

//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, MainFragment())
//            .commit()
binding.reversebtn.setOnClickListener {

    if(isPress)
    {
        switchToMainFragment()
        isPress=false
    }
    else
    {
        switchToFuelRequiredFragment()
        isPress=true
    }
}


    }

    fun switchToFuelRequiredFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FuelRequiredFragment())
            .addToBackStack(null)
            .commit()
    }

    fun switchToMainFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment())
            .commit()
    }
}
