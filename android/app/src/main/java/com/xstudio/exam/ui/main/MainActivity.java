package com.xstudio.exam.ui.main;

import android.os.Bundle;
import android.widget.Toast;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.xstudio.exam.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends BeamBaseActivity<MainPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // 加载题库
        getPresenter().LoadingQuestion();
    }

    @OnClick(R.id.btn_train_sequence)
    public void onSequenceTrainClick() {
        getPresenter().navigateTrainSequence();
    }

    @OnClick(R.id.btn_train_random)
    public void onRandomTrainClick() {
        getPresenter().navigateTrainRandom();
    }

    @OnClick(R.id.btn_train_chapter)
    public void onChapterTrainClick() {
        Toast.makeText(this, "未实现", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_train_wrong)
    public void onWrongTrainClick() {
        Toast.makeText(this, "未实现", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_exam)
    public void onExamClick() {
        Toast.makeText(this, "未实现", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_find)
    public void onFindClick() {
        Toast.makeText(this, "未实现", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_register)
    public void onRegisterClick() {
        Toast.makeText(this, "未实现", Toast.LENGTH_SHORT).show();
    }
}
