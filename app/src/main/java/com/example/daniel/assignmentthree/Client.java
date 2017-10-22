package com.example.daniel.assignmentthree;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Daniel on 2017-10-20.
 */

public class Client {
    private BufferedReader in;
    PrintWriter out;

    public Client(){

    }

    public void startClient() {
        String hostName = "10.0.2.15"; //OMG! It was supposed to be the emulators IP!!!!!!!!!!!!!!!!!!!!!!!!!!!
        int portNumber = 8080;
        try {
            InetAddress address = InetAddress.getByName(hostName);
            Socket socket = new Socket(address, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Log.i("From server", in.readLine());
            if (in.readLine().equals("Hello")){
                new Receive(in).start();
            }
        } catch (UnknownHostException e) {
            Log.i("Err", "Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            Log.i("Err", "Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }

    private class Receive extends Thread{
        BufferedReader in;

        public Receive(BufferedReader in){
            this.in = in;
        }

        @Override
        public void run() {
            String fromServer;
            try{
                while ((fromServer = in.readLine()) != null) {
                    Log.i("RECEIVED", fromServer);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String msg){
        new Send(msg).start();
    }

    private class Send extends Thread{
        String msg;

        public Send(String msg){
            this.msg = msg;
        }

        @Override
        public void run() {
            try{
                if(out != null){
                    out.println(msg);
                    Log.i("SENT", msg);
                }else{
                    Log.i("PrintWriter", "is null");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}