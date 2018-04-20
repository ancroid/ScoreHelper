package com.newth.scorehelper.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newth.scorehelper.R;
import com.newth.scorehelper.adapter.TeamRecyclerAdapter;
import com.newth.scorehelper.bean.Inner.User;
import com.newth.scorehelper.bean.TeamBeanDB;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Mr.chen on 2018/3/18.
 */

public class TeamFragment extends Fragment {
    @BindView(R.id.recycler_team)
    RecyclerView recyclerTeam;
    Unbinder unbinder;
    @BindView(R.id.text_no_team_info)
    TextView textNoTeamInfo;
    private User user = User.getUser();
    private TeamBeanDB mteam=new TeamBeanDB();

    public static TeamFragment newInstance() {
        Bundle arguments = new Bundle();
        arguments.putString("id", "TeamFragment");
        TeamFragment fragment = new TeamFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initTeam();
    }

    private void initTeam() {
        if (user.getJoinTeamID()==null || user.getJoinTeamID().isEmpty()) {
            textNoTeamInfo.setVisibility(View.VISIBLE);
        } else {
            getTeamInfo();
        }
    }
    private void getTeamInfo(){
        BmobQuery<TeamBeanDB> query = new BmobQuery<>();
        query.getObject(user.getJoinTeamID(), new QueryListener<TeamBeanDB>() {
            @Override
            public void done(TeamBeanDB teamBeanDB, BmobException e) {
                if(e==null){
                    mteam=teamBeanDB;
                    initFragment();
                }else{
                    Log.d("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
    private void initFragment(){
        if (textNoTeamInfo.getVisibility()==View.VISIBLE){
            textNoTeamInfo.setVisibility(View.GONE);
        }
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        TeamRecyclerAdapter adapter=new TeamRecyclerAdapter(mteam,getContext());
        recyclerTeam.setLayoutManager(layoutManager);
        recyclerTeam.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
