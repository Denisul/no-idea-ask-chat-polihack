package com.ncorti.kotlin.template.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ncorti.kotlin.template.app.databinding.PremiumResultFragmentBinding

class PremiumResultFragment: Fragment() {

    private lateinit var binding: PremiumResultFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PremiumResultFragmentBinding.inflate(layoutInflater)

        return binding.root
    }

}