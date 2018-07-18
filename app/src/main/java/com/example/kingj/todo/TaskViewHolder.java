package com.example.kingj.todo;

import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TaskViewHolder extends RecyclerView.ViewHolder{

    TextView title;
    TextView date;
    View itemView;
    ImageView imageView;

    public TaskViewHolder(View itemView) {
        super(itemView);
        this.itemView=itemView;
        imageView=itemView.findViewById(R.id.delete);
        title=itemView.findViewById(R.id.taskTitle);
        date=itemView.findViewById(R.id.taskDate);
    }

}
