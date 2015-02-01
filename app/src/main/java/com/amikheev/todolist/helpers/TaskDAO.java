package com.amikheev.todolist.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private SQLiteDatabase db;
    private SQLiteHelper dbHelper;

    public TaskDAO(Context context) {
        dbHelper = new SQLiteHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public void createTask(String title, String date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("date", date);

        db.insert("tasks", null, contentValues);
    }

    public void deleteTask(long  taskId) {
        db.delete("tasks", "_id = " + taskId, null);
    }

    public List<Task> getTasks() {
        List<Task> taskList = new ArrayList<Task>();

        String[] tableColumns = new String[] {"_id", "title", "date"};

        Cursor cursor = db.query("tasks", tableColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Task task = new Task(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            taskList.add(task);

            cursor.moveToNext();
        }

        return taskList;
    }
}
