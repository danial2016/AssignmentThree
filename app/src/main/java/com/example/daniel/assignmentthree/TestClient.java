package com.example.daniel.assignmentthree;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

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

public class TestClient{

    public TestClient(){

    }

    public void startClient() {
        String hostName = "10.0.2.15"; //OMG! It was supposed to be the emulators IP!!!!!!!!!!!!!!!!!!!!!!!!!!!
        int portNumber = 8080;
        try {
            InetAddress address = InetAddress.getByName(hostName);
            Socket socket = new Socket(address, portNumber);
            Log.i("Client ", "new Socket");
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;
            fromServer = in.readLine();
            Log.i("Client: ", fromServer);

            while ((fromServer = in.readLine()) != null) {
                Log.i("From server", "Server: " + fromServer);
                out.println("Hello");
            }
        } catch (UnknownHostException e) {
            Log.i("Err", "Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            Log.i("Err", "Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }


    }
}
