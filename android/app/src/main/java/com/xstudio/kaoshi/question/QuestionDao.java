package com.xstudio.kaoshi.question;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.xstudio.kaoshi.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionDao extends SQLiteOpenHelper {

    //数据库名称
    private static final String DATABASE_NAME = "kaoshi.db";

    //数据库版本号
    private static int DATABASE_VERSION = 1;

    private static QuestionDao helper;

    //表名。
    private static final String TABLE_QUESTION = "question";

    private static final String FIELD_QUESTION_ID = "id";
    private static final String FIELD_QUESTION_CHAPTER_ID = "chapter_id";
    private static final String FIELD_QUESTION_SUB_CHAPTER_ID = "sub_chapter_id";
    private static final String FIELD_QUESTION_TYPE = "type";
    private static final String FIELD_QUESTION_TITLE = "title";
    private static final String FIELD_QUESTION_IMAGE_SRC = "image_src";
    private static final String FIELD_QUESTION_OPTION = "option";
    private static final String FIELD_QUESTION_ANSWER = "answer";

    //创建数据库表的SQL语句
    private String SQL_CREATE_QUESTION = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTION + " ( "
            + FIELD_QUESTION_ID + " integer primary key,"
            + FIELD_QUESTION_CHAPTER_ID + " integer,"
            + FIELD_QUESTION_SUB_CHAPTER_ID + " text,"
            + FIELD_QUESTION_TYPE + " integer,"
            + FIELD_QUESTION_TITLE + " text,"
            + FIELD_QUESTION_OPTION + " text,"
            + FIELD_QUESTION_IMAGE_SRC + " text,"
            + FIELD_QUESTION_ANSWER + " text"
            + " ) ";

    public static QuestionDao getInstance(Context context) {
        if (helper == null) {
            helper = new QuestionDao(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        return helper;
    }

    private QuestionDao(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库的表，如果不存在
        db.execSQL(SQL_CREATE_QUESTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //查询SQLite数据库。读出所有数据内容。
    public List<Question> QueryAllQuestion() {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                FIELD_QUESTION_ID,
                FIELD_QUESTION_CHAPTER_ID,
                FIELD_QUESTION_SUB_CHAPTER_ID,
                FIELD_QUESTION_TYPE,
                FIELD_QUESTION_TITLE,
                FIELD_QUESTION_OPTION,
                FIELD_QUESTION_IMAGE_SRC,
                FIELD_QUESTION_ANSWER,
        };

        Cursor cursor = db.query(
                TABLE_QUESTION,    // The table to query
                projection,        // The array of columns to return (pass null to get all)
                null,         // The columns for the WHERE clause
                null,     // The values for the WHERE clause
                null,     // don't group the rows
                null,      // don't filter by row groups
                null      // The sort order
        );


        ArrayList<Question> questions = new ArrayList<>();

        while (cursor.moveToNext()) {
            Question question = new Question();

            question.mId = cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_QUESTION_ID));
            question.mChapterId = cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_QUESTION_CHAPTER_ID));
            question.mSubChapterId = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_QUESTION_SUB_CHAPTER_ID));
            question.mType = cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_QUESTION_TYPE));
            question.mTitle = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_QUESTION_TITLE));
            question.mOption = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_QUESTION_OPTION));
            question.mImage = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_QUESTION_IMAGE_SRC));
            question.mAnswer = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_QUESTION_ANSWER));

            questions.add(question);
        }

        cursor.close();
        db.close();

        return questions;
    }

    public long ReplaceQuestion(Question question) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(FIELD_QUESTION_ID, question.mId);
        cv.put(FIELD_QUESTION_CHAPTER_ID, question.mChapterId);
        cv.put(FIELD_QUESTION_SUB_CHAPTER_ID, question.mSubChapterId);
        cv.put(FIELD_QUESTION_TYPE, question.mType);
        cv.put(FIELD_QUESTION_TITLE, question.mTitle);
        cv.put(FIELD_QUESTION_OPTION, question.mOption);
        cv.put(FIELD_QUESTION_IMAGE_SRC, question.mImage);
        cv.put(FIELD_QUESTION_ANSWER, question.mAnswer);

        long res = db.replace(TABLE_QUESTION, null, cv);

        db.close();

        return res;
    }
}