package com.newth.scorehelper.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.newth.scorehelper.R;
import com.newth.scorehelper.ui.fragment.MeFragment;
import com.newth.scorehelper.ui.view.ScratchCardView;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author Mr.chen
 */
public class MyScoreRecyclerAdapter extends RecyclerView.Adapter<MyScoreRecyclerAdapter.MyViewHolder>  {
    List<MeFragment.MyScore> list;
    Context context;
    SharedPreferences preferences;
    public MyScoreRecyclerAdapter(List<MeFragment.MyScore> list, Context context){
        this.list=list;
        this.context=context;
        preferences=context.getSharedPreferences("score",MODE_PRIVATE);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_score,parent,false);
        final MyViewHolder viewHolder=new MyViewHolder(view);
        viewHolder.textScore.setOnclearCardListener(new ScratchCardView.onClearCardListener() {
            @Override
            public void clear() {
                SharedPreferences.Editor editor=preferences.edit();
                editor.putBoolean(list.get(viewHolder.getAdapterPosition()).getWeek(),true);
                editor.apply();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (preferences.getBoolean(list.get(position).getWeek(),false)){
            holder.textScore.setClear(true);
        }
        holder.textScore.setText(list.get(position).getScore()+"");
        holder.textWeek.setText(list.get(position).getWeek());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textWeek;
        ScratchCardView textScore;
        public MyViewHolder(View itemView) {
            super(itemView);
            textWeek=itemView.findViewById(R.id.text_score_week);
            textScore=itemView.findViewById(R.id.text_score);
        }
    }
}
