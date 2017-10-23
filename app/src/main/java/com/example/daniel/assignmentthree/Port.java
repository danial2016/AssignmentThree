package com.example.daniel.assignmentthree;

import java.util.Random;

/**
 * Created by Daniel on 2017-10-23.
 */

public class Port {
    private int port;
    private Random rand;

    public Port(){
        rand = new Random();
        port = 1000 + rand.nextInt(9999);
    }

    public int getPort(){
        return port;
    }
}
