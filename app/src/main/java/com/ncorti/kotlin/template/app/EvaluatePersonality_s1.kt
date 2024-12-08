package com.example.fivefactorfinder

import android.util.Log
import com.ncorti.kotlin.template.app.data.Question

class EvaluatePersonality_s1 {
    private var numofquestions = 10

    suspend fun evaluate(questionsList: List<Question>, sex: String, age: Int): MutableMap<String, Int> {

        // Create the answer's list (simulate answer values from the user or test responses)
        val qlist = mutableListOf<Int>()
        repeat(10) { index ->
            qlist.add(3) // Default placeholder value for all questions
        }

        // Replace with actual answer values from question responses
        for (i in 0 until numofquestions) {
            qlist[i] = questionsList[i].answerValue
        }
        Log.v("userAnswers", "${qlist}")

        // Compute the personality scores for each Big Five personality dimension: N, E, O, A, C
        val ss = MutableList(5) { 0 } // Scores for 5 personality dimensions (Neuroticism, Extraversion, Openness, Agreeableness, Conscientiousness)

        // Map each question to its respective Big Five personality dimensions
        for (i in 0 until numofquestions) {
            when (i % 5) { // Map questions cyclically
                0 -> ss[0] += qlist[i] // Neuroticism
                1 -> ss[1] += qlist[i] // Extraversion
                2 -> ss[2] += qlist[i] // Openness
                3 -> ss[3] += qlist[i] // Agreeableness
                4 -> ss[4] += qlist[i] // Conscientiousness
            }
        }
        Log.v("personalityScores", "$ss")

        // Normalize the scores with a simple normalization based on average mapping
        val normFactor = 10.0 // Normalize scores to a 0-100 scale based on the number of questions
        val normalizedScores = ss.map { (it / numofquestions.toDouble() * normFactor).toInt() }

        // Map normalized scores into a personality trait result dictionary
        val personalityResults = mutableMapOf<String, Int>()
        personalityResults["Neuroticism"] = normalizedScores[0]
        personalityResults["Extraversion"] = normalizedScores[1]
        personalityResults["Openness"] = normalizedScores[2]
        personalityResults["Agreeableness"] = normalizedScores[3]
        personalityResults["Conscientiousness"] = normalizedScores[4]

        Log.v("normalizedResults", "$personalityResults")
        return personalityResults
    }
}