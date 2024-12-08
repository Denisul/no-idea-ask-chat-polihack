package com.ncorti.kotlin.template.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ncorti.kotlin.template.app.databinding.FragmentTestResultBinding

class ResultFragment: Fragment() {

    private lateinit var binding: FragmentTestResultBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestResultBinding.inflate(layoutInflater)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().clearBackStack(R.id.mainFragment2)
            findNavController().navigate(R.id.action_resultFragment_to_mainFragment2)
        }

        binding.upgradeButton.setOnClickListener {
            Log.e("TAG", "HAI COAIE")
            findNavController().navigate(R.id.action_resultFragment_to_premiumResultFragment)
        }

        return binding.root
    }
}