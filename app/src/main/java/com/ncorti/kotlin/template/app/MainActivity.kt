package com.ncorti.kotlin.template.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ncorti.kotlin.template.app.databinding.ActivityMainBinding
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        // Set up ViewPager with fragments
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        // Connect TabLayout with ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Analysis"
                1 -> "Recommendations"
                else -> ""
            }
        }.attach()
    }

    // openAi ApiKey: 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
}
