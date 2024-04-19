package com.example.foodies.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun highlightText(searchText: String, fullText: String): AnnotatedString {
    val startIndex = fullText.indexOf(searchText, ignoreCase = true)
    return if (startIndex != -1) {
        val endIndex = startIndex + searchText.length
        buildAnnotatedString {
            append(fullText.substring(0, startIndex))
            withStyle(style = SpanStyle(color = Color.Red)) {
                append(fullText.substring(startIndex, endIndex))
            }
            append(fullText.substring(endIndex, fullText.length))
        }
    } else {
        AnnotatedString(fullText)
    }
}