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
            (byte)0xa1, (byte)0x01, // Collection (Application)
            (byte)0xa1, (byte)0x00, // Collection (Physical)
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
            (byte)0x25, (byte)0x7f, // Logical Maximum (127)
            (byte)0x09, (byte)0x30, // Usage (X)
            (byte)0x09, (byte)0x31, // Usage (Y)
            (byte)0x09, (byte)0x32, // Usage (Z)
            (byte)0x09, (byte)0x35, // Usage (Rz)
            (byte)0x75, (byte)0x08, // Report Size (8)
            (byte)0x95, (byte)0x04, // Report Count (4)
            (byte)0x81, (byte)0x02, // Input (Data,Var,Abs,No Wrap,Linear,Preferred State,No Null Position)
            (byte)0xc0, // End Col
            (byte)0xc0, // End Col
    };

    public final static byte[] C_Descriptor_Xbox = {
            (byte)0x05, (byte)0x01,                     //0       GLOBAL_USAGE_PAGE(Generic Desktop Controls)
            (byte)0x09, (byte)0x05,                     //2       LOCAL_USAGE(Game Pad)
            (byte)0xA1, (byte)0x01,                     //4       MAIN_COLLECTION(Applicatior)
            (byte)0xA1, (byte)0x00,                     //6       MAIN_COLLECTION(Physical)
            (byte)0x09, (byte)0x30,                     //8       LOCAL_USAGE(X)
            (byte)0x09, (byte)0x31,                     //10      LOCAL_USAGE(Y)
            (byte)0x15, (byte)0x00,                     //12      GLOBAL_LOGICAL_MINIMUM(0)
            (byte)0x26, (byte)0xFF, (byte)0xFF,         //14      GLOBAL_LOCAL_MAXIMUM(-1)
            (byte)0x35, (byte)0x00,                     //17      GLOBAL_PHYSICAL_MINIMUM(0)
            (byte)0x46, (byte)0xFF, (byte)0xFF,         //19      GLOBAL_PHYSICAL_MAXIMUM(65535)
            (byte)0x95, (byte)0x02,                     //22      GLOBAL_REPORT_COUNT(2)
            (byte)0x75, (byte)0x10,                     //24      GLOBAL_REPORT_SIZE(16)
            (byte)0x81, (byte)0x02,                     //26      MAIN_INPUT(data var absolute NoWrap linear PreferredState NoNullPosition NonVolatile )
            (byte)0xC0,                                 //28      MAIN_COLLECTION_END
            (byte)0xA1, (byte)0x00,                     //29      MAIN_COLLECTION(Physical)
            (byte)0x09, (byte)0x33,                     //31      LOCAL_USAGE(Rx)
            (byte)0x09, (byte)0x34,                     //33      LOCAL_USAGE(Ry)
            (byte)0x15, (byte)0x00,                     //35      GLOBAL_LOGICAL_MINIMUM(0)
            (byte)0x26, (byte)0xFF, (byte)0xFF,         //37      GLOBAL_LOCAL_MAXIMUM(-1)
            (byte)0x35, (byte)0x00,                     //40      GLOBAL_PHYSICAL_MINIMUM(0)
            (byte)0x46, (byte)0xFF, (byte)0xFF,         //42      GLOBAL_PHYSICAL_MAXIMUM(65535)
            (byte)0x95, (byte)0x02,                     //45      GLOBAL_REPORT_COUNT(2)
            (byte)0x75, (byte)0x10,                     //47      GLOBAL_REPORT_SIZE(16)
            (byte)0x81, (byte)0x02,                     //49      MAIN_INPUT(data var absolute NoWrap linear PreferredState NoNullPosition NonVolatile )
            (byte)0xC0,                                 //51      MAIN_COLLECTION_END
            (byte)0xA1, (byte)0x00,                     //52      MAIN_COLLECTION(Physical)
            (byte)0x09, (byte)0x32,                     //54      LOCAL_USAGE(Z)
            (byte)0x15, (byte)0x00,                     //56      GLOBAL_LOGICAL_MINIMUM(0)
            (byte)0x26, (byte)0xFF, (byte)0xFF,         //58      GLOBAL_LOCAL_MAXIMUM(-1)
            (byte)0x35, (byte)0x00,                     //61      GLOBAL_PHYSICAL_MINIMUM(0)
            (byte)0x46, (byte)0xFF, (byte)0xFF,         //63      GLOBAL_PHYSICAL_MAXIMUM(65535)
            (byte)0x95, (byte)0x01,                     //66      GLOBAL_REPORT_COUNT(1)
            (byte)0x75, (byte)0x10,                     //68      GLOBAL_REPORT_SIZE(16)
            (byte)0x81, (byte)0x02,                     //70      MAIN_INPUT(data var absolute NoWrap linear PreferredState NoNullPosition NonVolatile )
            (byte)0xC0,                                 //72      MAIN_COLLECTION_END
            (byte)0x05, (byte)0x09,                     //73      GLOBAL_USAGE_PAGE(Button)
            (byte)0x19, (byte)0x01,                     //75      LOCAL_USAGE_MINIMUM(1)
            (byte)0x29, (byte)0x0A,                     //77      LOCAL_USAGE_MAXIMUM(10)
            (byte)0x95, (byte)0x0A,                     //79      GLOBAL_REPORT_COUNT(10)
            (byte)0x75, (byte)0x01,                     //81      GLOBAL_REPORT_SIZE(1)
            (byte)0x81, (byte)0x02,                     //83      MAIN_INPUT(data var absolute NoWrap linear PreferredState NoNullPosition NonVolatile )
            (byte)0x05, (byte)0x01,                     //85      GLOBAL_USAGE_PAGE(Generic Desktop Controls)
            (byte)0x09, (byte)0x39,                     //87      LOCAL_USAGE(Hat switch)
            (byte)0x15, (byte)0x01,                     //89      GLOBAL_LOGICAL_MINIMUM(1)
            (byte)0x25, (byte)0x08,                     //91      GLOBAL_LOCAL_MAXIMUM(8)
            (byte)0x35, (byte)0x00,                     //93      GLOBAL_PHYSICAL_MINIMUM(0)
            (byte)0x46, (byte)0x3B, (byte)0x10,         //95      GLOBAL_PHYSICAL_MAXIMUM(4155)
            (byte)0x66, (byte)0x0E, (byte)0x00,         //98      GLOBAL_REPORT_UNIT(14)
            (byte)0x75, (byte)0x04,                     //101     GLOBAL_REPORT_SIZE(4)
            (byte)0x95, (byte)0x01,                     //103     GLOBAL_REPORT_COUNT(1)
            (byte)0x81, (byte)0x42,                     //105     MAIN_INPUT(data var absolute NoWrap linear PreferredState NullState NonVolatile )
            (byte)0x75, (byte)0x02,                     //107     GLOBAL_REPORT_SIZE(2)
            (byte)0x95, (byte)0x01,                     //109     GLOBAL_REPORT_COUNT(1)
            (byte)0x81, (byte)0x03,                     //111     MAIN_INPUT(const var absolute NoWrap linear PreferredState NoNullPosition NonVolatile )
            (byte)0x75, (byte)0x08,                     //113     GLOBAL_REPORT_SIZE(8)
            (byte)0x95, (byte)0x02,                     //115     GLOBAL_REPORT_COUNT(2)
            (byte)0x81, (byte)0x03,                     //117     MAIN_INPUT(const var absolute NoWrap linear PreferredState NoNullPosition NonVolatile )
            (byte)0xC0,                                 //119     MAIN_COLLECTION_END
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
        HidConsts.ControllerReport.ReportData[0] |= 4;
        SendControllerReport(HidConsts.ControllerReport.ReportData);
    }
    public static void XBtnUp(){
        HidConsts.ControllerReport.ReportData[0] &= (~4);
        SendControllerReport(HidConsts.ControllerReport.ReportData);
    }
    public static void YBtnDown(){
        HidConsts.ControllerReport.ReportData[0] |= 8;
        SendControllerReport(HidConsts.ControllerReport.ReportData);
    }
    public static void YBtnUp(){
        HidConsts.ControllerReport.ReportData[0] &= (~8);
        SendControllerReport(HidConsts.ControllerReport.ReportData);
    }
    public static void ABtnDown(){
        HidConsts.ControllerReport.ReportData[0] |= 1;
        SendControllerReport(HidConsts.ControllerReport.ReportData);
    }
    public static void ABtnUp(){
        HidConsts.ControllerReport.ReportData[0] &= (~1);
        SendControllerReport(HidConsts.ControllerReport.ReportData);
    }
    public static void BBtnDown(){
        HidConsts.ControllerReport.ReportData[0] |= 2;
        SendControllerReport(HidConsts.ControllerReport.ReportData);
    }
    public static void BBtnUp(){
        HidConsts.ControllerReport.ReportData[0] &= (~2);
        SendControllerReport(HidConsts.ControllerReport.ReportData);
    }
    public static void RSInfo(double X, double Y, double Z, double Rz ,
                              final boolean X_flag, final boolean Y_flag,
                              final boolean A_flag, final boolean B_flag){
        double T = 230.0;
//        if(ControllerReport.SendState.equals(HidReport.State.Sending)){
//            return;
//        }
        if(X != 0) X = X / T * 127;
        if(Y != 0) Y = Y / T * 127;
        if(Z != 0) Z = Z / T * 127;
        if(Rz != 0) Rz = Rz / T * 127;
        if(X_flag){
            ControllerReport.ReportData[0] |= 4;
        }else {
            ControllerReport.ReportData[0] = (byte) (ControllerReport.ReportData[0] & (~1));
        }
        if(Y_flag){
            ControllerReport.ReportData[0] |= 8;
        }else {
            ControllerReport.ReportData[0] = (byte) (ControllerReport.ReportData[0] & (~2));
        }
        if(A_flag){
            ControllerReport.ReportData[0] |= 1;
        }else {
            ControllerReport.ReportData[0] = (byte) (ControllerReport.ReportData[0] & (~4));
        }
        if(B_flag){
            ControllerReport.ReportData[0] |= 2;
        }else {
            ControllerReport.ReportData[0] = (byte) (ControllerReport.ReportData[0] & (~8));
        }
        ControllerReport.ReportData[2] = (byte)X;
        ControllerReport.ReportData[3] = (byte)Y;
        ControllerReport.ReportData[4] = (byte)Z;
        ControllerReport.ReportData[5] = (byte)Rz;
        System.out.println("x:" + X + "y:" + Y);
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
