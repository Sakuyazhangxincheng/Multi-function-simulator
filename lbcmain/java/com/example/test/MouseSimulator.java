package com.example.test;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Build;
import android.os.SystemClock;
import android.view.InputDevice;
import android.view.MotionEvent;

public class MouseSimulator {
    public static void simulateMouseClick(Context context, int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            InputManager inputManager = (InputManager) context.getSystemService(Context.INPUT_SERVICE);
            int[] deviceIds = inputManager.getInputDeviceIds();
            for (int deviceId : deviceIds) {
                InputDevice device = inputManager.getInputDevice(deviceId);
                if (isMouseDevice(device)) {
                    MotionEvent event = MotionEvent.obtain(
                            0,
                            SystemClock.uptimeMillis(),
                            MotionEvent.ACTION_DOWN,
                            x,
                            y,
                            0
                    );


                    event = MotionEvent.obtain(
                            0,
                            SystemClock.uptimeMillis(),
                            MotionEvent.ACTION_UP,
                            x,
                            y,
                            0
                    );

                    break;
                }
            }
        }
    }

    private static boolean isMouseDevice(InputDevice device) {
        int sources = device.getSources();
        return (sources & InputDevice.SOURCE_MOUSE) == InputDevice.SOURCE_MOUSE;
    }
}
