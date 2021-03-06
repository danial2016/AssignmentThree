package com.example.daniel.assignmentthree;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 2017-10-19.
 */

public class Controller{
    private MainActivity ma;
    private boolean boundToService = false;
    private boolean bound = false;
    private Context context;
    private ServiceClass serviceClass;
    private DatabaseUser dbU;
    private DatabaseGroups dbG;
    private String userName;
    private ListView listView;
    private ArrayList<String> listOfFriends = new ArrayList<String>();
    private ArrayList<String> listOfGroups = new ArrayList<String>();


    public Controller (DatabaseUser dbU, DatabaseGroups dbG, MainActivity ma, Context context){
        this.ma = ma;
        this.context = context;
        this.dbU = dbU;
        this.dbG = dbG;
        ServiceConn serviceConn = new ServiceConn();
        Intent serviceIntent = new Intent(ma, ServiceClass.class);
        boundToService = ma.bindService(serviceIntent, serviceConn, 0);
        ma.startService(serviceIntent);
    }

    public void connect(String hostName, int portNumber, String userName, DatabaseUser dbU){
        if(serviceClass != null){
            Log.i("ServiceClass", "is valid and NOT null");
        }else{
            Log.i("ServiceClass", "is null");
        }
        boolean port = checkIfPortAvailable(portNumber);
        if(port){
            serviceClass.startServer(dbG, dbU,portNumber);
            serviceClass.connectToServer(userName, hostName, portNumber);
        }else{
            Log.i("Com", "NOT AVAILABLE");
        }
    }

    public void decodeJSON(String fromServer){
        try{
            JSONObject obj = new JSONObject(fromServer);
            if(obj.get("type").equals("profiles")){
                JSONArray jsonArray = obj.getJSONArray("profiles");
                String[] listOfUsers = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    listOfUsers[i] = jsonArray.get(i).toString();
                }
                Intent intent = new Intent(context, NavigationBarActivity.class);
                intent.putExtra("listOfUsers", listOfUsers);
                context.startActivity(intent);
            }
            if(obj.get("type").equals("friend")){
                addFriendToList(obj.get("userName").toString());
            }
            if(obj.get("type").equals("groups")){
                JSONArray groupsArray = obj.getJSONArray("groups");
                String[] listOfGroups = new String[groupsArray.length()];
                if(listOfGroups.length > 0){
                    expandListOfGroups(listOfGroups);
                }else{
                    Log.i("Groups", "No groups available");
                }
            }
            if(obj.get("type").equals("createdGroup")){
                Log.i("Message", "Group has been created");
            }
        }catch (JSONException e){
            e.printStackTrace();
            Log.i("ERROR", "JSONException thrown");
        }
    }

    private void expandListOfGroups(String[] groups) {
        for (int i = 0; i < groups.length; i++) {
            listOfGroups.add(groups[i]);
        }
    }

    public ArrayList<String> getListOfGroups(){
        return listOfGroups;
    }

    public String getUserName(){
        return userName;
    }

    private void addFriendToList(String userName) {
        listOfFriends.add(userName);
    }

    public ArrayList<String> getListOfFriends(){
        return listOfFriends;
    }

    public void seeAllProfiles(){
        serviceClass.seeAllProfiles();
    }

    public void seeAllGroups() {
        serviceClass.seeAllGroups();
    }


    /*
    Checks if the user already is registered. If false is returned then the user does not exist
    and should accordingly sign up.
     */
    public boolean userAlreadyExists(String userName, String password) {
        Cursor cursor = dbU.getAllData();
        if(cursor.getCount() == 0){
            ma.toast("No users in database");
        }
        while(cursor.moveToNext()) {
            if (cursor.getString(1).equals(userName) && cursor.getString(2).equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void signUp(User user) {
        boolean res = dbU.addUserInfo(user);
        if(res){
            ma.toast("New user has been added");
        }else{
            ma.toast("Failed to add new user: " + res);
        }
    }

    public void affirmDisconnect(String userName) {
        serviceClass.affirmDisconnect(userName);
    }

    public void createGroup(String userName, String groupName) {
        serviceClass.createGroup(userName, groupName);

    }

    public void updateUI(Context applicationContext) {
        ArrayList<String> tempList = getListOfGroups();
        String[] listofGroups = new String[tempList.size()];
        for(int i = 0; i < tempList.size(); i++){
            listofGroups[i] = tempList.get(i);
        }
        Intent intent = new Intent(applicationContext, GroupsActivity.class);
        intent.putExtra("listOfGroups", listofGroups);
        applicationContext.startActivity(intent);
    }

    private class ServiceConn implements ServiceConnection {
        public void onServiceConnected(ComponentName arg0, IBinder binder) {
            ServiceClass.LocalBinder ls = (ServiceClass.LocalBinder) binder;
            serviceClass = ls.getService();
            bound = true;
            setServiceInstance(serviceClass);
            Log.i("Service", "is bound");
        }

        public void onServiceDisconnected(ComponentName arg0) {
            Log.i("Service", "is NOT bound");
            bound = false;
        }
    }

    private void setServiceInstance(ServiceClass serviceInstance) {
        serviceClass = serviceInstance;
    }

    public void sendRequest(String userName){
        serviceClass.sendRequest(userName);
    }

    public void login(String userName) {
        this.userName = userName;
        serviceClass.setDatabaseUser(dbU);
        serviceClass.setDatabaseGroups(dbG);
        connect("10.2.2.41", 8080, userName, dbU);
        Intent intent = new Intent(context,NavigationBarActivity.class);
        context.startActivity(intent);
        serviceClass.affirmUserConnectionToServer(userName);
        serviceClass.affirmUserConnectionToServer(userName);
        seeAllProfiles();
        seeAllProfiles();
        seeAllGroups();
        seeAllGroups();
    }

    private static boolean checkIfPortAvailable(int port) {
        ServerSocket tcpSocket = null;
        DatagramSocket udpSocket = null;

        try {
            tcpSocket = new ServerSocket(port);
            tcpSocket.setReuseAddress(true);

            udpSocket = new DatagramSocket(port);
            udpSocket.setReuseAddress(true);
            return true;
        } catch (IOException ex) {
            // denotes the port is in use
        } finally {
            if (tcpSocket != null) {
                try {
                    tcpSocket.close();
                } catch (IOException e) {
					/* not to be thrown */
                }
            }

            if (udpSocket != null) {
                udpSocket.close();
            }
        }
        return false;
    }

}
