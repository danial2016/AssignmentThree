package com.example.daniel.assignmentthree;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.*;

/**
 * Created by Daniel on 2017-10-19.
 */

public class Server {
    private int portNumber = 8080;
    private ServerSocket serverSocket = null;

    public Server(){

    }

    public void startServer() {
        //exception is thrown if it can't listen on the specified port
        try {
            serverSocket = new ServerSocket(portNumber);
            Log.i("ServerSocket", "has been created");

            while (true) {
                // blocks the call until connection is created and Socket object returned; i.e it
                // loops forever, listening for client connection requests on a ServerSocket
                // once request comes in the Server accepts the connection and hands it the socket
                // returned from accept, and starts the thread. Then the server goes back to
                // listening for other connection requests
                Socket clientSocket = serverSocket.accept();
                Log.i("Connection status", "server has accepted client connection request");
                new MultiServerThread(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    private class MultiServerThread extends Thread{
        Socket socket = null;

        public MultiServerThread(Socket socket){
            this.socket = socket;
        }

        public void run() {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String inputLine, outputLine;
                outputLine = "Hello"; //server initiates with "Hello"
                out.println(outputLine);
                while((inputLine = in.readLine()) != null){
                    Log.i("From client", inputLine);
                    String toClient = new ServerProtocol().processInput(inputLine);
                    out.print(toClient);
                    Log.i("To client", toClient);
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
