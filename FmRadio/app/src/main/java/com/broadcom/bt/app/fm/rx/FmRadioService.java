
package com.broadcom.bt.app.fm.rx;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;

import java.io.FileReader;


public class FmRadioService extends Service {

    private static final String TAG = "FmRadioService";
    String HEADSET_STATE_PATH = "/sys/class/switch/h2w/state";
    private  int headsetState;
   // private HeadsetPlugUnplugBroadcastReceiver FmHeadsetPlugUnplugBroadcastReceiver;
    FileReader file;
    boolean server;
   // FmRadio mFmRadio;
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
 // 
    public void spraygunRun() {
        new Thread(new Runnable() {
            public void run() {
                    try {
                        while(headsetState==1){
                        Thread.sleep(1000);
                        file = new FileReader(HEADSET_STATE_PATH); 
                        char[] buffer = new char[1024]; 
                        int len = file.read(buffer, 0, 1024); 
                        headsetState = Integer.valueOf((new String(buffer, 0, len)).trim());
                        if(headsetState==0){
                            Log.v(TAG, "MMMMMMMMMMMMMMMMMMMMMMMMMMM");
                            try {
                                getPendingIntent().send();  
                             } catch (CanceledException e) {  
                                // TODO Auto-generated catch block   
                                e.printStackTrace();  
                            }  
                         
                          }
                       }
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                    }
                }

         
        }).start();
    }
    
       
        private PendingIntent getPendingIntent() {  
           Intent callback = new Intent();  
            callback.setClass(this, FmRadio.class);  
            callback.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP);  
            return PendingIntent.getActivity(this, 0, callback, PendingIntent.FLAG_CANCEL_CURRENT);  
        }  

    
    
    @Override
    public void onCreate() {

        Log.v(TAG, "onCreate");
        server=true;
        try{
        file = new FileReader(HEADSET_STATE_PATH); 
        char[] buffer = new char[1024]; 
        int len = file.read(buffer, 0, 1024); 
        headsetState = Integer.valueOf((new String(buffer, 0, len)).trim());
        }catch (Exception e){
            
        }
        spraygunRun();
     //   registerReceiver(FmHeadsetPlugUnplugBroadcastReceiver,
     //           new IntentFilter(Intent.ACTION_HEADSET_PLUG));
    }
    
    

    @Override
    public void onDestroy() {
        Log.v(TAG, "onDestroy");
    //    mFmRadio.onDestroy();
      //  unregisterReceiver(FmHeadsetPlugUnplugBroadcastReceiver);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.v(TAG, "onStart");
        if (intent != null) {
          
        }  

    }

    public void play() {
        Log.v(TAG, "FFFFF");
    }

    public void pause() {
        Log.v(TAG, "CCCCCCCCCCCCCC");
    }

    public void stop() {
        server=false;
        Log.v(TAG, "BBBBBBBBBBBBBB");
    }

//    public class HeadsetPlugUnplugBroadcastReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Log.v(TAG, "SSSSSSSSSSSSSSSS");
//            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_HEADSET_PLUG)) 
//            {
//                int state = intent.getIntExtra("state", 0);
//                if (intent.hasExtra("state")) {
//                    if (intent.getIntExtra("state", 0) == 0) {//Headset is not plugged
//                       // Toast.makeText(context, getResources().getString(R.string.plug_headset_warning), 100).show();
//                        Log.v(TAG, "TTTTTTTTTTTTTT");
//                    } else if (intent.getIntExtra("state", 0) == 1)//Headphones into
//                    {
//                        Log.v(TAG, "VVVVVVVVVVVVVV");
//                     //   Toast.makeText(context, getResources().getString(R.string.plug_headset_warning), 100).show();
//                    }
//                }
//                
//            }
//        }
//    }
}
