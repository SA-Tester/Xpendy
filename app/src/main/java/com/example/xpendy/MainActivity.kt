package com.example.xpendy

import SettingsFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.xpendy.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener{ item ->
            when(item.itemId){
                R.id.home -> replaceFragment(HomeFragment());
                R.id.money -> replaceFragment(MoneyFragment());
                R.id.overview -> replaceFragment(OverviewFragment());
                R.id.compare -> replaceFragment(CompareFragment());
                R.id.settings -> replaceFragment(SettingsFragment());
            }
            true
        }
    }

    private fun replaceFragment(fragment:Fragment){
        val fragmentManager: FragmentManager = supportFragmentManager;
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

}