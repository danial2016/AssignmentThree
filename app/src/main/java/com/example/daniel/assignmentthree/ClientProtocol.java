package com.example.daniel.assignmentthree;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Daniel on 2017-10-21.
 */

public class ClientProtocol {

    public String getAllProfiles(){
        JSONObject obj = new JSONObject();
        try{
            obj.put("type", "profiles");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String sendUserRequest(String userName){
        JSONObject obj = new JSONObject();
        try{
            obj.put("type", "request");
            obj.put("userName", userName);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String affirmUserConnectionToServer(String userName){
        JSONObject obj = new JSONObject();
        try{
            obj.put("type", "affirm");
            obj.put("userName", userName);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String chatMessage(String userName, String textMessage){
        JSONObject obj = new JSONObject();
        try{
            obj.put("type", "chatMessage");
            obj.put("userName", userName);
            obj.put("textMessage", textMessage);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String affirmDisconnectionFromServer(String userName){
        JSONObject obj = new JSONObject();
        try{
            obj.put("type", "disconnect");
            obj.put("userName", userName);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getAllGroups() {
        JSONObject obj = new JSONObject();
        try{
            obj.put("type", "groups");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String createGroup(String userName, String groupName) {
        JSONObject obj = new JSONObject();
        try{
            obj.put("type", "createGroup");
            obj.put("groupName", groupName);
            obj.put("userName", userName);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return obj.toString();
    }
}
