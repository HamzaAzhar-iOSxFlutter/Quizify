package com.example.quizify.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizify.model.QuizModelItem
import com.example.quizify.utilities.AppColors

@Composable
fun QuizHome(viewModel: QuizViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()

    val questionIndex = remember {
        mutableStateOf(0)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = AppColors.mDarkPurple
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            if (viewModel.data.value.loading == true) {
                CircularProgressIndicator()
            } else {
                if (questions != null && questions.isNotEmpty()) {
                    BuildChoices(
                        modifier = Modifier,
                        questionIndex = questionIndex,
                        quiz = questions[questionIndex.value], // Pass the current question
                        totalQuestions = questions.count()
                    )
                }
            }
        }
    }
}

@Composable
fun DrawDottedDivider(pathEffect: PathEffect) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
        onDraw = {
            drawLine(
                color = AppColors.mLightGray,
                start = Offset(x = 0f, y = 0f),
                end = Offset(size.width, y = 0f),
                pathEffect = pathEffect)
        })
}

@Composable
fun BuildChoices(
    modifier: Modifier,
    questionIndex: MutableState<Int>,
    quiz: QuizModelItem,
    totalQuestions: Int,
    onNextClicked: (Int) -> Unit = {}) {

    val choicesState = remember(quiz.choices) {
        quiz.choices.toMutableList()
    }

    val answerState = remember(quiz) {
        mutableStateOf<Int?>(null)
    }

    val correctAnswerState = remember(quiz) {
        mutableStateOf<Boolean?>(null)
    }

    val updateAnswer: (Int) -> Unit = remember {
        {
            answerState.value = it
            correctAnswerState.value = choicesState[it] == quiz.answer
        }
    }

    if (questionIndex.value >= 3) {
        BuildProgressBar()
    }
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = AppColors.mLightGray, fontSize = 27.sp, fontWeight = FontWeight.ExtraBold)) {
            append("Question ${questionIndex.value}/")
        }
        withStyle(style = SpanStyle(color = AppColors.mLightGray, fontSize = 14.sp, fontWeight = FontWeight.Light)) {
            append(totalQuestions.toString())
        }
    }

    Text(
        annotatedText,
        modifier
            .padding(horizontal = 5.dp)
            .padding(top = 10.dp)
            .padding(bottom = 20.dp)
    )

    DrawDottedDivider(pathEffect)

    Text(
        modifier = modifier
            .padding(10.dp)
            .fillMaxHeight(0.3f),
        text = quiz.question,
        color = AppColors.mOffWhite,
        style = MaterialTheme.typography.bodyMedium,
        lineHeight = 22.sp
    )

    choicesState.forEachIndexed { index, answerText ->
        Row(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxWidth()
                .height(45.dp)
                .border(
                    width = 4.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(AppColors.mOffDarkPurple, AppColors.mOffDarkPurple)
                    ),
                    shape = RoundedCornerShape(15.dp))
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = answerState.value == index,
                onClick = {
                    updateAnswer(index)
                },
                modifier = Modifier.padding(start = 16.dp),
                colors = RadioButtonDefaults
                    .colors(
                        selectedColor =
                        if (correctAnswerState.value == true && index == answerState.value) {
                            Color.Green.copy(0.2f)
                        }  else {
                            Color.Red.copy(0.2f)
                        }
                    )
            )

            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Light,
                        color =
                        if (correctAnswerState.value == true && index == answerState.value) {
                            Color.Green
                        }  else if(correctAnswerState.value == false && index == answerState.value){
                            Color.Red
                        } else {
                            AppColors.mOffWhite
                        },
                        fontSize = 17.sp
                    )
                ) {
                    append(text = answerText)
                }
            }

            Text(annotatedString, modifier = Modifier.padding(6.dp))
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                if (questionIndex.value < totalQuestions - 1) {
                    questionIndex.value += 1 // Move to the next question
                }
            },
            modifier = Modifier.padding(3.dp),
            shape = RoundedCornerShape(34.dp),
            colors = ButtonDefaults.buttonColors(AppColors.mLightBlue)
        ) {
            Text(
                "Next",
                modifier = Modifier.padding(4.dp),
                color = AppColors.mOffWhite,
                fontSize = 17.sp
            )
        }
    }
}


@Preview
@Composable
fun BuildProgressBar(score: Int = 12) {

    val gradient = Brush.linearGradient(listOf(Color(0xFFF95075), Color(0xFFBE6BE5)))
    val progressFactor by remember(score) {
        mutableStateOf(score * 0.005f)
    }

    Row(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(45.dp)
            .border(
                width = 4.dp, brush = Brush.linearGradient(colors = listOf(AppColors.mLightPurple, AppColors.mLightPurple)),
                shape = RoundedCornerShape(34.dp)
            )
            .clip(RoundedCornerShape(
                topStartPercent = 50,
                topEndPercent = 50,
                bottomEndPercent = 50,
                bottomStartPercent = 50))
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            contentPadding = PaddingValues(1.dp),
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(progressFactor)
                .background(
                    brush = gradient
                ),
            enabled = false,
            elevation = null,
            colors = ButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Transparent,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent)
        ) {
            Text(
                text = (score*10).toString(),
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(23.dp))
                    .fillMaxHeight(0.87f)
                    .fillMaxWidth()
                    .padding(6.dp),
                color = AppColors.mOffWhite,
                textAlign = TextAlign.Center
                )
        }
    }
}