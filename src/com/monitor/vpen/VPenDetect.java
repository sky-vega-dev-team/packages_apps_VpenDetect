package com.monitor.vpen;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.os.UEventObserver;
import android.util.Log;

import android.os.Build ;
import android.content.Context ;
import android.os.Vibrator;
import android.os.VibrationEffect ;

public class VPenDetect extends Service {
    private static final String LOG_TAG = "V-PEN";
    private static final String PEN_CONNECT = "ON";
    private static final String PEN_DISCONNECT = "OFF";
    private final Object mLock = new Object();
    static native int setTouchMode(int state);

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mUEventObserver.startObserving("DEVPATH=/devices/virtual/switch/touch_pen_detection");
        Log.i(LOG_TAG, "V-Pen Monitor service started");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(LOG_TAG, "V-Pen Monitor service stopped");
    }

    private final UEventObserver mUEventObserver = new UEventObserver() {
        @Override
        public void onUEvent(UEvent event) {
            synchronized (mLock) {
                String switchState = event.get("SWITCH_STATE");

                if (PEN_CONNECT.equals(switchState)) {
                    Log.d(LOG_TAG, "CONNECTED");
                    setTouchMode(0x0);
                } else if (PEN_DISCONNECT.equals(switchState)) {
                    Log.d(LOG_TAG, "DISCONNECTED");
                    setTouchMode(0x2);
                }

                Vibrator v = (Vibrator) getSystemService(Context. VIBRATOR_SERVICE ) ;
                assert v != null;
                if (Build.VERSION. SDK_INT >= Build.VERSION_CODES. O ) {
                      v.vibrate(VibrationEffect. createOneShot ( 100 ,
                      VibrationEffect. DEFAULT_AMPLITUDE )) ;
                } else {
                      v.vibrate( 100 ) ;
                }
            }
        }
    };
    static {
        System.loadLibrary("tm_switch");
    }
}
