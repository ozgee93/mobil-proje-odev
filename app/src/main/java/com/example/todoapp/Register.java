package com.example.todoapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
    }


    public void register(View view) {
        EditText userName, password;
        userName = findViewById(R.id.usernameRegister);
        password = findViewById(R.id.passwordRegister);
        String userNameStr, passwordStr;
        userNameStr = userName.getText().toString();
        passwordStr = password.getText().toString();
        if (!MainActivity.isInputValid(userNameStr) || !MainActivity.isInputValid(passwordStr)) {
            Toast.makeText(this, "Giriş validasyon hatası", Toast.LENGTH_SHORT).show();
            return;
        }
        if (MainActivity.databaseService.register(userNameStr, passwordStr)) {
            Toast.makeText(this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Veritabanı Hatası", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

}