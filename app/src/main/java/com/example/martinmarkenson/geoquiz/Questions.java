package com.example.martinmarkenson.geoquiz;

/**
 * Created by martinmarkenson on 11/17/15.
 */
public class Questions {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mCheated;

    public Questions(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mCheated = false;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }


    public void setCheater(boolean cheater) {
        this.mCheated = cheater;
    }

    public boolean isCheaterTrue() {
        return mCheated;
    }
}