package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class SymptomChecker extends AppCompatActivity {
    EditText mSymptomEdit;
    Button CheckBUtton;
    TextView ResultsTextViewing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_symptom_checker);


        //initialize UI Elements
        mSymptomEdit = findViewById(R.id.symptomEditText);
        CheckBUtton = findViewById(R.id.checkButton);
        ResultsTextViewing = findViewById(R.id.resultsTextView);

        //set onClickListener for CheckButton
        CheckBUtton.setOnClickListener(v -> checkSymptoms());
    }

    public void checkSymptoms() {
        String userInput = mSymptomEdit.getText().toString().toLowerCase(); // Get user input and convert to lower case for comparison
        boolean hasHeadache = userInput.contains("headache");
        boolean hasFever = userInput.contains("fever");
        boolean hasSinus = userInput.contains("sinus");
        boolean hasCovid = userInput.contains("covid");
        boolean hasPinkEye = userInput.contains("pink eye");
        // You can add more symptoms checks here

        // Check symptoms against known conditions
        StringBuilder conditions = new StringBuilder();

        if (hasHeadache && hasFever) {
            conditions.append("Flu, Common Cold; ");
        }
        if (hasHeadache) {
            conditions.append("Migraine, Tension Headache; ");
        }
        if (hasFever) {
            conditions.append("Fever - may require further diagnosis; ");
        }
        if (hasSinus) {
            conditions.append("Sinusitis; ");
        }
        if (hasCovid) {
            conditions.append("Possible COVID-19 infection - please seek medical advice for proper testing and diagnosis; ");
        }
        if (hasPinkEye) {
            conditions.append("Conjunctivitis (Pink Eye); ");
        }
        if (conditions.length() == 0) {
            conditions.append("No specific condition detected based on the entered symptoms.");
        }

        displayPossibleConditions(conditions.toString());
    }

// No longer need separate methods for getting user input for headache and fever

    //Example: Display possible conditions
    @SuppressLint("SetTextI18n")
    private void displayPossibleConditions(String conditions) {
        ResultsTextViewing.setText("Possible conditions:" + conditions);
    }
}

