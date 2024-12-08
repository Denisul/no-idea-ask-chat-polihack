package com.example.fivefactorfinder

import android.util.Log
import com.ncorti.kotlin.template.app.data.Question

class EvaluatePersonality_gfps {
    private var numOfQuestions = 13 // Total number of GFPS questions

    fun evaluate(questionsList: List<Question>, sex: String, age: Int): MutableMap<String, Int> {

        // Simulate or extract user responses from the provided questions list
        val responseList = mutableListOf<Int>()
        repeat(numOfQuestions) { index ->
            responseList.add(3) // Default placeholder value for testing
        }

        // Replace default placeholder answers with real responses
        for (i in 0 until numOfQuestions) {
            responseList[i] = questionsList[i].answerValue
        }

        Log.v("userGFPSResponses", "${responseList}")

        // Initialize flow dimensions
        val challengeSkillsBalance = responseList[0] + responseList[2] + responseList[4] + responseList[6] + responseList[10]
        val clearGoals = responseList[1] + responseList[3] + responseList[7]
        val feedback = responseList[5] + responseList[8]
        val immersion = responseList[9] + responseList[11] + responseList[12]

        Log.v("rawFlowScores", "Challenge/Skills Balance: $challengeSkillsBalance, Clear Goals: $clearGoals, Feedback: $feedback, Immersion: $immersion")

        // Normalize scores to 0 - 100 range
        val normalizationFactor = 13.0*5 // Total number of questions
        val normalizedChallengeSkillsBalance = (challengeSkillsBalance / normalizationFactor * 100).toInt()
        val normalizedClearGoals = (clearGoals / normalizationFactor * 100).toInt()
        val normalizedFeedback = (feedback / normalizationFactor * 100).toInt()
        val normalizedImmersion = (immersion / normalizationFactor * 100).toInt()

        // Map normalized scores into a dictionary to return
        val flowResults = mutableMapOf<String, Int>()
        flowResults["Challenge/Skills Balance"] = normalizedChallengeSkillsBalance
        flowResults["Clear Goals"] = normalizedClearGoals
        flowResults["Feedback"] = normalizedFeedback
        flowResults["Immersion"] = normalizedImmersion

        Log.v("normalizedFlowResults", "$flowResults")
        return flowResults
    }
}
