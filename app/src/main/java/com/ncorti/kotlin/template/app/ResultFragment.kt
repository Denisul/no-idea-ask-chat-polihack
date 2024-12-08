package com.ncorti.kotlin.template.app

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ncorti.kotlin.template.app.data.Question
import com.ncorti.kotlin.template.app.databinding.FragmentTestResultBinding
import com.ncorti.kotlin.template.app.util.EvaluatePersonality_s1

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentTestResultBinding

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestResultBinding.inflate(layoutInflater)

        val questions: ArrayList<Question> =
            arguments?.getSerializable("questions", ArrayList::class.java) as ArrayList<Question>
        val result = EvaluatePersonality_s1().evaluate(questions, "male", 22)

        binding.neuroticism.apply {
            traitName.text = "Neuroticism"
            traitScore.text = result["Neuroticism"].toString()
            traitProgress.progress = result["Neuroticism"]!!
        }
        binding.extraversion.apply {
            traitName.text = "Extraversion"
            traitScore.text = result["Extraversion"].toString()
            traitProgress.progress = result["Extraversion"]!!
        }
        binding.openness.apply {
            traitName.text = "Openness"
            traitScore.text = result["Openness"].toString()
            traitProgress.progress = result["Openness"]!!
        }
        binding.agreeableness.apply {
            traitName.text = "Agreeableness"
            traitScore.text = result["Agreeableness"].toString()
            traitProgress.progress = result["Agreeableness"]!!
        }
        binding.consciousness.apply {
            traitName.text = "Conscientiousness"
            traitScore.text = result["Conscientiousness"].toString()
            traitProgress.progress = result["Conscientiousness"]!!
        }

        val bundle = Bundle().apply {
            putInt("Neuroticism", result["Neuroticism"]!!)
            putInt("Extraversion", result["Extraversion"]!!)
            putInt("Openness", result["Openness"]!!)
            putInt("Agreeableness", result["Agreeableness"]!!)
            putInt("Conscientiousness", result["Conscientiousness"]!!)
        }

        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().clearBackStack(R.id.mainFragment2)
            findNavController().navigate(R.id.action_resultFragment_to_mainFragment2)
        }

        binding.upgradeButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_resultFragment_to_premiumResultFragment, bundle
            )
        }

        return binding.root
    }
}