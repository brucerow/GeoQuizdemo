package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    

    Button mTrueButton;
    Button mFalseButton;
    Button mNextButton;
    Button mCheatButton;
    Button mPervButton;

    boolean mIsCheater;

    TextView mQuestionTextView;
    
    TrueFalse[] mAnswerKey = new TrueFalse[] {
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true)
    };
    
    boolean mKnowAnswer [] = new boolean[mAnswerKey.length];

    int mCurrentIndex = 0;

    private void updateQuestion() {
        int question = mAnswerKey[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }
    
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mAnswerKey[mCurrentIndex].isTrueQuestion();

        int messageResId = 0;
        mIsCheater =mKnowAnswer[mCurrentIndex];
        if (mIsCheater) {
        	//如果答案正确就警告，如果答案错误就表扬
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.judgment_toast;
            } else {
                messageResId = R.string.incorrect_judgement_toast;
            }
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");
        setContentView(R.layout.activity_quiz);

        mIsCheater = false;

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 点击文字获取下一题
				mCurrentIndex = (mCurrentIndex + 1) % mAnswerKey.length;
                mIsCheater = false;
                updateQuestion();
			}
		});

        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button)findViewById(R.id.false_button);		
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
        
        //上一题
        mPervButton = (Button) findViewById(R.id.prev_button);
        mPervButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mCurrentIndex>0){
					mCurrentIndex = mCurrentIndex-1 ;
	                mIsCheater = false;
	                updateQuestion();
				}else{
					Toast.makeText(QuizActivity.this,"已经到头来",Toast.LENGTH_SHORT).show();
				}
				
			}
		});
        
        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mAnswerKey.length;
                mIsCheater = false;
                updateQuestion();
            }
        });		

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "cheat button clicked");
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                Log.d(TAG, "intent created");
                boolean answerIsTrue = mAnswerKey[mCurrentIndex].isTrueQuestion();
                i.putExtra("ANSWER_IS_TRUE", answerIsTrue);
                startActivityForResult(i, 0);
            }
        });

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean("mIsCheater");
        }

        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mIsCheater = data.getBooleanExtra("ANSWER_SHOWN", false);
        //把对应题号存下来
        mKnowAnswer [mCurrentIndex] =mIsCheater;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean("mIsCheater", mIsCheater);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_quiz, menu);
        return true;
    }

}
