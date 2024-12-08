package com.ncorti.kotlin.template.app.data

data class Test(
    val testId : Int,
    val testName : String,
    val testDescription : String,
    val testPhoto : Int,
    val testDuration : String,
    val numberOfQuestions : Int,
    val questions: ArrayList<Question>?
)