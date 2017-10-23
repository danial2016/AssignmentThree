package com.example.daniel.assignmentthree;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

/**
 * Created by Daniel on 2017-10-19.
 */

public class Controller {
    private MainActivity ma;
    private boolean boundToService, bound = false;
    private ServiceClass serviceClass;
    Client client;
    Context context;

    public Controller (MainActivity ma, Context context){
        this.ma = ma;
        this.context = context;
        ServiceConn serviceConn = new ServiceConn();
        Intent serviceIntent = new Intent(ma, ServiceClass.class);
        boundToService = ma.bindService(serviceIntent, serviceConn, 0);
        ma.startService(serviceIntent);
        boolean port = checkIfPortAvailable(8080);
        if(port){
            new ServerThread().start();
            new ClientThread().start();
        }else{
            Log.i("Port", "NOT AVAILABLE");
        }
    }

    public void seeAllProfiles(){
        client.sendMessage(new ClientProtocol().getAllProfiles());
    }

    public void login() {
        Intent intent = new Intent(context,ViewPagerAdapter.class);
        context.startActivity(intent);
    }

    private class ServerThread extends Thread{
        @Override
        public void run() {
            Server server = new Server();
            server.startServer();
            Log.i("Server status", "started");
        }
    }

    private class ClientThread extends Thread{

        public void run() {
            client = new Client();
            client.startClient();
        }
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


    private class ServiceConn implements ServiceConnection {
        public void onServiceConnected(ComponentName arg0, IBinder binder) {
            ServiceClass.LocalBinder ls = (ServiceClass.LocalBinder) binder;
            serviceClass = ls.getService();
            bound = true;
        }

        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    }

}
