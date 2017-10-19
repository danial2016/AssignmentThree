package com.example.daniel.assignmentthree;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Created by Daniel on 2017-10-19.
 */

public class Controller {
    private MainActivity ma;
    private boolean boundToService, bound = false;
    private ServiceClass serviceClass;

    public Controller (MainActivity ma){
        this.ma = ma;
        ServiceConn serviceConn = new ServiceConn();
        Intent serviceIntent = new Intent(ma, ServiceClass.class);
        boundToService = ma.bindService(serviceIntent, serviceConn, 0);
        ma.startService(serviceIntent);
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
