package com.example.daniel.assignmentthree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class GroupsActivity extends AppCompatActivity {
    private Controller controller;
    private ListView listViewOfGroups;
    private Button btnCreateGroup;
    private String[] listOfGroups;
    private EditText edtGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        this.controller = MainActivity.controller;
        initializeComponents();
        Bundle bundle = this.getIntent().getExtras();
        try{
            listOfGroups = bundle.getStringArray("listOfGroups");
            if (listOfGroups.length >0){
                listViewOfGroups.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listOfGroups));
                listViewOfGroups.setOnItemClickListener(new ListViewerListener());
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            Log.i("ERROR", "list of groups is empty");
        }
        registerButtonListener();
    }

    private void initializeComponents() {
        listViewOfGroups = (ListView) findViewById(R.id.listViewOfGroups);
        btnCreateGroup = (Button) findViewById(R.id.btnCreateGroup);
        edtGroupName = (EditText) findViewById(R.id.edtGroupName);
    }

    private void registerButtonListener() {
        btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.createGroup(controller.getUserName(), edtGroupName.getText().toString());
                //controller.updateUI(getApplicationContext());
            }
        });
    }

    private class ListViewerListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
            //controller.registerToGroup(listOfGroups[pos]);
            Toast.makeText(getApplicationContext(), "You have joined " + listOfGroups[pos], Toast.LENGTH_SHORT).show();
        }
    }
}
