package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todoapp.database.DatabaseConnection;

public class MainActivity extends AppCompatActivity {

    public static DatabaseConnection databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseService = new DatabaseConnection(getApplicationContext());
    }


    @Override
    protected void onResume() {
        super.onResume();
        EditText userName, password;
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        userName.setText("");
        password.setText("");
    }

    public void login(View view) {
        EditText userName, password;
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        String userNameStr, passwordStr;
        userNameStr = userName.getText().toString();
        passwordStr = password.getText().toString();
        if (!isInputValid(userNameStr) || !isInputValid(passwordStr)) {
            Toast.makeText(this, "Giriş validasyon hatası", Toast.LENGTH_SHORT).show();
            return;
        }
        if (databaseService.checkUserLogin(userNameStr, passwordStr)) {

            Intent dashboardIntent = new Intent(this, Dashboard.class);
            dashboardIntent.putExtra("userName", userNameStr);
            startActivity(dashboardIntent);
        } else {
            Toast.makeText(this, "Hatalı Giriş !", Toast.LENGTH_SHORT).show();
        }

    }

    public static boolean isInputValid(String input) {
        if (input.isEmpty() || input.length() < 3) {
            return false;
        } else {
            return true;
        }
    }

    public void startRegisterActivity(View view) {
        Intent registerIntent = new Intent(this, Register.class);
        startActivity(registerIntent);
    }

}