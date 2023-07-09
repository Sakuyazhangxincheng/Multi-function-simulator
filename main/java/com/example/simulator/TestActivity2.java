package com.example.simulator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity2 extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private List<String> deviceList;
    private Map<String,String> deviceIpMap;
    private Handler handler = new Handler();
    private final Object lock = new Object();
    private int flag = 0;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                try {
                    synchronized (lock) {
                        deviceList.clear();
                        updateDeviceList();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_2);

        ListView listView = findViewById(R.id.listView2);
        Button scanButton = findViewById(R.id.scanButton2);

        // 注册 BroadcastReceiver
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        // 更新设备列表
        try {
            updateDeviceList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 初始化列表适配器和WiFi列表
        deviceList = new ArrayList<>();
        deviceIpMap = new HashMap<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deviceList);
        listView.setAdapter(adapter);

        // 扫描按钮点击事件
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateDeviceList();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // 点击列表项连接到选定的WiFi网络
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 获取点击设备的名称
                String deviceName = (String) parent.getItemAtPosition(position);
                // 获取该设备的IP地址
                String ipAddress = deviceIpMap.get(deviceName);
                // 执行你希望的操作，例如显示弹窗、日志输出等
                Toast.makeText(TestActivity2.this, "设备 " + deviceName + " 的IP地址是 " + ipAddress, Toast.LENGTH_SHORT).show();
                startWifiHidCommunication(ipAddress);
                if(flag == 1){
                    Toast.makeText(TestActivity2.this, "flag = 1", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(TestActivity2.this, "flag = 0", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消注册 BroadcastReceiver
        unregisterReceiver(receiver);
    }

    private void updateDeviceList() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 在此处执行网络请求和其他耗时操作
                ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ipAddress = wifiInfo.getIpAddress();
                String localIPAddress = formatIpAddress(ipAddress);
                String[] localIPParts = localIPAddress.split("\\.");

                for (int i = 1; i <= 254; i++) {
                    String targetIP = localIPParts[0] + "." + localIPParts[1] + "." + localIPParts[2] + "." + i;
                    try {
                        InetAddress inetAddress = InetAddress.getByName(targetIP);
                        if (inetAddress.isReachable(3000)) {
                            // 找到可达的设备，获取设备信息
                            String deviceName = inetAddress.getHostName();
                            String deviceIP = inetAddress.getHostAddress();
                            // 处理设备信息...
                            deviceList.add(deviceName);
                            deviceIpMap.put(deviceName, deviceIP);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                // 更新 UI
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 更新 UI 的操作
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private String formatIpAddress(int ipAddress) {
        return (ipAddress & 0xFF) + "." +
                ((ipAddress >> 8) & 0xFF) + "." +
                ((ipAddress >> 16) & 0xFF) + "." +
                (ipAddress >> 24 & 0xFF);
    }

    // 测试连接
    private static final int PORT = 8888; // 设置一个自定义的端口号
    private class WifiHidThread extends Thread {
        private Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;
        private String ip;

        @Override
        public void run() {
            try {
                flag = 1;

                InetAddress serverAddress = InetAddress.getByName(ip);
                socket = new Socket(serverAddress, PORT);

                // 获取输入流和输出流
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                // 在此处处理与WiFi HID设备的通信
                // 可以使用InputStream和OutputStream来读写数据
                int t = 0;
                while (t < 10) {
                    outputStream.write(0x41);
                    outputStream.flush();
                    Toast.makeText(TestActivity2.this, "第" + t + "次", Toast.LENGTH_SHORT).show();
                    t++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 在某个地方启动WiFi HID通信线程
    private void startWifiHidCommunication(String ip) {
        WifiHidThread thread = new WifiHidThread();
        thread.ip = ip;
        thread.start();
    }

    private void closeCommunication(WifiHidThread thread) {
        try {
            if (thread.inputStream != null) {
                thread.inputStream.close();
            }
            if (thread.outputStream != null) {
                thread.outputStream.close();
            }
            if (thread.socket != null) {
                thread.socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
