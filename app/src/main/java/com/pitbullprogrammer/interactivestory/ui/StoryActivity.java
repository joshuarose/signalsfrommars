package com.pitbullprogrammer.interactivestory.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pitbullprogrammer.interactivestory.R;
import com.pitbullprogrammer.interactivestory.model.Page;
import com.pitbullprogrammer.interactivestory.model.Story;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class StoryActivity extends ActionBarActivity {
    public static final String TAG = StoryActivity.class.getSimpleName();

    private Story mStory = new Story();
    @InjectView(R.id.storyImageView) ImageView mImageView;
    @InjectView(R.id.storyTextView) TextView mTextView;
    @InjectView(R.id.choiceButton1) Button mChoice1;
    @InjectView(R.id.choiceButton2) Button mChoice2;
    Page mCurrentPage;
    private String mName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        mName = intent.getStringExtra(getString(R.string.key_name));
        if (mName == null){
            mName = "Friend";
        }
        Log.d(TAG, mName);
        loadPage(0);
    }

    private void loadPage(int choice){
        mCurrentPage = mStory.getPage(choice);
        Drawable drawable = getResources().getDrawable(mCurrentPage.getImageId());
        mImageView.setImageDrawable(drawable);

        //Add the name if placeholder included. Won't add if no placeholder
        String pageText = mCurrentPage.getText();
        pageText = String.format(pageText, mName);

        mTextView.setText(pageText);

        if (mCurrentPage.isFinal()) {
            mChoice1.setVisibility(View.INVISIBLE);
            mChoice2.setText("PLAY AGAIN");
            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        else {
            mChoice1.setText(mCurrentPage.getChoice1().getText());
            mChoice2.setText(mCurrentPage.getChoice2().getText());

            mChoice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nextPage = mCurrentPage.getChoice1().getNextPage();
                    loadPage(nextPage);
                }
            });

            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nextPage = mCurrentPage.getChoice2().getNextPage();
                    loadPage(nextPage);
                }
            });
        }
    }
}






