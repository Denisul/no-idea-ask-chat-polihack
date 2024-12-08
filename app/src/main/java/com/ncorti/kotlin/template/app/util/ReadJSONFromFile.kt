package com.ncorti.kotlin.template.app.util

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.ncorti.kotlin.template.app.data.Question
import java.io.BufferedReader
import java.io.InputStreamReader

fun ReadJSONFromFile(context: Context, path: String): ArrayList<Question>? {
    var questions = ArrayList<Question>()
    try {
        val file = context.assets.open(path)

        val bufferedReader = BufferedReader(InputStreamReader(file))
        val stringBuilder = StringBuilder()
        bufferedReader.useLines { lines ->
            lines.forEach {
                stringBuilder.append(it)
            }
        }
        val jsonString = stringBuilder.toString()
        questions = Gson().fromJson(jsonString, Array<Question>::class.java).toList() as ArrayList<Question>
    }
    catch (e : Exception) {
        Log.e("ERROR", e.toString())
    }
    return questions
}