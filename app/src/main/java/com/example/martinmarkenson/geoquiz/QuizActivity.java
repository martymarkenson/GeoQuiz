package com.example.martinmarkenson.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.content.Intent;

public class QuizActivity extends AppCompatActivity {
    private Questions[] mQuestionBank = new Questions[]{
            new Questions(R.string.question_ocean, true),
            new Questions(R.string.question_mideast, false),
            new Questions(R.string.question_americas, true),
    };
    public int mCurrentIndex=0;
    private Button mTrueButton, mFalseButton, mCheatButton;
    private ImageButton mNextButton, mPreviousButton;
    private TextView mQuestionTextView;
    public static final String TAG = "QuizActivity";
    public static final String KEY_INDEX = "index";
    public static final String KEY_OCEAN_CHEATER = "ocean_cheater";
    public static final String KEY_MIDEAST_CHEATER = "mideast_cheater";
    public static final String KEY_AMERICAS_CHEATER = "americas_cheater";
    public static final int REQUEST_CODE_CHEAT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate() called");
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);

        if (savedInstanceState!=null) {
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX,0);
            mQuestionBank[0].setCheater(savedInstanceState.getBoolean(KEY_OCEAN_CHEATER,false));
            mQuestionBank[1].setCheater(savedInstanceState.getBoolean(KEY_MIDEAST_CHEATER, false));
            mQuestionBank[2].setCheater(savedInstanceState.getBoolean(KEY_AMERICAS_CHEATER,false));
        }
        updateQuestion();

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Change View
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = cheatActivity.newIntent(QuizActivity.this,answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
             }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex=(mCurrentIndex+1) % mQuestionBank.length;
                updateQuestion();
            };
        });

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex=(mCurrentIndex+mQuestionBank.length-1) % (mQuestionBank.length);
                updateQuestion();
            };
        });
    }

    private final void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean clickedTrue) {

        if (clickedTrue & mQuestionBank[mCurrentIndex].isAnswerTrue() || !clickedTrue & !mQuestionBank[mCurrentIndex].isAnswerTrue()) {

            Toast.makeText(QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            if (mQuestionBank[mCurrentIndex].isCheaterTrue()) {
                Toast.makeText(QuizActivity.this, R.string.judgment_toast, Toast.LENGTH_LONG).show();
            }
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            updateQuestion();

        } else if ((!clickedTrue & mQuestionBank[mCurrentIndex].isAnswerTrue() || clickedTrue & !mQuestionBank[mCurrentIndex].isAnswerTrue()) & mQuestionBank[mCurrentIndex].isCheaterTrue()) {
            Toast.makeText(QuizActivity.this, R.string.incorrect_cheater_toast,Toast.LENGTH_LONG).show();


        } else {
            Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState() called");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_OCEAN_CHEATER, mQuestionBank[0].isCheaterTrue());
        savedInstanceState.putBoolean(KEY_MIDEAST_CHEATER, mQuestionBank[1].isCheaterTrue());
        savedInstanceState.putBoolean(KEY_AMERICAS_CHEATER, mQuestionBank[2].isCheaterTrue());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data==null) {
                return;
            }
            mQuestionBank[mCurrentIndex].setCheater(true);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG,"onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy() called");
    }
}


