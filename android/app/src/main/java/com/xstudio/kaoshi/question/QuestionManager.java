package com.xstudio.kaoshi.question;

import android.content.Context;

import com.xstudio.kaoshi.model.Question;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QuestionManager {
    // 单例
    private static QuestionManager instance;


    public static synchronized QuestionManager getInstance(Context context) {
        if (instance == null) {
            instance = new QuestionManager();
        }

        instance.mContext = context;
        return instance;
    }


    private Map<Integer, Question> mQuestionMap;
    // TODO 处理题库版本
    private int mQuestionVersion;
    private Context mContext;

    private QuestionManager() {
        mQuestionMap = new ConcurrentHashMap<>();
    }

    public List<Question> GetQuestions() {
        List<Question> questions = new ArrayList<>();

        for (Map.Entry<Integer, Question> entry : mQuestionMap.entrySet()) {
            questions.add(entry.getValue());
        }

        return questions;
    }

    public void InitQuestionRepository() {
        QuestionDao dao = QuestionDao.getInstance(mContext);
        List<Question> questions = dao.QueryAllQuestion();
        for (Question question : questions) {
            mQuestionMap.put(question.mId, question);
        }
    }

    public void UpdateQuestionRepository(List<Question> questions) throws JSONException {
        QuestionDao dao = QuestionDao.getInstance(mContext);

        for (Question question : questions) {
            dao.ReplaceQuestion(question);
        }

        InitQuestionRepository();
    }
}
