package com.example.daniel.assignmentthree;

import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Daniel on 2017-10-21.
 */

public class ServerProtocol {
    private JSONObject jsonObject = new JSONObject();
    private DatabaseUser dbU;
    private DatabaseGroups dbG;
    //ArrayList must be made static otherwise it will be initialized every time an instance of this class is created
    private static ArrayList<String> listOfUsersConnectedToServer = new ArrayList<String>();


    public ServerProtocol(DatabaseGroups dbG, DatabaseUser dbU){
        this.dbG = dbG;
        this.dbU = dbU;
    }

    public String processInput(String input){
        try {
            JSONObject inputObj = new JSONObject(input);
            if (inputObj.get("type").equals("profiles")){
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < listOfUsersConnectedToServer.size(); i++) {
                    jsonArray.put(i, listOfUsersConnectedToServer.get(i));
                }
                jsonObject.put("type", "profiles");
                jsonObject.put("profiles", jsonArray);
            }
            if (inputObj.get("type").equals("request")){
                String userName = inputObj.get("userName").toString();
                jsonObject.put("type", "friend");
                jsonObject.put("userName", userName);
            }
            if (inputObj.get("type").equals("affirm")){
                String userName = inputObj.get("userName").toString();
                listOfUsersConnectedToServer.add(userName);
            }
            if (inputObj.get("type").equals("disconnect")){
                String userName = inputObj.get("userName").toString();
                listOfUsersConnectedToServer.remove(userName);
            }
            if (inputObj.get("type").equals("groups")){
                JSONArray jsonArray = new JSONArray();
                Cursor cursor = dbG.getAllData();
                while(cursor.moveToNext()) {
                    String group = cursor.getString(1);
                    jsonArray.put(group);
                }
                jsonObject.put("type", "groups");
                jsonObject.put("groups", jsonArray);
            }
            if (inputObj.get("type").equals("createGroup")){
                String groupName = inputObj.get("groupName").toString();
                String userName = inputObj.get("userName").toString();
                Group group = new Group(userName, groupName);
                dbG.addGroupInfo(group);
                jsonObject.put("type", "createdGroup");
                jsonObject.put("groupName", groupName);
                jsonObject.put("userName", userName);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}
