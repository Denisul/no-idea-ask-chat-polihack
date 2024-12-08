package com.ncorti.kotlin.template.app

import PersonalityAnalysisResponse
import PersonalityAnalysisService
import PersonalityTraits
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ncorti.kotlin.template.app.databinding.PremiumResultFragmentBinding
import com.ncorti.kotlin.template.app.util.ApiKeys
import kotlinx.coroutines.runBlocking

class PremiumResultFragment : Fragment() {

    private lateinit var binding: PremiumResultFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = PremiumResultFragmentBinding.inflate(layoutInflater)
        var neuroticism_ans = 0
        var extroversion_ans = 0
        var openness_ans = 0
        var agreeableness_ans = 0
        var conscientiousness_ans = 0

        arguments?.apply {
            neuroticism_ans = getInt("neuroticism")
            extroversion_ans = getInt("extraversion")
            openness_ans = getInt("openness")
            agreeableness_ans = getInt("agreeableness")
            conscientiousness_ans = getInt("conscientiousness")
        }

        val apiServce = PersonalityAnalysisService(ApiKeys.openAiKey)

        val traits = PersonalityTraits(
            neuroticism = neuroticism_ans,
            extroversion = extroversion_ans,
            openness = openness_ans,
            agreeableness = agreeableness_ans,
            conscientiousness = conscientiousness_ans
        )

        val result = runBlocking { apiServce.analyzePersonality(traits) }

        result.onSuccess { response ->

        }.onFailure {
            println("Error: ${it.message}" + it.stackTrace)
        }

        return binding.root
    }

}