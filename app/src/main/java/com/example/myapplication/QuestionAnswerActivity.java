package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuestionAnswerActivity extends AppCompatActivity {
    private TextView textViewQuestion;
    private RadioGroup radioGroupAnswers;
    private Button buttonConfirm;
    private final String[] questions = {"having stomach problems?","having constant coughing?","Are you feeling fatigue?","Do you have a fever?", "Do you feel nausea?", "Are you experiencing headaches?","is your Temperature above 30 degrees?","experiencing chest pains?","having a sore throat?","Are your eyes sore/itching?"}; // Add more questions as needed
    private int currentQuestionIndex = 0;
    private int yesCount = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);

        textViewQuestion = findViewById(R.id.textViewQuestion);
        radioGroupAnswers = findViewById(R.id.radioGroupAnswers);
        buttonConfirm = findViewById(R.id.buttonConfirm);

        setupQuestionView();
        displayNextQuestion();
    }

    @SuppressLint("SetTextI18n")
    private void setupQuestionView() {
        textViewQuestion.setText(questions[currentQuestionIndex]);

        RadioButton radioButtonYes = new RadioButton(this);
        radioButtonYes.setText("Yes");
        RadioButton radioButtonNo = new RadioButton(this);
        radioButtonNo.setText("No");
        radioGroupAnswers.addView(radioButtonYes);
        radioGroupAnswers.addView(radioButtonNo);

        buttonConfirm.setOnClickListener(v -> {
            int checkedRadioButtonId = radioGroupAnswers.getCheckedRadioButtonId();
            if (checkedRadioButtonId == radioButtonYes.getId()) {
                yesCount++;
            }
            else(Toast.makeText())
            displayNextQuestion();
        });
    }

    private void displayNextQuestion() {
        if (currentQuestionIndex < questions.length - 1) {
            currentQuestionIndex++;
            textViewQuestion.setText(questions[currentQuestionIndex]);
            radioGroupAnswers.clearCheck();
        } else {
            finishQuestions();
        }
    }

    private void finishQuestions() {
        if (yesCount >= 7) {
            showDoctorDialog();
        } else {
            navigateToSymptomChecker();
        }
    }

    private void showDoctorDialog() {
        Intent intent = new Intent(QuestionAnswerActivity.this, BookAppointment.class);
        startActivity(intent);
        finish();
    }

    private void navigateToSymptomChecker() {
        Intent intent = new Intent(QuestionAnswerActivity.this, SymptomChecker.class);
        startActivity(intent);
        finish();
    }
}
