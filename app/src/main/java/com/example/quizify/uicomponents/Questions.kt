package com.example.quizify.uicomponents

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizify.screens.QuizViewModel

@Composable
fun BuildQuestions(viewModel: QuizViewModel) {

}

@Preview(showBackground = true)
@Composable
fun BuildTopHeader(modifier: Modifier = Modifier, totalQuestions: Int = 23423) {

    val currentQuestionNumber by remember {
        mutableStateOf(0)
    }

    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Black, fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)) {
            append("Question $currentQuestionNumber/")
        }
        withStyle(style = SpanStyle(color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Light)) {
            append(totalQuestions.toString())
        }
    }

    Text(
        annotatedText,
        modifier.padding(horizontal = 5.dp)
    )

   VerticalDivider(modifier = Modifier.padding(2.dp), thickness = 2.dp, color = Color.LightGray)
}