package com.xstudio.exam.utils;

import android.util.Pair;

import com.xstudio.exam.data.bean.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionUtils {
    private static final String JSON_VERSION = "version";
    private static final String JSON_QUESTION = "question";
    private static final String JSON_ID = "id";
    private static final String JSON_CHAPTER_ID = "chapter_id";
    private static final String JSON_SUB_CHAPTER_ID = "sub_chapter_id";
    private static final String JSON_TYPE = "type";
    private static final String JSON_TITLE = "title";
    private static final String JSON_OPTION = "option";
    private static final String JSON_IMAGE_SRC = "image_src";
    private static final String JSON_ANSWER = "answer";

    public static Pair<Integer, List<Question>> ParseQuestionJson(String jsonRepository) throws JSONException {
        JSONObject rootObject = new JSONObject(jsonRepository);
        int version = rootObject.getInt(JSON_VERSION);

        List<Question> questions = new ArrayList<>();

        JSONArray questionObjects = rootObject.getJSONArray(JSON_QUESTION);
        for (int i = 0; i < questionObjects.length(); i++) {
            JSONObject object = questionObjects.getJSONObject(i);

            Question question = new Question();
            question.mId = object.getInt(JSON_ID);
            question.mChapterId = object.getInt(JSON_CHAPTER_ID);
            question.mSubChapterId = object.getString(JSON_SUB_CHAPTER_ID);
            question.mType = object.getInt(JSON_TYPE);
            question.mTitle = object.getString(JSON_TITLE);
            question.mOption = object.getString(JSON_OPTION);
            question.mImage = object.getString(JSON_IMAGE_SRC);
            question.mAnswer = object.getString(JSON_ANSWER);

            questions.add(question);
        }

        return new Pair<>(version, questions);
    }

    public final static int YesOrNo = 1;
    public final static int Choice = 2;

    public static String GetQuestionType(Question question) {
        switch (question.mType) {
            case YesOrNo:
                return "判断题";
            case Choice:
                return "单选题";
        }

        return "未知题型";
    }
}
