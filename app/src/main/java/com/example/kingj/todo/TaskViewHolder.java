package com.example.kingj.todo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView date;


    public TaskViewHolder(View itemView) {
        super(itemView);
        title=itemView.findViewById(R.id.taskTitle);
        date=itemView.findViewById(R.id.taskDate);
    }
}
