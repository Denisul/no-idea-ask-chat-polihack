package com.ncorti.kotlin.template.app

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.Visibility
import com.ncorti.kotlin.template.app.data.Question
import com.ncorti.kotlin.template.app.databinding.FragmentQuestionaireBinding
import java.util.Random

class QuestionaireFragment() : Fragment() {

    private lateinit var binding: FragmentQuestionaireBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestionaireBinding.inflate(layoutInflater)

        val questions: ArrayList<Question> =
            arguments?.getSerializable("questions", ArrayList::class.java) as ArrayList<Question>

        if (questions.indexOfFirst { !it.answered } != -1) {

            binding.progressIndicator.progress = questions.indexOfFirst { !it.answered } * 100 / questions.size

            Log.e("PLM", questions.indexOfFirst { !it.answered }.toString())
            val resources = arrayOf(R.drawable.q1, R.drawable.q2, R.drawable.q3, R.drawable.q4, R.drawable.q5, R.drawable.q6, R.drawable.q7, R.drawable.q8, R.drawable.q9, R.drawable.q10, R.drawable.q11, R.drawable.q12, R.drawable.q13, R.drawable.q14, R.drawable.q15, R.drawable.q16, R.drawable.q17, R.drawable.q18, R.drawable.q19, R.drawable.q20, R.drawable.q21, R.drawable.q22, R.drawable.q23, R.drawable.q24, R.drawable.q25, R.drawable.q26, R.drawable.q27, R.drawable.q28, R.drawable.q29, R.drawable.q30, R.drawable.q31, R.drawable.q32, R.drawable.q33, R.drawable.q34, R.drawable.q35, R.drawable.q36, R.drawable.q37, R.drawable.q38, R.drawable.q39, R.drawable.q40, R.drawable.q41, R.drawable.q42, R.drawable.q43, R.drawable.q44, R.drawable.q45, R.drawable.q46, R.drawable.q47, R.drawable.q48, R.drawable.q49, R.drawable.q50, R.drawable.q51, R.drawable.q52, R.drawable.q53, R.drawable.q54, R.drawable.q55, R.drawable.q56, R.drawable.q57, R.drawable.q58, R.drawable.q59, R.drawable.q60, R.drawable.q61, R.drawable.q62, R.drawable.q63, R.drawable.q64, R.drawable.q65, R.drawable.q66, R.drawable.q67, R.drawable.q68, R.drawable.q69, R.drawable.q70, R.drawable.q71, R.drawable.q72, R.drawable.q73, R.drawable.q74, R.drawable.q75, R.drawable.q76, R.drawable.q77, R.drawable.q78, R.drawable.q79, R.drawable.q80, R.drawable.q81, R.drawable.q82, R.drawable.q83, R.drawable.q84, R.drawable.q85, R.drawable.q86, R.drawable.q87, R.drawable.q88, R.drawable.q89, R.drawable.q90, R.drawable.q91, R.drawable.q92, R.drawable.q93, R.drawable.q94, R.drawable.q95, R.drawable.q96, R.drawable.q97, R.drawable.q98, R.drawable.q99, R.drawable.q100, R.drawable.q101, R.drawable.q102, R.drawable.q103, R.drawable.q104, R.drawable.q105, R.drawable.q106, R.drawable.q107, R.drawable.q108, R.drawable.q109, R.drawable.q110, R.drawable.q111, R.drawable.q112, R.drawable.q113, R.drawable.q114, R.drawable.q115, R.drawable.q116, R.drawable.q117, R.drawable.q118, R.drawable.q119, R.drawable.q120)
            val number = Random().nextInt(120)
            binding.image.setImageResource(resources[number])
            binding.questionTest.text =
                questions[questions.indexOfFirst { !it.answered }].questionSubtextContent


            binding.nextButton.setOnClickListener {
                questions[questions.indexOfFirst { !it.answered }].answerValue =
                    binding.answerSlider.value.toInt()
                questions[questions.indexOfFirst { !it.answered }].answered = true
                val bundle = Bundle().apply {
                    putSerializable("questions", questions)
                }
                findNavController().navigate(R.id.action_questionaireFragment_self, bundle)
            }
        } else
            findNavController().navigate(R.id.action_questionaireFragment_to_mainFragment2, Bundle().apply {
                putSerializable("questions", questions)
            })
        return binding.root
    }

}