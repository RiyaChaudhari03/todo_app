package com.example.todo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.DoneTasks;
import com.example.todo.Model.doneModel;
import com.example.todo.R;
import com.example.todo.Utils.dataBaseHandler;

import java.util.List;

public class doneAdapter extends RecyclerView.Adapter<doneAdapter.ViewHolder> {
    private List<doneModel> doneList;
    private dataBaseHandler db;
    private DoneTasks activity;

    public doneAdapter(dataBaseHandler db, DoneTasks activity) {
        this.db = db;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.done_task_layout, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        db.openDataBase();

        final doneModel item = doneList.get(position);
        holder.task.setText(item.getTask());

    }


    @Override
    public int getItemCount() {
        return doneList.size();
    }

    public Context getContext() {
        return activity;
    }

    public void setTasks(List<doneModel> doneList) {
        this.doneList = doneList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox task;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.checkbox2);
        }
    }
}