package com.example.daniel.assignmentthree;

import android.util.Log;

/**
 * Created by Daniel on 2017-10-23.
 */

public class UploadImageToServer extends Thread{
    private int port;
    private byte[] imageData;

    public UploadImageToServer(int port, byte[] imageData){
        this.imageData = imageData;
        this.port = port;
    }

    @Override
    public void run() {
        Log.i("Image", "has been sent to ImageServer");
    }
}
