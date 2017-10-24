package com.example.daniel.assignmentthree;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.ServerSocket;

/**
 * Created by Daniel on 2017-10-19.
 */

public class Controller{
    private MainActivity ma;
    private boolean boundToService = false;
    private boolean bound = false;
    //Client client;
    Context context;
    //private byte[] imageData;
    //private ClientProtocol clientProtocol;
    ServiceClass serviceClass;

    public Controller (MainActivity ma, Context context){
        this.ma = ma;
        this.context = context;
        ServiceConn serviceConn = new ServiceConn();
        Intent serviceIntent = new Intent(ma, ServiceClass.class);
        boundToService = ma.bindService(serviceIntent, serviceConn, 0);
        ma.startService(serviceIntent);
        //clientProtocol = new ClientProtocol();
    }

    public void connect(){
        if(serviceClass != null){
            Log.i("ServiceClass", "is valid and NOT null");
        }else{
            Log.i("ServiceClass", "is null");
        }
        boolean port = checkIfPortAvailable(8080);
        if(port){
            serviceClass.startServer(8080);
            serviceClass.connectToServer("10.0.2.15", 8080);
            //new ServerThread(8080).start();
            //new ClientThread("10.0.2.15", 8080).start();
        }else{
            Log.i("Port", "NOT AVAILABLE");
        }
    }

    public void decodeJSON(String fromServer){
        try{
            JSONObject obj = new JSONObject(fromServer);
            if(obj.get("type").equals("uploadImage")){
                int port = Integer.parseInt(obj.get("port").toString());
                byte[] imageData = serviceClass.getImageData();
                serviceClass.startImageServer(port);
                serviceClass.connectToServer("10.0.2.15", port);
                if(imageData != null){
                    Log.i("Image data", "is complete");
                    new UploadImageToServer(port,imageData).start();
                }else{
                    Log.i("Image data", "is null");
                }
                /*
                new ImageServerThread(port).start();
                new ClientThread("10.0.2.15", port).start();
                if(imageData != null){
                    Log.i("Image data", "is complete");
                    new UploadImageToServer(port,imageData).start();
                }else{
                    Log.i("Image data", "is null");
                }
                */
            }
        }catch (JSONException e){

        }
    }

    public void seeAllProfiles(){
        serviceClass.seeAllProfiles();
        //client.sendMessage(clientProtocol.getAllProfiles());
    }

    public void uploadImage(byte[] imageData){
        serviceClass.uploadImage(imageData);
        //client.sendMessage(clientProtocol.uploadImage("myImage"));
        //this.imageData = imageData;
    }

    private class ServiceConn implements ServiceConnection {
        public void onServiceConnected(ComponentName arg0, IBinder binder) {
            ServiceClass.LocalBinder ls = (ServiceClass.LocalBinder) binder;
            serviceClass = ls.getService();
            bound = true;
            setServiceInstance(serviceClass);
            Log.i("Service", "is bound");
        }

        public void onServiceDisconnected(ComponentName arg0) {
            Log.i("Service", "is NOT bound");
            bound = false;
        }
    }

    private void setServiceInstance(ServiceClass serviceInstance) {
        serviceClass = serviceInstance;
    }

    public void login() {
        Intent intent = new Intent(context,ViewPagerAdapter.class);
        context.startActivity(intent);
    }

    private static boolean checkIfPortAvailable(int port) {
        ServerSocket tcpSocket = null;
        DatagramSocket udpSocket = null;

        try {
            tcpSocket = new ServerSocket(port);
            tcpSocket.setReuseAddress(true);

            udpSocket = new DatagramSocket(port);
            udpSocket.setReuseAddress(true);
            return true;
        } catch (IOException ex) {
            // denotes the port is in use
        } finally {
            if (tcpSocket != null) {
                try {
                    tcpSocket.close();
                } catch (IOException e) {
					/* not to be thrown */
                }
            }

            if (udpSocket != null) {
                udpSocket.close();
            }
        }
        return false;
    }

    /*
    private class ServiceConn implements ServiceConnection {
        public void onServiceConnected(ComponentName arg0, IBinder binder) {
            ServiceClass.LocalBinder ls = (ServiceClass.LocalBinder) binder;
            serviceClass = ls.getService();
            bound = true;
            Log.i("Service", "is bound");
        }

        public void onServiceDisconnected(ComponentName arg0) {
            Log.i("Service", "is NOT bound");
            bound = false;
        }
    }
    */

}
