package com.example.daniel.assignmentthree;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Daniel on 2017-10-19.
 */

public class ServiceClass extends Service {
    Client client;
    private byte[] imageData;
    private ClientProtocol clientProtocol;

    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    public byte[] getImageData() {
        return imageData;
    }

    public class LocalBinder extends Binder {
        //client receives the Binder and can use it to directly access public
        // methods available in either the Binder implementation or the Service.
        public ServiceClass getService(){
            return ServiceClass.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        clientProtocol = new ClientProtocol();
        Log.i("Service", "has been started");
        return super.onStartCommand(intent, flags, startId);
    }


    public void startServer(int port) {
        new ServerThread(port).start();
    }

    public void startImageServer(int port) {
        new ImageServerThread(port).start();
    }

    public void connectToServer(String hostName, int portNumber) {
        new ClientThread(hostName, portNumber).start();
    }


    private class ImageServerThread extends Thread{
        int portNumber;

        public ImageServerThread(int portNumber){
            this.portNumber = portNumber;
        }

        @Override
        public void run() {
            ImageServer imageServer = new ImageServer();
            imageServer.startImageServer(portNumber);
            Log.i("ImageServer status", "started");
        }
    }

    private class ServerThread extends Thread{
        int port;

        public ServerThread(int port){
            this.port = port;
        }

        @Override
        public void run() {
            Server server = new Server();
            server.startServer(port);
            Log.i("Server status", "started");
        }
    }

    private class ClientThread extends Thread{
        String hostName;
        int portNumber;

        public ClientThread(String hostName, int portNumber){
            this.hostName = hostName;
            this.portNumber = portNumber;
        }

        public void run() {
            client = new Client();
            client.setController(MainActivity.controller);
            client.startClient(hostName, portNumber);
        }
    }

    public void seeAllProfiles(){
        client.sendMessage(clientProtocol.getAllProfiles());
    }

    public void uploadImage(byte[] imageData){
        client.sendMessage(clientProtocol.uploadImage("myImage"));
        this.imageData = imageData;
    }

}
