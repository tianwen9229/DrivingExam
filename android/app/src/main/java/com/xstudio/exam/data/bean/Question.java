package com.xstudio.exam.data.bean;

import android.annotation.SuppressLint;

public class Question {
    public int mId; // 题目在数据库中的ID
    public int mChapterId; // 章节
    public String mSubChapterId; // 章节

    public int mType; // 题目类型 单选/判断
    public String mTitle; // 题目描述
    public String mOption; // 选项
    public String mImage; // 图片base64
    public String mAnswer; // 正确答案

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        return String.format("id: %d, chapter_id: %d, sub_chapter_id: %s, " +
                        "type: %d, title: %s, option: %s, answer: %s",
                mId, mChapterId, mSubChapterId, mType, mTitle, mOption, mAnswer);
    }
}
