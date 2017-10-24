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

    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
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
        Log.i("Service", "has been started");
        return super.onStartCommand(intent, flags, startId);
    }

    /*
    public void startServer(int port) {
        new ServerThread(port).start();
    }

    public void startImageServer(int port) {
        new ImageServerThread(port).start();
    }

    public void connectToServer(Client client, String hostName, int portNumber) {
        new ClientThread(client, hostName, portNumber).start();
    }
    */

}
