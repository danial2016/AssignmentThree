package com.example.daniel.assignmentthree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Controller controller;
    private Button button, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new Controller(this,this);
        initializeComponents();
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
