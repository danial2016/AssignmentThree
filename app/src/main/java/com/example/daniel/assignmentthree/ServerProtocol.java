package com.example.daniel.assignmentthree;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Daniel on 2017-10-21.
 */

public class ServerProtocol {
    private String[] profiles = {"Daniel", "Mardokh", "Ansam", "Namra", "Jelena"};
    private JSONObject jsonObject = new JSONObject();

    public String processInput(String input){
        try {
            JSONObject inputObj = new JSONObject(input);
            if(inputObj.get("type").equals("profiles")){
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < profiles.length; i++) {
                    jsonArray.put(i, profiles[i]);
                }
                jsonObject.put("type", "profiles");
                jsonObject.put("profiles", jsonArray);
            }
            if(inputObj.get("type").equals("request")){
                jsonObject.put("type", "request");
                jsonObject.put("request", "Sending request to userName");
            }
            if(inputObj.get("type").equals("userInfo")){
                jsonObject.put("type", "userInfo");
                jsonObject.put("userInfo", "Fake userInfo");
            }
            if (inputObj.get("type").equals("image")){
                jsonObject.put("type", "uploadImage");
                jsonObject.put("port", String.valueOf(new Port().getPort()));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}
