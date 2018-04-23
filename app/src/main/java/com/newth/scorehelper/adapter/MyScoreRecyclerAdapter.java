package com.newth.scorehelper.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newth.scorehelper.R;
import com.newth.scorehelper.ui.fragment.MeFragment;

import java.util.List;

/**
 * @author Mr.chen
 */
public class MyScoreRecyclerAdapter extends RecyclerView.Adapter<MyScoreRecyclerAdapter.MyViewHolder>  {
    List<MeFragment.MyScore> list;
    public MyScoreRecyclerAdapter(List<MeFragment.MyScore> list){
        this.list=list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_score,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textScore.setText(list.get(position).getScore()+"");
        holder.textWeek.setText(list.get(position).getWeek());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textWeek;
        TextView textScore;
        public MyViewHolder(View itemView) {
            super(itemView);
            textWeek=itemView.findViewById(R.id.text_score_week);
            textScore=itemView.findViewById(R.id.text_score);
        }
    }
}
