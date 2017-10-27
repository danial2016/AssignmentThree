package com.example.daniel.assignmentthree;

import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static Controller controller;
    private Button btnLogin, btnSignUp;
    private EditText userName, password;
    private DatabaseUser dbU;
    private DatabaseGroups dbG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
        dbU = new DatabaseUser(this);
        dbG = new DatabaseGroups(this);
        controller = new Controller(dbU, dbG, this, this);
        registerButtonListener();
    }

    public void initializeComponents(){
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
    }

    public void registerButtonListener(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userName.getText().toString();
                String pass = password.getText().toString();
                if(controller.userAlreadyExists(name, pass) == false){
                    User user = new User(name, pass);
                    controller.signUp(user);
                }else{
                    Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userName.getText().toString();
                String pass = password.getText().toString();
                if(controller.userAlreadyExists(name, pass) == true){
                    controller.login(name);
                    Toast.makeText(getApplicationContext(), "Welcome " + name, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "You don't exist!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void toast(String toastMsg) {
        Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
    }
}
