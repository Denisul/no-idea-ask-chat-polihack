package com.ncorti.kotlin.template.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ncorti.kotlin.template.app.databinding.ActivityMainBinding
import com.ncorti.kotlin.template.library.FactorialCalculator
import com.ncorti.kotlin.template.library.android.ToastUtil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.buttonCompute.setOnClickListener {
//            val message = if (binding.editTextFactorial.text.isNotEmpty()) {
//                val input = binding.editTextFactorial.text.toString().toLong()
//                val result = try {
//                    FactorialCalculator.computeFactorial(input).toString()
//                } catch (ex: IllegalStateException) {
//                    "Error: ${ex.message}"
//                }
//
//                binding.textResult.text = result
//                binding.textResult.visibility = View.VISIBLE
//                getString(R.string.notification_title, result)
//            } else {
//                getString(R.string.please_enter_a_number)
//            }
//            ToastUtil.showToast(this, message)
//        }
//
//        binding.buttonAppcompose.setOnClickListener {
//            val intent = Intent(it.context, ComposeActivity::class.java)
//            startActivity(intent)
//        }
    }

    // openAi ApiKey: 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
}
