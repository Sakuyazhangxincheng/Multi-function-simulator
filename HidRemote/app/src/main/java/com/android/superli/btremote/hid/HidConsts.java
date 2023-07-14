package com.android.superli.btremote.hid;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidDevice;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;

public class HidConsts {

    public final static String NAME = "app_remote";
    public final static String DESCRIPTION = "description";
    public final static String PROVIDER = "provider";
    public static BluetoothHidDevice HidDevice;
    public static BluetoothDevice BtDevice;
    public static byte ModifierByte = 0x00;
    public static byte KeyByte = 0x00;

    public final static byte[] Descriptor = {
            (byte) 0x05, (byte) 0x01, (byte) 0x09, (byte) 0x02, (byte) 0xa1, (byte) 0x01, (byte) 0x09, (byte) 0x01, (byte) 0xa1, (byte) 0x00,
            (byte) 0x85, (byte) 0x01, (byte) 0x05, (byte) 0x09, (byte) 0x19, (byte) 0x01, (byte) 0x29, (byte) 0x03, (byte) 0x15, (byte) 0x00, (byte) 0x25, (byte) 0x01,
            (byte) 0x95, (byte) 0x03, (byte) 0x75, (byte) 0x01, (byte) 0x81, (byte) 0x02, (byte) 0x95, (byte) 0x01, (byte) 0x75, (byte) 0x05, (byte) 0x81, (byte) 0x03,
            (byte) 0x05, (byte) 0x01, (byte) 0x09, (byte) 0x30, (byte) 0x09, (byte) 0x31, (byte) 0x09, (byte) 0x38, (byte) 0x15, (byte) 0x81, (byte) 0x25, (byte) 0x7f,
            (byte) 0x75, (byte) 0x08, (byte) 0x95, (byte) 0x03, (byte) 0x81, (byte) 0x06, (byte) 0xc0, (byte) 0xc0, (byte) 0x05, (byte) 0x01, (byte) 0x09, (byte) 0x06,
            (byte) 0xa1, (byte) 0x01, (byte) 0x85, (byte) 0x02, (byte) 0x05, (byte) 0x07, (byte) 0x19, (byte) 0xE0, (byte) 0x29, (byte) 0xE7, (byte) 0x15, (byte) 0x00,
            (byte) 0x25, (byte) 0x01, (byte) 0x75, (byte) 0x01, (byte) 0x95, (byte) 0x08, (byte) 0x81, (byte) 0x02, (byte) 0x95, (byte) 0x01, (byte) 0x75, (byte) 0x08,
            (byte) 0x15, (byte) 0x00, (byte) 0x25, (byte) 0x65, (byte) 0x19, (byte) 0x00, (byte) 0x29, (byte) 0x65, (byte) 0x81, (byte) 0x00, (byte) 0x05, (byte) 0x08,
            (byte) 0x95, (byte) 0x05, (byte) 0x75, (byte) 0x01, (byte) 0x19, (byte) 0x01, (byte) 0x29, (byte) 0x05,
            (byte) 0x91, (byte) 0x02, (byte) 0x95, (byte) 0x01, (byte) 0x75, (byte) 0x03, (byte) 0x91, (byte) 0x03,
            (byte) 0xc0
    };

    public final static byte[] C_Descriptor = {
            (byte)0x05, (byte)0x01, // Usage Page (Generic Desktop Ctrls)
            (byte)0x09, (byte)0x05, // Usage (Game Pad)
            (byte)0xA1, (byte)0x01, // Collection (Application)
            (byte)0x85, (byte)0x04, // Report ID (4)
            (byte)0x05, (byte)0x09, // Usage Page (Button)
            (byte)0x19, (byte)0x01, // Usage Minimum (Button 1)
            (byte)0x29, (byte)0x10, // Usage Maximum (Button 16)
            (byte)0x15, (byte)0x00, // Logical Minimum (0)
            (byte)0x25, (byte)0x01, // Logical Maximum (1)
            (byte)0x75, (byte)0x01, // Report Size (1)
            (byte)0x95, (byte)0x10, // Report Count (16)
            (byte)0x81, (byte)0x02, // Input (Data,Var,Abs,No Wrap,Linear,Preferred State,No Null Position)
            (byte)0x05, (byte)0x01, // Usage Page (Generic Desktop Ctrls)
            (byte)0x15, (byte)0x81, // Logical Minimum (-127)
            (byte)0x25, (byte)0x7F, // Logical Maximum (127)
            (byte)0x09, (byte)0x30, // Usage (X)
            (byte)0x09, (byte)0x31, // Usage (Y)
            (byte)0x09, (byte)0x32, // Usage (Z)
            (byte)0x09, (byte)0x35, // Usage (Rz)
            (byte)0x75, (byte)0x08, // Report Size (8)
            (byte)0x95, (byte)0x04, // Report Count (4)
            (byte)0x81, (byte)0x02, // Input (Data,Var,Abs,No Wrap,Linear,Preferred State,No Null Position)
            (byte)0xC0, // End Col
    };

    private static Handler handler;
    private static ExecutorService singleThreadExecutor;

    public static void reportTrans() {
        singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler = new Handler(Looper.myLooper()) {

                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        HidReport mHidReport = (HidReport) msg.obj;
                        postReport(mHidReport);
                    }
                };
                Looper.loop();
            }
        });
    }

    private static void postReport(HidReport report) {
        if ( Build.VERSION.SDK_INT<Build.VERSION_CODES.P) {
            return;
        }
        report.SendState = HidReport.State.Sending;
        Log.e("postReport", "ID:" + report.ReportId + "\t\tDATA:" + BytesUtils.toHexStringForLog(report.ReportData));
        boolean ret = HidDevice.sendReport(BtDevice, report.ReportId, report.ReportData);
        if (!ret) {
            report.SendState = HidReport.State.Failded;
        } else {
            report.SendState = HidReport.State.Sended;
        }
    }

    public static void exit() {
        if (handler != null) {
            handler.getLooper().quit();
            handler = null;
        }

        if (singleThreadExecutor != null && !singleThreadExecutor.isShutdown()) {
            singleThreadExecutor.shutdown();
            singleThreadExecutor = null;
        }
    }

    public static void CleanKbd() {
        SendKeyReport(new byte[]{0, 0});
    }

    protected static void addInputReport(final HidReport inputReport) {
        if (handler == null || singleThreadExecutor == null) {
            reportTrans();
        }
        if (inputReport != null && handler != null) {
            Message msg = new Message();
            msg.obj = inputReport;
            handler.sendMessage(msg);
        }
    }


    private static HidReport MouseReport = new HidReport(HidReport.DeviceType.Mouse, (byte) 0x01, new byte[]{0, 0, 0, 0});
    private static HidReport ControllerReport = new HidReport(HidReport.DeviceType.Controller, (byte) 0x04, new byte[]{0, 0, 0, 0, 0, 0});
    //1:btn1-8(XY AB)   2:btn2-16   3:X方向   4:Y方向    5:Z方向    6:Rz方向

    public static void XBtnDown(){
        HidConsts.ControllerReport.ReportData[0] |= 1;
        SendControllerReport(HidConsts.ControllerReport.ReportData);
    }
    public static void XBtnUp(){
        HidConsts.ControllerReport.ReportData[0] &= (~1);
        SendControllerReport(HidConsts.ControllerReport.ReportData);
    }
    public static void YBtnDown(){
        HidConsts.ControllerReport.ReportData[0] |= 2;
        SendControllerReport(HidConsts.ControllerReport.ReportData);
    }
    public static void YBtnUp(){
        HidConsts.ControllerReport.ReportData[0] &= (~2);
        SendControllerReport(HidConsts.ControllerReport.ReportData);
    }
    public static void ABtnDown(){
        HidConsts.ControllerReport.ReportData[0] |= 4;
        SendControllerReport(HidConsts.ControllerReport.ReportData);
    }
    public static void ABtnUp(){
        HidConsts.ControllerReport.ReportData[0] &= (~4);
        SendControllerReport(HidConsts.ControllerReport.ReportData);
    }
    public static void BBtnDown(){
        HidConsts.ControllerReport.ReportData[0] |= 8;
        SendControllerReport(HidConsts.ControllerReport.ReportData);
    }
    public static void BBtnUp(){
        HidConsts.ControllerReport.ReportData[0] &= (~8);
        SendControllerReport(HidConsts.ControllerReport.ReportData);
    }
    public static void RSInfo(double X, double Y, double Z, double Rz){
        double T = 1.0;
        if(ControllerReport.SendState.equals(HidReport.State.Sending)){
            return;
        }
        if(X != 0) X = X / T * 127;
        if(Y != 0) Y = Y / T * 127;
        if(Z != 0) Z = Z / T * 127;
        if(Rz != 0) Rz = Rz / T * 127;
        ControllerReport.ReportData[2] = (byte)X;
        ControllerReport.ReportData[3] = (byte)Y;
        ControllerReport.ReportData[4] = (byte)Z;
        ControllerReport.ReportData[5] = (byte)Rz;

        addInputReport(ControllerReport);
    }
    public static void MouseMove(int dx, int dy, int wheel, final boolean leftButton, final boolean rightButton, final boolean middleButton) {
        if (MouseReport.SendState.equals(HidReport.State.Sending)) {
            return;
        }
        if (dx > 127) dx = 127;
        if (dx < -127) dx = -127;
        if (dy > 127) dy = 127;
        if (dy < -127) dy = -127;
        if (wheel > 127) wheel = 127;
        if (wheel < -127) wheel = -127;
        if (leftButton) {
            MouseReport.ReportData[0] |= 1;
        } else {
            MouseReport.ReportData[0] = (byte) (MouseReport.ReportData[0] & (~1));
        }
        if (rightButton) {
            MouseReport.ReportData[0] |= 2;
        } else {
            MouseReport.ReportData[0] = (byte) (MouseReport.ReportData[0] & (~2));
        }
        if (middleButton) {
            MouseReport.ReportData[0] |= 4;
        } else {
            MouseReport.ReportData[0] = (byte) (MouseReport.ReportData[0] & (~4));
        }
        MouseReport.ReportData[1] = (byte) dx;
        MouseReport.ReportData[2] = (byte) dy;
        MouseReport.ReportData[3] = (byte) wheel;

        addInputReport(MouseReport);
    }

    public static void LeftBtnDown() {
        HidConsts.MouseReport.ReportData[0] |= 1;
        SendMouseReport(HidConsts.MouseReport.ReportData);
    }

    public static void LeftBtnUp() {
        HidConsts.MouseReport.ReportData[0] &= (~1);
        SendMouseReport(HidConsts.MouseReport.ReportData);
    }

    public static void LeftBtnClick() {
        LeftBtnDown();
        UtilCls.DelayTask(new Runnable() {
            @Override
            public void run() {
                LeftBtnUp();
            }
        }, 20, true);
    }

    public static TimerTask LeftBtnClickAsync(int delay) {
        return UtilCls.DelayTask(new Runnable() {
            @Override
            public void run() {
                LeftBtnClick();
            }
        }, delay, true);
    }

    public static void RightBtnDown() {
        HidConsts.MouseReport.ReportData[0] |= 2;
        SendMouseReport(HidConsts.MouseReport.ReportData);
    }

    public static void RightBtnUp() {
        HidConsts.MouseReport.ReportData[0] &= (~2);
        SendMouseReport(HidConsts.MouseReport.ReportData);
    }

    public static void MidBtnDown() {
        HidConsts.MouseReport.ReportData[0] |= 4;
        SendMouseReport(HidConsts.MouseReport.ReportData);
    }

    public static void MidBtnUp() {
        HidConsts.MouseReport.ReportData[0] &= (~4);
        SendMouseReport(HidConsts.MouseReport.ReportData);
    }

    public static byte ModifierDown(byte UsageId) {
        synchronized (HidConsts.class) {
            ModifierByte |= UsageId;
        }
        return ModifierByte;
    }

    public static byte ModifierUp(byte UsageId) {
        UsageId = (byte) (~((byte) (UsageId)));
        synchronized (HidConsts.class) {
            ModifierByte = (byte) (ModifierByte & UsageId);
        }
        return ModifierByte;
    }

    public static void KbdKeyDown(String usageStr) {
        if (!TextUtils.isEmpty(usageStr)) {
            if (usageStr.startsWith("M")) {
                usageStr = usageStr.replace("M", "");
                synchronized (HidConsts.class) {
                    byte mod = ModifierDown((byte) Integer.parseInt(usageStr));
                    SendKeyReport(new byte[]{mod, KeyByte});
                }
            } else {
                byte key = (byte) Integer.parseInt(usageStr);
                synchronized (HidConsts.class) {
                    KeyByte = key;
                    SendKeyReport(new byte[]{ModifierByte, KeyByte});
                }
            }
        }
    }

    public static void KbdKeyUp(String usageStr) {
        if (!TextUtils.isEmpty(usageStr)) {
            if (usageStr.startsWith("M")) {
                usageStr = usageStr.replace("M", "");
                synchronized (HidConsts.class) {
                    byte mod = ModifierUp((byte) Integer.parseInt(usageStr));
                    SendKeyReport(new byte[]{mod, KeyByte});
                }
            } else {
                synchronized (HidConsts.class) {
                    KeyByte = 0;
                    SendKeyReport(new byte[]{ModifierByte, KeyByte});
                }
            }
        }
    }

    public static void SendKeyReport(byte[] reportData) {
        HidReport report = new HidReport(HidReport.DeviceType.Keyboard, (byte) 0x02, reportData);
        addInputReport(report);
    }
    public static void SendMouseReport(byte[] reportData) {
        HidReport report = new HidReport(HidReport.DeviceType.Mouse, (byte) 0x01, reportData);
        addInputReport(report);
    }
    public static void SendControllerReport(byte[] reportData){
        HidReport report = new HidReport(HidReport.DeviceType.Controller, (byte) 0x04, reportData);
        addInputReport(report);
    }
}
