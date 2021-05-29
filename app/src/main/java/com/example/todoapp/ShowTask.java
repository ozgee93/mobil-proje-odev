package com.example.todoapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class ShowTask extends AppCompatActivity {
    String userName;
    String taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_task);
        this.userName = getIntent().getStringExtra("userName");
        this.taskId = getIntent().getStringExtra("taskId");
        EditText taskText;
        taskText = findViewById(R.id.taskText);
        HashMap<String, String> task = MainActivity.databaseService.returnTask(userName, taskId);
        taskText.setText(task.get("Gorev"));
    }


    public void finishTaskBtn(View view) {
        MainActivity.databaseService.deleteTask(userName, taskId);
        finish();
    }

    public void updateTask(View view) {
        EditText taskText;
        taskText = findViewById(R.id.taskText);
        MainActivity.databaseService.updateTask(userName, taskId, taskText.getText().toString());
        finish();
    }

}