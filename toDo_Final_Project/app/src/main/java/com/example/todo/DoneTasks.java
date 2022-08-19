package com.example.todo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Adapter.doneAdapter;
import com.example.todo.Model.doneModel;
import com.example.todo.Utils.dataBaseHandler;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DoneTasks extends AppCompatActivity {

    private RecyclerView donetasksRecyclerView;
    private doneAdapter tasksAdapter;

    private List<doneModel> doneList;
    private dataBaseHandler db;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.done_task);

        Objects.requireNonNull(getSupportActionBar()).hide();

        db = new dataBaseHandler(this);
        db.openDataBase();

        donetasksRecyclerView = findViewById(R.id.done_tasksRecyclerView);
        donetasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new doneAdapter(db, this);
        donetasksRecyclerView.setAdapter(tasksAdapter);

        doneList = db.doneTasks();
        Collections.reverse(doneList);

        tasksAdapter.setTasks(doneList);


    }
}
