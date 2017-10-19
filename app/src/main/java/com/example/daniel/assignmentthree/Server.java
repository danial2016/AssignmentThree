package com.example.daniel.assignmentthree;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Daniel on 2017-10-19.
 */

public class Server {
    private int portNumber = 8080;

    public void startServer() throws IOException{
        try (
                //exception is thrown if it can't listen on the specified port
                ServerSocket serverSocket = new ServerSocket(portNumber);
                //blocks/waits until a client starts up and requests a connection on the host and port of this server.
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        )
        {


        }catch (IOException e) {
            Log.i("Exception", "Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
