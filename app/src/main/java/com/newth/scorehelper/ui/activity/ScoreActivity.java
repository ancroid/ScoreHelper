package com.newth.scorehelper.ui.activity;

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

import com.newth.scorehelper.R;
import com.newth.scorehelper.util.SpinerListUtil;

import org.angmarch.views.NiceSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private String week;
    private String noteScore;
    private String sampleScore;
    private String questionScor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        inittoolbar();
        initSpinner();
        btuSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void inittoolbar() {
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        toolbarTitle.setText("个人详细评分");
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
                Log.d("score", "onItemSelected: "+position);
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
