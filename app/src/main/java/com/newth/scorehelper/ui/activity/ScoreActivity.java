package com.newth.scorehelper.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.newth.scorehelper.R;
import com.newth.scorehelper.bean.ScoreBeanDB;
import com.newth.scorehelper.util.SpinerListUtil;

import org.angmarch.views.NiceSpinner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class ScoreActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.spin_week)
    NiceSpinner spinWeek;
    @BindView(R.id.spin_note)
    NiceSpinner spinNote;
    @BindView(R.id.spin_sample)
    NiceSpinner spinSample;
    @BindView(R.id.spin_question)
    NiceSpinner spinQuestion;
    @BindView(R.id.btu_submit)
    Button btuSubmit;

    private String week="第1周";
    private String noteScore="1";
    private String sampleScore="1";
    private String questionScore="1";
    private Long stuId;
    private String studName="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        stuId=intent.getLongExtra("id",0);
        studName=intent.getStringExtra("name");
        initView();
    }
    private void initView(){
        inittoolbar();
        initSpinner();
        btuSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitScore();
            }
        });
    }

    private void inittoolbar() {
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        toolbarTitle.setText(studName+"同学详细评分");
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initSpinner(){
        spinWeek.attachDataSource(SpinerListUtil.getWeekList());
        spinNote.attachDataSource(SpinerListUtil.getScoreList());
        spinSample.attachDataSource(SpinerListUtil.getScoreList());
        spinQuestion.attachDataSource(SpinerListUtil.getScoreList());

        spinWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("week", "onItemSelected: "+position);
                week=SpinerListUtil.getWeekList().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinNote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("noteScore", "onItemSelected: "+position);
                noteScore=SpinerListUtil.getScoreList().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinSample.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("sampleScore", "onItemSelected: "+position);
                sampleScore=SpinerListUtil.getScoreList().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinQuestion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("questionScore", "onItemSelected: "+position);
                questionScore=SpinerListUtil.getScoreList().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void submitScore(){
        if (stuId!=0){
            checkScoreIsExit();
        }
    }
    private void checkScoreIsExit(){
        BmobQuery<ScoreBeanDB> query=new BmobQuery<>();
        query.addWhereEqualTo("week",week);
        query.addWhereEqualTo("userStuID",stuId);
        query.findObjects(new FindListener<ScoreBeanDB>() {
            @Override
            public void done(List<ScoreBeanDB> list, BmobException e) {
                if (e==null){
                    update(list.get(0).getObjectId());
                }else {
                    submit();
                }
            }
        });
    }
    private void update(String objectid){
        ScoreBeanDB scoreBean=new ScoreBeanDB();
        scoreBean.setWeek(week);
        scoreBean.setAnswerScore(questionScore);
        scoreBean.setCodeScore(sampleScore);
        scoreBean.setNoteScore(noteScore);
        scoreBean.update(objectid, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(ScoreActivity.this,"更新成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ScoreActivity.this,"更新失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void submit(){
        ScoreBeanDB scoreBean=new ScoreBeanDB();
        scoreBean.setUserStuID(stuId);
        scoreBean.setUserName(studName);
        scoreBean.setWeek(week);
        scoreBean.setAnswerScore(questionScore);
        scoreBean.setCodeScore(sampleScore);
        scoreBean.setNoteScore(noteScore);
        scoreBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(ScoreActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ScoreActivity.this,"上传失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
