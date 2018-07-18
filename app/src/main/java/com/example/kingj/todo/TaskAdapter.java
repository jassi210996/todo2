package com.example.kingj.todo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {


    List<Task>tasks;
    Context context;
    OnItemClick listner;

    TaskAdapter(Context context,List<Task> tasks,OnItemClick click)
    {
        listner=click;
        this.context=context;
        this.tasks=tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowLayout = inflater.inflate(R.layout.rowlayout,viewGroup,false);

        return new TaskViewHolder(rowLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskViewHolder holder, int position) {

        final Task task = tasks.get(position);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.itemClick(v,holder.getAdapterPosition());
            }
        });
        String otit=task.getTaskTitle();
        holder.title.setText(task.getTaskTitle());
        holder.date.setText(task.getTaskDate());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.itemClick(v,holder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
