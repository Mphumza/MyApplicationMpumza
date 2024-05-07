package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class InterviewActivity extends AppCompatActivity {
    private TextView textViewQuestion;
    private RadioGroup radioGroupAnswers;
    private Button buttonConfirm;
    private Cursor questionsCursor;
    private databaseHelper dbHelper;
    private boolean isLastQuestion = false;
    private long startTime;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);

        textViewQuestion = new TextView(this);
        radioGroupAnswers = new RadioGroup(this);
        buttonConfirm = new Button(this);
        buttonConfirm.setText("Confirm");
        buttonConfirm.setVisibility(View.GONE);

        dbHelper = new databaseHelper(this);
        questionsCursor = dbHelper.getRandomQuestions();

        setupQuestionView();
        displayNextQuestion();

        startTime = SystemClock.elapsedRealtime();
    }

    @SuppressLint("SetTextI18n")
    private void setupQuestionView() {
        LinearLayout layout = findViewById(R.id.layout_questionnaire);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(24, 24, 24, 24);

        textViewQuestion.setLayoutParams(params);
        textViewQuestion.setTextSize(18);
        textViewQuestion.setPadding(16, 16, 16, 16);

        radioGroupAnswers.setLayoutParams(params);
        radioGroupAnswers.setOrientation(RadioGroup.VERTICAL);

        RadioButton radioButtonYes = new RadioButton(this);
        radioButtonYes.setText("Yes");
        RadioButton radioButtonNo = new RadioButton(this);
        radioButtonNo.setText("No");
        radioGroupAnswers.addView(radioButtonYes);
        radioGroupAnswers.addView(radioButtonNo);

        buttonConfirm.setLayoutParams(params);

        layout.addView(textViewQuestion);
        layout.addView(radioGroupAnswers);
        layout.addView(buttonConfirm);

        radioGroupAnswers.setOnCheckedChangeListener((group, checkedId) -> buttonConfirm.setVisibility(View.VISIBLE));

        buttonConfirm.setOnClickListener(v -> {
            if (!isLastQuestion) {
                if (questionsCursor.moveToNext()) {
                    updateQuestionView();
                } else {
                    isLastQuestion = true;
                    buttonConfirm.setText("Submit");
                }
            } else {
                finishQuestions();
            }
        });
    }

    private void updateQuestionView() {
        @SuppressLint("Range") String question = questionsCursor.getString(questionsCursor.getColumnIndex("question"));
        textViewQuestion.setText(question);
        radioGroupAnswers.clearCheck();
        buttonConfirm.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    private void displayNextQuestion() {
        if (questionsCursor.moveToNext()) {
            updateQuestionView();
        } else {
            isLastQuestion = true;
            buttonConfirm.setText("Submit");
        }
    }

    private void finishQuestions() {
        long endTime = SystemClock.elapsedRealtime();
        long duration = (endTime - startTime) / 1000;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Time Taken");
        builder.setMessage("You completed the interview in " + duration + " seconds.");
        builder.setPositiveButton("OK", (dialog, which) -> askToReviewAnswers());
        builder.show();
    }

    private void askToReviewAnswers() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Review Answers");
        builder.setMessage("Do you want to review your answers?");
        builder.setPositiveButton("Yes", (dialog, which) -> showAnswers());
        builder.setNegativeButton("No", (dialog, which) -> navigateToNextPage());
        builder.show();
    }

    private void showAnswers() {
        // This method should gather and show the answers. Here, just a placeholder.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Your Answers");
        builder.setMessage("Placeholder for answers.");
        builder.setPositiveButton("Done", (dialog, which) -> navigateToNextPage());
        builder.show();
    }

    private void navigateToNextPage() {
        // Intent to start a new activity or finish the current one
        Intent intent = new Intent(InterviewActivity.this, FeedbackActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (questionsCursor != null) {
            questionsCursor.close();
        }
        dbHelper.close();
    }
}