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
    private ClientProtocol clientProtocol;
    private DatabaseUser dbU;

    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    public void sendRequest(String userName) {
        client.sendMessage(clientProtocol.sendUserRequest(userName));
    }

    public void seeAllProfiles(){
        client.sendMessage(clientProtocol.getAllProfiles());
    }

    public void affirmUserConnectionToServer(String userName) {
        client.sendMessage(clientProtocol.affirmUserConnectionToServer(userName));
    }

    public void affirmDisconnect(String userName) {
        client.sendMessage(clientProtocol.affirmDisconnectionFromServer(userName));
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

    public void setDatabaseUser(DatabaseUser dbU){
        this.dbU = dbU;
    }

    public void startServer(DatabaseUser dbU, int port) {
        new ServerThread(dbU, port).start();
    }

    public void connectToServer(String userName, String hostName, int portNumber) {
        new ClientThread(userName, hostName, portNumber).start();
    }

    private class ServerThread extends Thread{
        int port;
        DatabaseUser dbU;

        public ServerThread(DatabaseUser dbU, int port){
            this.dbU = dbU;
            this.port = port;
        }

        @Override
        public void run() {
            Server server = new Server(dbU);
            server.startServer(port);
            Log.i("Server status", "started");
        }
    }

    private class ClientThread extends Thread{
        String hostName;
        int portNumber;
        String userName;

        public ClientThread(String userName, String hostName, int portNumber){
            this.hostName = hostName;
            this.portNumber = portNumber;
            this.userName = userName;
        }

        public void run() {
            client = new Client(userName);
            client.setController(MainActivity.controller);
            client.startClient(hostName, portNumber);
        }
    }

    /*
    public void uploadImage(String pathToPicture){
        client.sendMessage(clientProtocol.uploadImage("myImage"));
        this.pathToPicture = pathToPicture;
    }


    public String getPathToPicture(){
        return pathToPicture;
    }
    */

}
