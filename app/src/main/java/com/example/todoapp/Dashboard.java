package com.example.todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class Dashboard extends AppCompatActivity {
    ListView listView;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        this.userName = getIntent().getStringExtra("userName");
        this.listView = findViewById(R.id.listView);

        ListAdapter adapter = new SimpleAdapter(this,
                MainActivity.databaseService.getAllTasksByUserName(userName),
                R.layout.row, new String[]{"Gorev", "Durum"},
                new int[]{R.id.gorevAdi, R.id.durum});
        listView.setAdapter(adapter);
        Context mContext = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> taskMap = (HashMap) parent.getAdapter().getItem(position);
                Intent taskIntent = new Intent(mContext, ShowTask.class);
                taskIntent.putExtra("userName", userName);
                taskIntent.putExtra("taskId", taskMap.get("Id"));
                startActivity(taskIntent);
            }
        });
    }

    public void logoutUser(View view) {
        finish();
    }


    public void addTask(View view) {
        Intent taskIntent = new Intent(this, AddTask.class);
        taskIntent.putExtra("userName", userName);
        startActivity(taskIntent);
    }

    public void searchTask(View view) {
        TextView searchTask = findViewById(R.id.taskSearchKey);
        String keyword = searchTask.getText().toString();
        if (keyword.isEmpty() || keyword.length() < 2) {
            Toast.makeText(this, "Kelime 2 karakterden az olamaz !", Toast.LENGTH_SHORT).show();
            return;
        }
        ListAdapter adapter = new SimpleAdapter(this,
                MainActivity.databaseService.searchTasksByKeyword(userName, keyword),
                R.layout.row, new String[]{"Gorev", "Durum"},
                new int[]{R.id.gorevAdi, R.id.durum});
        listView.setAdapter(adapter);
    }


    public void showOpenTasks(View view) {
        updateList();
        Toast.makeText(this, "Liste Güncellendi", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    public void updateList() {
        ListAdapter adapter = new SimpleAdapter(this,
                MainActivity.databaseService.getAllTasksByUserName(userName),
                R.layout.row, new String[]{"Gorev"},
                new int[]{R.id.gorevAdi});
        listView.setAdapter(adapter);
    }

}