package com.newth.scorehelper.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newth.scorehelper.R;
import com.newth.scorehelper.bean.Inner.User;
import com.newth.scorehelper.bean.TeamBeanDB;
import com.newth.scorehelper.ui.activity.ScoreActivity;

import java.util.List;

/**
 * Created by Mr.chen on 2018/3/26.
 */

public class TeamRecyclerAdapter extends RecyclerView.Adapter<TeamRecyclerAdapter.MyViewHolder>  {
    private List<String> teamMembName;
    private List<Long> teamMembStuID;
    private Context context;
    public TeamRecyclerAdapter(TeamBeanDB teamBeanDB, Context context){
        teamMembName=teamBeanDB.getTeamMembName();
        teamMembStuID=teamBeanDB.getTeamMembStuID();
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team,parent,false);
        final MyViewHolder viewHolder=new MyViewHolder(view);
        if (User.getInstance().isLeader()){
            viewHolder.clickView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ScoreActivity.class);
                    intent.putExtra("id",teamMembStuID.get(viewHolder.getAdapterPosition()));
                    intent.putExtra("name",teamMembName.get(viewHolder.getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textNum.setText(position+1);
        holder.textId.setText(String.valueOf(teamMembStuID.get(position)));
        holder.textName.setText(teamMembName.get(position));
    }

    @Override
    public int getItemCount() {
        return teamMembStuID.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textNum;
        TextView textId;
        TextView textName;
        View clickView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textNum=itemView.findViewById(R.id.text_memb_num);
            textId=itemView.findViewById(R.id.text_memb_id);
            textName=itemView.findViewById(R.id.text_memb_name);
            clickView=itemView.findViewById(R.id.line_item_team);
        }
    }
}
