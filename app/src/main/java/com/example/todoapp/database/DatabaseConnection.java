package com.example.todoapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseConnection extends SQLiteOpenHelper {

    private String userTable = "user";
    private String todoTable = "task";

    public DatabaseConnection(Context context) {
        super(context, "TODO_DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String userTable = "CREATE TABLE user(userName text primary key, password text)";
        db.execSQL(userTable);
        String todoTable = "CREATE TABLE task(id integer primary key autoincrement,userName text,gorevAdi text,durum boolean)";
        db.execSQL(todoTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // USER
    public boolean checkUserLogin(String userName, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select password from user where userName = ?", new String[]{userName});
        cursor.moveToFirst();
        try {
            if (password.equals(cursor.getString(0))) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            cursor.close();
        }
    }

    public boolean register(String userName, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName", userName);
        values.put("password", password);

        long insVal = db.insert(userTable, null, values);
        return insVal != -1;
    }


    // GOREV

    public ArrayList<HashMap<String, String>> getAllTasksByUserName(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<HashMap<String, String>> tasks = new ArrayList<>();
        Cursor cursor = db.rawQuery("select id,gorevAdi,durum from task where userName = ?", new String[]{userName});
        while (cursor.moveToNext()) {

            HashMap<String, String> task = new HashMap<>();
            task.put("Id", cursor.getString(0));
            task.put("Gorev", cursor.getString(1));
            task.put("Durum", convertStatusToText(cursor.getInt(2)));
            tasks.add(task);
        }

        cursor.close();
        return tasks;
    }

    public ArrayList<HashMap<String, String>> searchTasksByKeyword(String userName, String keyword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> tasks = new ArrayList<>();
        String keywordQuery = "%" + keyword + "%";
        Cursor cursor = db.rawQuery("select id,gorevAdi,durum from task where userName = ? and gorevAdi like ?", new String[]{userName, keywordQuery});
        while (cursor.moveToNext()) {

            HashMap<String, String> task = new HashMap<>();
            task.put("Id", cursor.getString(0));
            task.put("Gorev", cursor.getString(1));
            tasks.add(task);
        }

        cursor.close();
        return tasks;
    }


    public boolean addTask(String taskTxt, String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("gorevAdi", taskTxt);
        values.put("userName", userName);
        values.put("durum", false);
        long insVal = db.insert(todoTable, null, values);
        return insVal != -1;
    }


    public HashMap<String, String> returnTask(String userName, String taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select id,gorevAdi,userName,durum from task where id = ? and userName = ?", new String[]{taskId, userName});
        cursor.moveToFirst();
        HashMap<String, String> task = new HashMap<>();
        task.put("Id", cursor.getString(0));
        task.put("Gorev", cursor.getString(1));

        return task;
    }

    public void updateTask(String userName, String taskId, String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE task SET gorevAdi=? where id = ? and userName = ?", new String[]{task, taskId, userName});
    }

    public void deleteTask(String userName, String taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" delete from task where id = ? and userName = ?", new String[]{taskId, userName});
    }


    private String convertStatusToText(int status) {
        if (status == 0) {
            return "Tamamlanmadı";
        } else {
            return "Bitti";
        }
    }


}
