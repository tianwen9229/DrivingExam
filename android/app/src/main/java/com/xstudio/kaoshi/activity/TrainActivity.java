package com.xstudio.kaoshi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xstudio.kaoshi.R;
import com.xstudio.kaoshi.model.Question;
import com.xstudio.kaoshi.question.QuestionManager;

import java.util.List;

public class TrainActivity extends AppCompatActivity {

    public enum TrainMode {
        Sequence, Random, Chapter, Wrong
    }

    TrainMode mMode;

    private TextView mQuestionTextView;
    private QuestionManager mQMamager = QuestionManager.getInstance(this);
    private List<Question> mQuestions;
    private int mCurrentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        initToolbar();

        initContentView();

        initQuestionRepository();
    }


    private void initToolbar() {
        TextView titleTextView = findViewById(R.id.toolbar_title);
        mMode = (TrainMode) getIntent().getSerializableExtra("mode");
        switch (mMode) {
            case Wrong:
                titleTextView.setText(R.string.btn_train_wrong);
                break;
            case Random:
                titleTextView.setText(R.string.btn_train_random);
                break;
            case Chapter:
                titleTextView.setText(R.string.btn_train_chapter);
                break;
            case Sequence:
                titleTextView.setText(R.string.btn_train_sequence);
                break;
        }

        findViewById(R.id.toolbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initContentView() {
        mQuestionTextView = findViewById(R.id.title);
        findViewById(R.id.previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex > 0) {
                    mCurrentIndex -= 1;
                } else {
                    Toast.makeText(TrainActivity.this, "已经是第一题", Toast.LENGTH_SHORT).show();
                }
                ShowQuestion();
            }
        });

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex < mQuestions.size() - 1) {
                    mCurrentIndex += 1;
                } else {
                    Toast.makeText(TrainActivity.this, "已经是最后一题", Toast.LENGTH_SHORT).show();
                }
                ShowQuestion();
            }
        });
    }

    private void initQuestionRepository() {
        mQuestions = mQMamager.GetQuestions();
        mCurrentIndex = 0;
        ShowQuestion();
    }

    private void ShowQuestion() {
        if (mCurrentIndex < mQuestions.size()) {
            Question question = mQuestions.get(mCurrentIndex);
            mQuestionTextView.setText(question.toString());
        } else {
            mQuestionTextView.setText("空");
        }
    }
}
