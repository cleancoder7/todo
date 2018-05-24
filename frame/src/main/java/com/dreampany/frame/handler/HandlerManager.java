package com.dreampany.frame.handler;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Created by Hawladar Roman on 5/24/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */
public final class HandlerManager {
    private Handler handler;
    private HandlerThread thread;

    public HandlerManager() {

    }

    synchronized public void start() {
        if (thread == null) {
            thread = new HandlerThread(HandlerManager.class.getSimpleName());
            thread.start();
            handler = new Handler(thread.getLooper());
        }
    }

    synchronized public void stop() {
        if (thread != null) {
            thread.quitSafely();
            try {
                thread.join();
                thread = null;
                handler = null;
            } catch (InterruptedException ignored) {
            }
        }
    }

    synchronized public void run(Runnable runnable) {
        if (handler != null) {
            handler.post(runnable);
        }
    }

    synchronized public void run(Runnable runnable, long delay) {
        if (handler != null) {
            handler.postDelayed(runnable, delay);
        }
    }
}
