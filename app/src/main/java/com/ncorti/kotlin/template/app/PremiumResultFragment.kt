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

class AnalysisItemAdapter(private val items: List<String>) :
    RecyclerView.Adapter<AnalysisItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position]
    }

    override fun getItemCount() = items.size
}

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

        neuroticism_ans = arguments?.getInt("Neuroticism")!!
        extroversion_ans = arguments?.getInt("Extraversion")!!
        openness_ans = arguments?.getInt("Openness")!!
        agreeableness_ans = arguments?.getInt("Agreeableness")!!
        conscientiousness_ans = arguments?.getInt("Conscientiousness")!!


        val apiService = PersonalityAnalysisService(ApiKeys.openAiKey)

        val traits = PersonalityTraits(
            neuroticism = neuroticism_ans,
            extroversion = extroversion_ans,
            openness = openness_ans,
            agreeableness = agreeableness_ans,
            conscientiousness = conscientiousness_ans
        )

        val result = runBlocking { apiService.analyzePersonality(traits) }

        result.onSuccess { response ->
            setupUI(response)
        }.onFailure {
            println("Error: ${it.message}" + it.stackTrace)
            // You might want to show an error message to the user here
            binding.fullAnalysisText.text = "An error occurred while analyzing personality"
        }

        return binding.root
    }

    private fun setupUI(response: PersonalityAnalysisResponse) {
        with(binding) {
            // Set the full analysis text
            fullAnalysisText.text = response.fullAnalysis

            // Setup RecyclerViews with LinearLayoutManager
            strengthsList.layoutManager = LinearLayoutManager(context)
            challengesList.layoutManager = LinearLayoutManager(context)
            strategiesList.layoutManager = LinearLayoutManager(context)
            resourcesList.layoutManager = LinearLayoutManager(context)

            // Set up adapters for each RecyclerView
            strengthsList.adapter = AnalysisItemAdapter(response.personalityInsights.strengths)
            challengesList.adapter = AnalysisItemAdapter(response.personalityInsights.challenges)
            strategiesList.adapter = AnalysisItemAdapter(response.developmentStrategies)
            resourcesList.adapter = AnalysisItemAdapter(response.recommendedResources)
        }
    }
}