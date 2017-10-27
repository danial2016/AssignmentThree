package com.example.daniel.assignmentthree;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NavigationBarActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Controller controller;
    private String[] listOfUsers;
    private ListView listViewOfUsers;
    private Button btnListOfUsers;
    private ActionBarDrawerToggle actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);
        this.controller = MainActivity.controller;
        initializeComponents();
        Bundle bundle = this.getIntent().getExtras();
        try{
            listOfUsers = bundle.getStringArray("listOfUsers");
            if (listOfUsers.length >0){
                listViewOfUsers.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listOfUsers));
                listViewOfUsers.setOnItemClickListener(new ListViewerListener());
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            Log.i("ERROR", "list of users is empty");
        }

        registerButtonListener();
    }

    private void initializeComponents() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBar = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        actionBar.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(actionBar);
        actionBar.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final NavigationView navView = (NavigationView) findViewById(R.id.navView);
        navView.setNavigationItemSelectedListener
                (new NavigationView.OnNavigationItemSelectedListener() {

                     @Override
                     public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                         int id = item.getItemId();
                         if (id == R.id.itemFriends) {
                             ArrayList<String> tempList = MainActivity.controller.getListOfFriends();
                             String[] listOfFriends = new String[tempList.size()];
                             for(int i = 0; i < tempList.size(); i++){
                                 listOfFriends[i] = tempList.get(i);
                             }
                             Intent intent = new Intent(getApplicationContext(), FriendsActivity.class);
                             intent.putExtra("listOfFriends", listOfFriends);
                             startActivity(intent);
                         }
                         if (id == R.id.itemGroups) {
                             Toast.makeText(getApplicationContext(), "Groups", Toast.LENGTH_SHORT).show();
                             //Intent intent = new Intent(getApplicationContext(), Groups.class);
                             //startService(intent);
                         }
                         return true;
                     }
                 }
                );
        listViewOfUsers = (ListView)findViewById(R.id.listViewOfUsers);
        btnListOfUsers = (Button) findViewById(R.id.btnListOfUsers);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBar.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private void registerButtonListener() {
        btnListOfUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.controller.seeAllProfiles();
                MainActivity.controller.seeAllProfiles();
            }
        });
    }

    private class ListViewerListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
            controller.sendRequest(listOfUsers[pos]);
        }
    }

}
