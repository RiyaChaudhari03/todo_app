package com.example.todo.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.AddNewTask;
import com.example.todo.MainActivity;
import com.example.todo.Model.toDoModel;
import com.example.todo.R;
import com.example.todo.Utils.dataBaseHandler;

import java.util.List;

public class toDoAdapter extends RecyclerView.Adapter<toDoAdapter.ViewHolder> {

    private List<toDoModel> todolist;
    // define the Database
    private dataBaseHandler db;
    private MainActivity activity;


    public toDoAdapter(dataBaseHandler db, MainActivity activity) {
        this.db = db;
        this.activity = activity;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tasks_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        db.openDataBase();

        final toDoModel item = todolist.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db.updateStatus(item.getId(), 1);
                } else {
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    private boolean toBoolean(int n) {

        return n != 0;
    }

    @Override
    public int getItemCount() {
        return todolist.size();
    }

    public Context getContext() {
        return activity;
    }

    public void setTasks(List<toDoModel> todolist) {
        this.todolist = todolist;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        toDoModel item = todolist.get(position);
        db.deleteTask(item.getId());
        todolist.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position) {
        toDoModel item = todolist.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox task;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.checkbox1);
        }
    }
}
