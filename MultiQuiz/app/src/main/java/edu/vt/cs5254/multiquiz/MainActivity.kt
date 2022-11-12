package edu.vt.cs5254.multiquiz

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlin.collections.forEach
import edu.vt.cs5254.multiquiz.databinding.ActivityMainBinding
import androidx.activity.viewModels

class MainActivity() : AppCompatActivity() {
    //  Name: Yechen Xu
    //  PID: yechenx

    private lateinit var binding: ActivityMainBinding
    private val vm: QuizViewModel by viewModels()

    lateinit var answerButtonList: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $vm")

        answerButtonList = binding.answerButtons.children.toList().filterIsInstance<Button>()
        binding.questiontextview.setText(R.string.country_question)

        answerButtonList.zip(vm.answerList).forEach {(button, answer) -> button.setText(answer.textResId)}

        binding.hintButton.setText(R.string.hint_button)
        binding.submitButton.setText(R.string.submit_button)
        updateQuestion()

        binding.submitButton.isEnabled = false
        updateView()
    }

    private fun updateQuestion() {
        answerButtonList.zip(vm.answerList).forEach { (button, answer) ->
            button.setOnClickListener {
                AnswerButtonClick(answer)
            }
        }
        binding.hintButton.setOnClickListener {
            HintButtonClick()
        }
        binding.submitButton.setOnClickListener {
            SubmitButtonClick()
        }
    }

    private fun AnswerButtonClick(answer: Answer) {
        val origIsSelected = answer.isSelected
        vm.answerList.forEach { it.isSelected = false }
        answer.isSelected = !origIsSelected
        binding.submitButton.isEnabled = answer.isSelected
        updateView()
    }

    private fun HintButtonClick() {
        vm.giveHint()
        updateView()
    }

    private fun SubmitButtonClick() {
        vm.gotoNextQuestion()
        updateQuestion()
        updateView()
    }

    private fun updateView() {
        binding.hintButton.isEnabled = true
        val a = vm.answerList.filterNot { it.isCorrect }.filter { it.isEnabled }.size
        if (a == 0) {
            binding.hintButton.isEnabled = false
        }
        binding.submitButton.isEnabled = false
        binding.questiontextview.setText(vm.questionText)
        answerButtonList.zip(vm.answerList)
            .forEach { (button, answer) -> button.setText(answer.textResId) }

        answerButtonList.zip(vm.answerList).forEach { (button, answer) ->
            button.isEnabled = answer.isEnabled
            button.isSelected = answer.isSelected
            if (answer.isSelected) {
                binding.submitButton.isEnabled = true
            }
            button.updateColor()
        }
    }
}
