package com.example.todo.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todo.Model.doneModel;
import com.example.todo.Model.toDoModel;

import java.util.ArrayList;
import java.util.List;

public class dataBaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "toDoListDB";
    private static final String TODO_TABLE = "toDo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";
    /*private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                + TASK + "TEXT, " + STATUS + " INTEGER)";*/
    private static final String CREATE_TODO_TABLE = "CREATE TABLE toDO (id INTEGER PRIMARY KEY AUTOINCREMENT, task TEXT, status INTEGER)";

    private SQLiteDatabase db;

    public dataBaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop existing table
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        //create table again
        onCreate(db);
    }

    public void openDataBase() {
        db = this.getWritableDatabase();
    }

    public void insertTask(toDoModel task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(STATUS, 0);
        db.insert(TODO_TABLE, null, cv);
    }

    public List<toDoModel> getAllTasks() {
        List<toDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db.query(TODO_TABLE, null, null, null, null, null, null, null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        toDoModel task = new toDoModel();
                        task.setId(cur.getInt(cur.getColumnIndexOrThrow(ID)));
                        task.setTask(cur.getString(cur.getColumnIndexOrThrow(TASK)));
                        task.setStatus(cur.getInt(cur.getColumnIndexOrThrow(STATUS)));
                        taskList.add(task);
                    } while (cur.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }

    public List<doneModel> doneTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<doneModel> donetask = new ArrayList<>();
        Cursor res = null;
        db.beginTransaction();
        try {
            res = db.rawQuery("select * from " + TODO_TABLE + " WHERE STATUS = '1' ", null);
            if (res != null) {
                if (res.moveToFirst()) {
                    do {
                        doneModel task = new doneModel();
                        task.setId(res.getInt(res.getColumnIndexOrThrow(ID)));
                        task.setTask(res.getString(res.getColumnIndexOrThrow(TASK)));
                        donetask.add(task);
                    } while (res.moveToNext());
                }

            }
        } finally {
            db.endTransaction();
            assert res != null;
            res.close();
        }

        return donetask;
    }

    public void updateStatus(int id, int status) {
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO_TABLE, cv, ID + "=?", new String[]{String.valueOf(id)});
    }

    public void updateTask(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(TODO_TABLE, cv, ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id) {
        db.delete(TODO_TABLE, ID + "=?", new String[]{String.valueOf(id)});
    }

}
