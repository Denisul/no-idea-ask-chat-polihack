package com.ncorti.kotlin.template.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ncorti.kotlin.template.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // openAi ApiKey: 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
}
