package com.example.daniel.assignmentthree;

/**
 * Created by Daniel on 2017-10-25.
 */

public class User {
    private String userName;
    private String password;

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

}
