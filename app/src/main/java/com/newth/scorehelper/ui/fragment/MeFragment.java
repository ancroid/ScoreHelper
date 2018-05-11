package com.newth.scorehelper.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.newth.scorehelper.R;
import com.newth.scorehelper.adapter.MyScoreRecyclerAdapter;
import com.newth.scorehelper.bean.Inner.User;
import com.newth.scorehelper.bean.ScoreBeanDB;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by Mr.chen on 2018/3/18.
 */

public class MeFragment extends Fragment {
    @BindView(R.id.text_score_all)
    TextView textScoreAll;
    @BindView(R.id.recycler_score)
    RecyclerView recyclerScore;
    @BindView(R.id.text_no_score_info)
    TextView textNoScoreInfo;
    Unbinder unbinder;

    private User user = User.getUser();


    public static MeFragment newInstance() {
        Bundle arguments = new Bundle();
        arguments.putString("id", "MeFragment");
        MeFragment fragment = new MeFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initScore();
        Log.d("memememe", "onResume: ");
    }

    private void initScore() {
        if (user.getJoinTeamID() == null || user.getJoinTeamID().isEmpty()) {
            setNoScore();
        } else {
            getScore();
        }
    }

    private void setNoScore() {
        textNoScoreInfo.setVisibility(View.VISIBLE);
    }

    private void getScore() {
        BmobQuery<ScoreBeanDB> query = new BmobQuery<>();
        query.addWhereEqualTo("userName", user.getUserName());
        query.addWhereEqualTo("userStuID", user.getUserStuID());
        query.order("-week");
        query.findObjects(new FindListener<ScoreBeanDB>() {
            @Override
            public void done(List<ScoreBeanDB> list, BmobException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        handleScore(list);
                    } else {
                        setNoScore();
                    }
                } else {
                    Toast.makeText(getContext(), "查询失败", Toast.LENGTH_SHORT).show();
                    setNoScore();
                }
            }
        });
    }

    private void handleScore(List<ScoreBeanDB> list) {
        int temp = 0;
        List<MyScore> slist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int score = Integer.parseInt(list.get(i).getNoteScore()) +
                    Integer.parseInt(list.get(i).getCodeScore()) +
                    Integer.parseInt(list.get(i).getAnswerScore());
            MyScore myScore = new MyScore();
            myScore.setScore(score);
            myScore.setWeek(list.get(i).getWeek());
            slist.add(myScore);
            temp += score;
        }
        temp = temp / list.size();
        textScoreAll.setText(temp + "分");
        initRecycler(slist);
    }

    private void initRecycler(List<MyScore> list) {
        textNoScoreInfo.setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        MyScoreRecyclerAdapter adapter = new MyScoreRecyclerAdapter(list,getContext());
        recyclerScore.setLayoutManager(layoutManager);
        recyclerScore.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public class MyScore {
        private int score;
        private String week;

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }
    }
}
