package com.ncorti.kotlin.template.app.data

import android.os.Parcelable
import kotlinx.serialization.Serializable

data class Question(
    val questionId: Int,
    val questionHeadingContent: String,
    val questionSubtextContent: String,
    var answerValue: Int = 3,
    var answered : Boolean = false
) : java.io.Serializable