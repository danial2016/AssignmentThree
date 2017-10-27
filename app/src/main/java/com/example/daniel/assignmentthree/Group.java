package com.example.daniel.assignmentthree;

import java.util.ArrayList;

/**
 * Created by Daniel on 2017-10-27.
 */

public class Group {
    String userName;
    String groupName;
    ArrayList<String> members = new ArrayList<String>();

    public Group(String userName, String groupName){
        this.userName = userName;
        this.groupName = groupName;
        addMember(userName);
    }

    public String getGroupName() {
        return groupName;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void addMember(String member) {
        members.add(member);
    }

    public void removeMember(String member) {
        members.remove(member);
    }
}
