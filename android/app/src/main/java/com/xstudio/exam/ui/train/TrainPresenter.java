package com.xstudio.exam.ui.train;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jude.beam.expansion.BeamBasePresenter;
import com.xstudio.exam.data.bean.Question;
import com.xstudio.exam.model.QuestionModel;

public class TrainPresenter extends BeamBasePresenter<TrainActivity> {
    @Override
    protected void onCreate(@NonNull TrainActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
    }

    void init() {
        // 设置标题
        String trainMode = getIdFromIntent();
        getView().setActionBarTitle(trainMode);

        // 获取题目
        Question question = QuestionModel.getInstance().GetQuestionWithId(298);
        getView().setQuestion(question);
    }
}
