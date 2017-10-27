package com.example.daniel.assignmentthree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FriendsActivity extends AppCompatActivity {
    private Controller controller;
    private ListView listViewOfFriends;
    private String[] listOfFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        this.controller = MainActivity.controller;
        initializeComponents();
        Bundle bundle = this.getIntent().getExtras();
        try{
            listOfFriends = bundle.getStringArray("listOfFriends");
            if (listOfFriends.length >0){
                listViewOfFriends.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listOfFriends));
                listViewOfFriends.setOnItemClickListener(new ListViewerListener());
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            Log.i("ERROR", "list of users is empty");
        }
    }

    private void initializeComponents() {
        listViewOfFriends = (ListView) findViewById(R.id.listViewOfFriends);
    }

    private class ListViewerListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
            Toast.makeText(getApplicationContext(), "Let's chat!", Toast.LENGTH_SHORT).show();
        }
    }
}
