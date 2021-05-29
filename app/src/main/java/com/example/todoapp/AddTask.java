package com.example.todoapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddTask extends AppCompatActivity {
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
        this.userName = getIntent().getStringExtra("userName");

    }


    public void addNewTask(View view) {
        EditText taskText;
        taskText = findViewById(R.id.taskText);
        String taskTextStr;
        taskTextStr = taskText.getText().toString();
        if (MainActivity.databaseService.addTask(taskTextStr, userName)) {
            Toast.makeText(this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Veritabanı Hatası", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

}