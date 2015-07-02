package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

    private static final String TAG = "CheatActivity";
    public static final String EXTRA_ANSWER_IS_TRUE = "tfquiz.ANSWER_IS_TRUE";
    //public static final String EXTRA_ANSWER_SHOWN = "tfquiz.ANSWER_SHOWN";
    
    boolean mAnswerIsTrue;
    boolean mIsClick = false;
    int num = 0;
    
    TextView mAnswerTextView;
    Button mShowAnswer;
    TextView mVersionTextView;
    String version = "版本是："+ Build.VERSION.RELEASE+"\nsdk是："+Build.VERSION.SDK_INT;
    boolean mIsShow;


    private void setAnswerShownResult(boolean isAnswerShown) {
    	mIsClick = isAnswerShown;
    	Intent data = new Intent();
        data.putExtra("ANSWER_SHOWN", isAnswerShown);
        setResult(RESULT_OK, data);
        //mVersionTextView.setText(version);
    }
    
    
    @Override
    //活动销毁前保存按钮状态
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean("mIsClick", mIsClick);
		//outState.putBoolean("mAnswerIsTrue", mAnswerIsTrue);
		outState.putInt("num", num);
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");
        
        setContentView(R.layout.activity_cheat);
        mVersionTextView = (TextView) findViewById(R.id.version_textview);
        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);
        mAnswerIsTrue = getIntent().getBooleanExtra("ANSWER_IS_TRUE", false);
        if (savedInstanceState == null) {
            // first startup, so the answer must not
            // be shown yet
            setAnswerShownResult(false);
            mVersionTextView.setText(version);
            num = 1;
            Log.d("num", num+"");
           
        }else {/*if((mIsClick=savedInstanceState.getBoolean("mIsClick"))){
        	  if (mAnswerIsTrue=savedInstanceState.getBoolean("mAnswerIsTrue")) {
                  mAnswerTextView.setText(R.string.true_button);
              } else {
                  mAnswerTextView.setText(R.string.false_button);
              }*/
        	mIsClick = savedInstanceState.getBoolean("mIsClick");
        	//mAnswerIsTrue = savedInstanceState.getBoolean("mAnswerIsTrues");
        	num = savedInstanceState.getInt("num");
        	Log.d("num","活动重启后的num值："+num);
        	if (mAnswerIsTrue) {
                mAnswerTextView.setText(R.string.true_button);
            } else {
                mAnswerTextView.setText(R.string.false_button);
            }  
        	setAnswerShownResult(true);
        	mVersionTextView.setText(version);  
          }
        
        
        mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });
    }

}
