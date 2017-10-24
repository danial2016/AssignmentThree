package com.example.daniel.assignmentthree;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static Controller controller;
    private Button button, btnLogin;
    boolean boundToService = false;
    boolean bound = false;
    ServiceClass serviceClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
        controller = new Controller(this, this);
        registerButtonListener();
    }

    public void initializeComponents(){
        button = (Button) findViewById(R.id.button);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }

    public void registerButtonListener(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.seeAllProfiles();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.login();
            }
        });
    }

}
