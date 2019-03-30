package com.xstudio.exam.model;

import android.content.Context;
import android.util.SparseArray;

import com.jude.beam.model.AbsModel;
import com.xstudio.exam.data.bean.Question;
import com.xstudio.exam.data.db.QuestionDbHelper;

import java.util.List;

public class QuestionModel extends AbsModel {

    public static QuestionModel getInstance() {
        return getInstance(QuestionModel.class);
    }

    private SparseArray<Question> mQuestionMap;
    private QuestionDbHelper mDBHelper;

    @Override
    protected void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);

        mDBHelper = QuestionDbHelper.getInstance(ctx);
        mQuestionMap = new SparseArray<>();
    }

    @Override
    protected void onAppCreateOnBackThread(Context ctx) {
        super.onAppCreateOnBackThread(ctx);
    }

    public void InitQuestionRepository() {
        List<Question> questions = mDBHelper.QueryAllQuestion();
        for (Question question : questions) {
            mQuestionMap.put(question.mId, question);
        }
    }

    public void ReInitQuestionRepository(final List<Question> questions) {
        for (Question question : questions) {
            mQuestionMap.put(question.mId, question);
        }

        mDBHelper.ReInitQuestionDatabase(questions);
    }

    public List<Question> GetQuestion(int mode, int type) {
        // mode = 0 顺序
        // type = 0 所有类型  type = 1 选择题  type = 2 判断题
        return null;
    }

    public Question GetQuestionWithId(int id) {
        return mQuestionMap.get(id);
    }
}
