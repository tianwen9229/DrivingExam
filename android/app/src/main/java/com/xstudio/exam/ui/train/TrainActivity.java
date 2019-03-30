package com.xstudio.exam.ui.train;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.xstudio.exam.R;
import com.xstudio.exam.data.bean.Question;
import com.xstudio.exam.utils.QuestionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@RequiresPresenter(TrainPresenter.class)
public class TrainActivity extends BeamBaseActivity<TrainPresenter> {
    @BindView(R.id.bar_title)
    TextView mTitle;

    @BindView(R.id.tv_question_type)
    TextView mQuestionType;

    @BindView(R.id.tv_question_indicator)
    TextView mQuestionIndicator;

    @BindView(R.id.tv_question_title)
    TextView mQuestionTitle;

    @BindView(R.id.iv_question_image)
    ImageView mQuestionImage;

    @BindView(R.id.rg_question_options)
    RadioGroup mQuestionRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        ButterKnife.bind(this);

        getPresenter().init();
    }

    @OnClick(R.id.bar_back)
    void OnClickBarBack() {
        finish();
    }

    public void setActionBarTitle(String mode) {
        mTitle.setText(mode);
    }

    public void setQuestion(Question question) {
        mQuestionTitle.setText(question.mTitle);
        if (question.mImage.length() == 0) {
            mQuestionImage.setVisibility(View.GONE);
        } else {
            byte[] decodedString = Base64.decode(question.mImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            if (decodedByte != null) {
                mQuestionImage.setVisibility(View.VISIBLE);
                mQuestionImage.setImageBitmap(decodedByte);
            }
        }
        mQuestionType.setText(String.format("[%s]", QuestionUtils.GetQuestionType(question)));

        // 动态添加RadioButton
        String[] options = question.mOption.split("\\|");
        for (String option : options) {
            RadioButton radioButton = new RadioButton(this);
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(15, 0, 0, 0);
            radioButton.setButtonDrawable(android.R.drawable.btn_radio);
            radioButton.setPadding(15, 0, 0, 0);
            radioButton.setText(option);
            mQuestionRadioGroup.addView(radioButton, lp);
        }
    }


}
