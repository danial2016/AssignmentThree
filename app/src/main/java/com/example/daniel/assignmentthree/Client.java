package com.example.daniel.assignmentthree;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
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
    private PrintWriter out;

    public Client(){

    }

    public void startClient(String hostName, int portNumber) {
        try {
            InetAddress address = InetAddress.getByName(hostName);
            Socket socket = new Socket(address, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Log.i("From server", in.readLine());
            new Receive(in).start();
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
                while (in.readLine() != null) {
                    fromServer = in.readLine();
                    Log.i("RECEIVED", fromServer);
                    JSONObject obj = new JSONObject(fromServer);
                    if(obj.get("type").equals("uploadImage")){
                        int port = Integer.parseInt(obj.get("port").toString());
                        new ImageServer().startImageServer(port);
                        new UploadImageToServer(port).start();
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    private class UploadImageToServer extends Thread{
        int port;

        public UploadImageToServer(int port){
            this.port = port;
        }

        @Override
        public void run() {

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
