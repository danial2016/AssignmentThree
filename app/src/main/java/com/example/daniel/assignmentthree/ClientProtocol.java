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

    public String followProfileRequest(String userName){
        JSONObject obj = new JSONObject();
        try{
            obj.put("type", "request");
            obj.put("userName", userName);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String userInfo(String userName, String password){
        JSONObject obj = new JSONObject();
        try{
            obj.put("type", "userInfo");
            obj.put("userName", userName);
            obj.put("password", password);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return obj.toString();
    }

}
