package com.ncorti.kotlin.template.app

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ncorti.kotlin.template.app.data.Question
import com.ncorti.kotlin.template.app.data.Test
import com.ncorti.kotlin.template.app.databinding.FragmentMainBinding
import com.ncorti.kotlin.template.app.util.ReadJSONFromFile

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        findNavController().clearBackStack(R.id.questionaireFragment)
        if (arguments != null) {
            val questions: ArrayList<Question> =
                arguments?.getSerializable(
                    "questions",
                    ArrayList::class.java
                ) as ArrayList<Question>
            Log.e("QUESIONS", questions.toString())
        }

        val dataSet = arrayOf(
            Test(
                testId = 1,
                testName = "Short Big Five Personality Test",
                testDescription = "This test evaluates personality across five traits: Openness , Conscientiousness , Extraversion , Agreeableness , and Neuroticism. It is used for personal growth, workplace insights, and research.",
                testPhoto = R.drawable.q25,
                testDuration = "10 min",
                numberOfQuestions = 10,
                questions = ReadJSONFromFile(requireContext(), "questions_s1.json")!!
            ),
            Test(
                testId = 2,
                testName = "General Flow Proneness Scale",
                testDescription = "This test measures an individual’s tendency to experience flow across three domains: Work, Leisure, and Social Interactions, focusing on key flow elements like challenge-skill balance, clear goals, focus, and intrinsic reward.",
                testPhoto = R.drawable.q48,
                testDuration = "15 min",
                numberOfQuestions = 13,
                questions = ReadJSONFromFile(requireContext(), "GFPS.json")!!
            ),
            Test(
                testId = 3,
                testName = "Buss-Perry Aggression Questionnaire",
                testDescription = "The Buss-Perry Aggression Questionnaire (BPAQ) assesses an individual's levels of aggression. It measures physical aggression, verbal aggression, anger, and hostility.",
                testPhoto = R.drawable.q5,
                testDuration = "32 min",
                numberOfQuestions = 2,
                questions = ReadJSONFromFile(requireContext(), "mock_question.json")!!
            ),
            Test(
                testId = 4,
                testName = "Sensation Seeking Scale",
                testDescription = "The Sensation Seeking Scale (SSS) measures an individual's desire for novel, varied, and intense experiences. It assesses risk-taking, thrill-seeking, and the pursuit of new stimulation.",
                testPhoto = R.drawable.q13,
                testDuration = "25 min",
                numberOfQuestions = 2,
                questions = ReadJSONFromFile(requireContext(), "mock_question.json")!!
            ),
        )
        val adapter = ItemTestsAdapter(dataSet, findNavController())


        val recyclerView: RecyclerView = binding.rvTests
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return binding.root
    }
}