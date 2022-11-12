package edu.vt.cs5254.multiquiz

import androidx.lifecycle.ViewModel
const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel() {

    private val questionBank = listOf(
        Question(
            R.string.country_question,
            listOf(
                Answer(R.string.country_answer_China, true),
                Answer(R.string.country_answer_Russia, false),
                Answer(R.string.country_answer_American, false),
                Answer(R.string.country_answer_Canada, false)
            )
        ),
        Question(
            R.string.capital_question,
            listOf(
                Answer(R.string.capital_answer_Madrid, false),
                Answer(R.string.capital_answer_Lisbon, true),
                Answer(R.string.capital_answer_Barcelona, false),
                Answer(R.string.capital_answer_Paris, false)
            )
        ),
        Question(
            R.string.math_question,
            listOf(
                Answer(R.string.math_answer_2752, false),
                Answer(R.string.math_answer_3512, false),
                Answer(R.string.math_answer_1632, true),
                Answer(R.string.math_answer_1756, false)
            )
        ),
        Question(
            R.string.planet_question,
            listOf(
                Answer(R.string.planet_answer_Mars, false),
                Answer(R.string.planet_answer_Jupiter, false),
                Answer(R.string.planet_answer_Earth, false),
                Answer(R.string.planet_answer_Saturn, true)
            )
        )
    )

    var questionIndex = 0

    val questionText get() = questionBank[questionIndex].questionResId
    val answerList get() = questionBank[questionIndex].answerList

    fun gotoNextQuestion() {
        questionIndex = (questionIndex + 1) % questionBank.size
    }

    fun giveHint() {
        val answerToHide = answerList .filterNot { it.isCorrect } .filter { it.isEnabled }.random()
        answerToHide.isEnabled = false
        answerToHide.isSelected = false
    }
}