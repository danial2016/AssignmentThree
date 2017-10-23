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
        int high = 9999;
        int low = 1000;
        port = rand.nextInt(high-low) + low;
    }

    public int getPort(){
        return port;
    }
}
