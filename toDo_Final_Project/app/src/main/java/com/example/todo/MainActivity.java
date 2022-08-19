package com.example.todo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Adapter.toDoAdapter;
import com.example.todo.Model.doneModel;
import com.example.todo.Model.toDoModel;
import com.example.todo.Utils.dataBaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    public List<doneModel> doneList;
    private RecyclerView tasksRecyclerView;
    private toDoAdapter tasksAdapter;
    private FloatingActionButton fab;
    private FloatingActionButton done;
    private List<toDoModel> taskList;
    private dataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //123456
        Objects.requireNonNull(getSupportActionBar()).hide();
        //654321
        //getSupportActionBar().hide();

        db = new dataBaseHandler(this);
        db.openDataBase();

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new toDoAdapter(db, this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        /* toDoModel task = new toDoModel();
        task.setTask("This is a test");
        task.setStatus(0);
        task.setId(1);

        taskList.add(task);
        taskList.add(task); */ //dummyData

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        fab = findViewById(R.id.fab);
        done = findViewById(R.id.done);

        //123456
        //taskList = new ArrayList<>();

        taskList = db.getAllTasks();
        Collections.reverse(taskList);

        tasksAdapter.setTasks(taskList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneList = db.doneTasks();
                openDoneModel();

            }


        });


    }

    private void openDoneModel() {
        Intent intent = new Intent(this, DoneTasks.class);
        startActivity(intent);
    }

    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks();
        Collections.reverse((taskList));
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();  //all this will update the Recyclerview

    }

}