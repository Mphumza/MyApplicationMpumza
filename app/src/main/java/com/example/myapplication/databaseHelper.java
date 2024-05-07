package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "healthDiagnosis.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_QUESTIONS = "Questions";
    private static final String TABLE_ANSWERS = "Answers";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_ANSWER = "answer";
    private static final String COLUMN_QUESTION_ID = "questionId";

    private static final String CREATE_TABLE_QUESTIONS = "CREATE TABLE " + TABLE_QUESTIONS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_QUESTION + " TEXT," +
            COLUMN_CATEGORY + " TEXT)";

    private static final String CREATE_TABLE_ANSWERS = "CREATE TABLE " + TABLE_ANSWERS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_QUESTION_ID + " INTEGER," +
            COLUMN_ANSWER + " TEXT," +
            "FOREIGN KEY(" + COLUMN_QUESTION_ID + ") REFERENCES " + TABLE_QUESTIONS + "(" + COLUMN_ID + "))";

    public databaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUESTIONS);
        db.execSQL(CREATE_TABLE_ANSWERS);
        insertInitialQuestions(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWERS);
        onCreate(db);
    }

    public void insertAnswer(int questionId, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION_ID, questionId);
        values.put(COLUMN_ANSWER, answer);
        db.insert(TABLE_ANSWERS, null, values);
        db.close();
    }

    public Cursor getAllAnswers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_ANSWERS, new String[]{COLUMN_QUESTION_ID, COLUMN_ANSWER}, null, null, null, null, null);
    }

    private void insertInitialQuestions(SQLiteDatabase db) {
        // Questions for Headache
        insertQuestion(db, "Do you have pain in one side of your head?", "Headache");
        insertQuestion(db, "Is your headache accompanied by nausea?", "Headache");
        insertQuestion(db, "Does your headache worsen with bright light?", "Headache");
        insertQuestion(db, "Are your headaches recurring?", "Headache");
        insertQuestion(db, "Do you experience blurred vision with your headache?", "Headache");

        // Questions for Stomachache
        insertQuestion(db, "Is the pain in your upper stomach?", "Stomachache");
        insertQuestion(db, "Have you eaten anything unusual recently?", "Stomachache");
        insertQuestion(db, "Does the pain get better after eating?", "Stomachache");
        insertQuestion(db, "Is the stomach pain sharp and sudden?", "Stomachache");
        insertQuestion(db, "Do you experience bloating?", "Stomachache");

        // Questions for Red Eye
        insertQuestion(db, "Is only one of your eyes red?", "Red Eye");
        insertQuestion(db, "Do you feel like there is something in your eye?", "Red Eye");
        insertQuestion(db, "Is there discharge coming from your eye?", "Red Eye");
        insertQuestion(db, "Do you have a recent history of eye injury?", "Red Eye");
        insertQuestion(db, "Are you experiencing any vision loss?", "Red Eye");

        // Questions for Fever
        insertQuestion(db, "Have you measured your temperature?", "Fever");
        insertQuestion(db, "Do you feel chills?", "Fever");
        insertQuestion(db, "Is your fever above 38°C (100.4°F)?", "Fever");
        insertQuestion(db, "Do you have accompanying symptoms like a rash?", "Fever");
        insertQuestion(db, "Have you been in contact with sick individuals?", "Fever");

        // Questions for COVID-19
        insertQuestion(db, "Are you experiencing difficulty breathing?", "COVID-19");
        insertQuestion(db, "Have you lost your sense of taste or smell?", "COVID-19");
        insertQuestion(db, "Do you have a persistent cough?", "COVID-19");
        insertQuestion(db, "Are you feeling unusually tired?", "COVID-19");
        insertQuestion(db, "Have you been in a crowd recently?", "COVID-19");

        // Questions for Common Cold
        insertQuestion(db, "Do you have a runny or blocked nose?", "Common Cold");
        insertQuestion(db, "Are you sneezing frequently?", "Common Cold");
        insertQuestion(db, "Do you have a sore throat?", "Common Cold");
        insertQuestion(db, "Are you experiencing mild body aches?", "Common Cold");
        insertQuestion(db, "Do you have a mild headache?", "Common Cold");

        // Questions for Flu
        insertQuestion(db, "Are you experiencing body aches?", "Flu");
        insertQuestion(db, "Do you have a sore throat?", "Flu");
        insertQuestion(db, "Is your fever above 38.5°C (101.3°F)?", "Flu");
        insertQuestion(db, "Are you feeling extreme fatigue?", "Flu");
        insertQuestion(db, "Have you noticed sudden onset of symptoms?", "Flu");
    }

    private void insertQuestion(SQLiteDatabase db, String question, String category) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION, question);
        values.put(COLUMN_CATEGORY, category);
        db.insert(TABLE_QUESTIONS, null, values);
    }

    public Cursor getRandomQuestions() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_QUESTIONS + " ORDER BY RANDOM() LIMIT 10", null);
    }
}

