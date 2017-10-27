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
    private String[] profiles = {"Daniel", "Mardokh", "Ansam", "Namra", "Jelena"};
    private JSONObject jsonObject = new JSONObject();
    private DatabaseUser dbU;
    //ArrayList must be made static otherwise it will be initialized every time an instance of this class is created
    private static ArrayList<String> listOfUsersConnectedToServer = new ArrayList<String>();


    public ServerProtocol(DatabaseUser dbU){
        this.dbU = dbU;
    }

    public String processInput(String input){
        try {
            JSONObject inputObj = new JSONObject(input);
            if(inputObj.get("type").equals("profiles")){
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < listOfUsersConnectedToServer.size(); i++) {
                    jsonArray.put(i, listOfUsersConnectedToServer.get(i));
                }
                jsonObject.put("type", "profiles");
                jsonObject.put("profiles", jsonArray);
            }
            if(inputObj.get("type").equals("request")){
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
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}
