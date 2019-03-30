package com.xstudio.exam.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.widget.Toast;

import com.jude.beam.expansion.BeamBasePresenter;
import com.xstudio.exam.R;
import com.xstudio.exam.data.bean.Question;
import com.xstudio.exam.model.PreferencesModel;
import com.xstudio.exam.model.QuestionModel;
import com.xstudio.exam.ui.train.TrainActivity;
import com.xstudio.exam.utils.QuestionUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.List;

public class MainPresenter extends BeamBasePresenter<MainActivity> {

    private final static int MSG_LOAD_QUESTION_DONE = 100;

    private boolean isReady = false;

    private static class MainHandler extends Handler {
        WeakReference<MainPresenter> mPresenter = null;

        MainHandler(MainPresenter mPresenter) {
            this.mPresenter = new WeakReference<MainPresenter>(mPresenter);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_QUESTION_DONE:
                    mPresenter.get().isReady = true;
                    break;
                default:
                    break;
            }
        }
    }

    private MainHandler mHandler;


    @Override
    protected void onCreate(@NonNull MainActivity view, Bundle savedState) {
        super.onCreate(view, savedState);

        mHandler = new MainHandler(this);
    }

    void navigateTrainSequence() {
        if (isReady) {
            startActivityWithData(getView().getString(R.string.btn_train_sequence),
                    TrainActivity.class);
        } else {
            Toast.makeText(getView(), "正在加载题库...", Toast.LENGTH_SHORT).show();
        }
    }

    void navigateTrainRandom() {
        if (isReady) {
            startActivityWithData(getView().getString(R.string.btn_train_random),
                    TrainActivity.class);
        } else {
            Toast.makeText(getView(), "正在加载题库...", Toast.LENGTH_SHORT).show();
        }
    }


    // 检查
    void LoadingQuestion() {
        new Thread() {
            @Override
            public void run() {
                try {
                    int version = PreferencesModel.getInstance().getQuestionVersion();

                    // 加载最新的版本号
                    // TODO 从服务器中加载
                    InputStream is = getView().getResources().openRawResource(R.raw.question);
                    InputStreamReader reader = new InputStreamReader(is);

                    BufferedReader bufferedReader = new BufferedReader(reader);
                    StringBuilder buffer = new StringBuilder("");
                    String str;
                    while ((str = bufferedReader.readLine()) != null) {
                        buffer.append(str);
                        buffer.append("\n");
                    }

                    Pair<Integer, List<Question>> pair = QuestionUtils.ParseQuestionJson(buffer.toString());

                    int newVersion = pair.first;

                    if (newVersion > version) {
                        QuestionModel.getInstance().ReInitQuestionRepository(pair.second);
                        PreferencesModel.getInstance().setQuestionVersion(newVersion);
                    } else {
                        QuestionModel.getInstance().InitQuestionRepository();
                    }

                    mHandler.sendEmptyMessage(MSG_LOAD_QUESTION_DONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

