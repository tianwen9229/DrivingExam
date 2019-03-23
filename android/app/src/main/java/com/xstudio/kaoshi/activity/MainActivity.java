package com.xstudio.kaoshi.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.xstudio.kaoshi.R;
import com.xstudio.kaoshi.model.Question;
import com.xstudio.kaoshi.question.QuestionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static class MainHandler extends Handler {
        WeakReference<MainActivity> mActivity = null;

        MainHandler(MainActivity mActivity) {
            this.mActivity = new WeakReference<MainActivity>(mActivity);
        }

        @Override
        public void handleMessage(Message msg) {

        }
    }

    private static class CheckQuestionUpdateTask extends AsyncTask<Void, Integer, Boolean> {

        WeakReference<MainActivity> mActivity = null;

        CheckQuestionUpdateTask(MainActivity mActivity) {
            this.mActivity = new WeakReference<MainActivity>(mActivity);
        }

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

        private Pair<Integer, List<Question>> ParseQuestionJson(String jsonRepository) throws JSONException {
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

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                ArrayList<Question> questions = new ArrayList<>();
                InputStream is = mActivity.get().getResources().openRawResource(R.raw.question);
                InputStreamReader reader = new InputStreamReader(is);

                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder buffer = new StringBuilder("");
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    buffer.append(str);
                    buffer.append("\n");
                }

                Pair<Integer, List<Question>> pair = ParseQuestionJson(buffer.toString());

                // TODO 存储当前题库版本弹出对话框让用户确认是否更新题库
                // 如果版本号比现在的版本号更高那么更新数据库
                mActivity.get().mQMamager.UpdateQuestionRepository(pair.second);

            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Boolean result) {
        }
    }

    private Handler mHandler = new MainHandler(this);
    private QuestionManager mQMamager = QuestionManager.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CheckQuestionUpdateTask(this).execute();

        initToolbar();

        initContentView();

        // TODO 初始化题库，需要异步加载
        QuestionManager.getInstance(this).InitQuestionRepository();
    }

    private void initToolbar() {
        findViewById(R.id.toolbar_back).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.toolbar_title)).setText(R.string.app_name);
    }

    private void initContentView() {
        findViewById(R.id.btn_train_sequence).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToTrain(TrainActivity.TrainMode.Sequence);
            }
        });

        findViewById(R.id.btn_train_random).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToTrain(TrainActivity.TrainMode.Random);
            }
        });

        findViewById(R.id.btn_train_chapter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        findViewById(R.id.btn_train_wrong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        findViewById(R.id.btn_exam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToExam();
            }
        });

        findViewById(R.id.btn_find).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFind();
            }
        });

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToRegister();
            }
        });
    }

    private void navigateToTrain(TrainActivity.TrainMode mode) {
        Intent intent = new Intent(MainActivity.this, TrainActivity.class);
        intent.putExtra("mode", mode);
        startActivity(intent);
    }

    public void navigateToExam() {
        Intent intent = new Intent(MainActivity.this, ExamSettingActivity.class);
        startActivity(intent);
    }

    public void navigateToFind() {
        Intent intent = new Intent(MainActivity.this, FindActivity.class);
        startActivity(intent);
    }

    public void navigateToRegister() {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
