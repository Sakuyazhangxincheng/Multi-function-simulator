package com.example.simulator;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.NetworkSpecifier;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSpecifier;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.simulator.Util.DialogUtil;
import com.example.simulator.thread.HidThread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class TestActivity extends AppCompatActivity {
    private static final int WIFI_PERMISSION_REQUEST_CODE = 1001;
    private WifiManager wifiManager;
    private ConnectivityManager.NetworkCallback networkCallback;
    private ConnectivityManager connectivityManager;
    private ArrayAdapter<String> arrayAdapter;
    private List<ScanResult> wifiList;
    private List<String> ssidList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ListView listView = findViewById(R.id.listView);
        Button scanButton = findViewById(R.id.scanButton);

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        checkWIFI();

        // 注册广播接收器以接收WiFi扫描结果
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        // 初始化列表适配器和WiFi列表
        ssidList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ssidList);
        listView.setAdapter(arrayAdapter);

        // 扫描按钮点击事件
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanWiFi();
            }
        });

        // 点击列表项连接到选定的WiFi网络
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                connectWiFi(position,wifiList);
                showPasswordDialog(arrayAdapter.getItem(position));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 检查请求码
        if (requestCode == WIFI_PERMISSION_REQUEST_CODE) {
            // 检查授权结果
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户授权了权限，进行相关操作
                // TODO: 在这里处理 WiFi 相关操作
                scanWiFi();
            } else {
                // 用户拒绝了权限请求，可以给出相应的提示或处理
                Toast.makeText(TestActivity.this, "未授予 WiFi 权限，无法进行相关操作", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销广播接收器
        unregisterReceiver(wifiReceiver);
    }

    private final BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                wifiList = wifiManager.getScanResults();
                ssidList.clear();

                for (ScanResult scanResult : wifiList) {
                    ssidList.add(scanResult.SSID);
                }
                arrayAdapter.notifyDataSetChanged();
            }
        }
    };

    // 检测WiFi权限
    private void checkWIFI(){
        // 检查并请求 WiFi 权限
        if (ContextCompat.checkSelfPermission(TestActivity.this, Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(TestActivity.this, Manifest.permission.CHANGE_WIFI_STATE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(TestActivity.this, Manifest.permission.ACCESS_NETWORK_STATE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(TestActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(TestActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(TestActivity.this, Manifest.permission.CHANGE_NETWORK_STATE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(TestActivity.this, Manifest.permission.WRITE_SETTINGS)
                        != PackageManager.PERMISSION_GRANTED) {

            // 请求权限，并传入权限数组和请求码
            ActivityCompat.requestPermissions(TestActivity.this,
                    new String[]{
                            Manifest.permission.ACCESS_WIFI_STATE,
                            Manifest.permission.CHANGE_WIFI_STATE,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    WIFI_PERMISSION_REQUEST_CODE);
        } else {
            // 已经拥有权限，进行相关操作
            // TODO: 在这里处理 WiFi 相关操作
            scanWiFi();
        }
    }

    // 连接WiFi
    private void connectWiFi(String ssid, String password){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            NetworkSpecifier specifier = new WifiNetworkSpecifier.Builder()
                    .setSsid(ssid)
                    .setWpa2Passphrase(password)
                    .build();

            NetworkRequest request = new NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .setNetworkSpecifier(specifier)
                    .build();

            networkCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(@NonNull Network network) {
                    // WiFi网络已连接成功
                    Toast.makeText(TestActivity.this, "已连接到WiFi网络：" + ssid, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onUnavailable() {
                    // 无法连接到WiFi网络
                    Toast.makeText(TestActivity.this, "连接失败，请检查密码和网络", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLost(@NonNull Network network) {
                    // WiFi网络已断开
                    Toast.makeText(TestActivity.this, "WiFi网络已断开", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
                    if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                        String networkSSID = wifiManager.getConnectionInfo().getSSID().replace("\"", ""); // 获取当前连接的WiFi网络的SSID
                        if (networkSSID.equals(ssid)) {
                            // 网络已连接并且是所需的WiFi网络
                            SupplicantState supplicantState = wifiManager.getConnectionInfo().getSupplicantState();
                            if (supplicantState == SupplicantState.COMPLETED || supplicantState == SupplicantState.DISCONNECTED) {
                                // WiFi连接已成功建立或已断开
                                connectivityManager.unregisterNetworkCallback(networkCallback);
                            }
                        }
                    }
                }
            };
            connectivityManager.requestNetwork(request, networkCallback);
        } else {
            // 对于Android Q之前的设备，可以继续使用旧的连接方法（使用WifiManager）
            Toast.makeText(TestActivity.this, "当前设备不支持通过此方式连接WiFi网络", Toast.LENGTH_SHORT).show();
        }
    }

    public  void connectWiFi2(String ssid, String password){
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", ssid);
        wifiConfig.preSharedKey = String.format("\"%s\"", password);

        int networkId = wifiManager.addNetwork(wifiConfig);
        wifiManager.disconnect();
        if (wifiManager.enableNetwork(networkId, true)) {
            wifiManager.reconnect();
            Toast.makeText(TestActivity.this, "已连接到WiFi网络：" + ssid, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "连接失败，请检查密码和网络设置", Toast.LENGTH_SHORT).show();
        }
}

    // 扫描WiFi
    private void scanWiFi(){
        // 检查 WiFi 是否已打开
        if (!wifiManager.isWifiEnabled()) {
            // 如果 WiFi 未打开，则打开 WiFi
            wifiManager.setWifiEnabled(true);
        }
        wifiManager.startScan();
//        Toast.makeText(activity, "正在扫描附近的WiFi网络...", Toast.LENGTH_LONG).show();
    }

    // 输入密码对话框
    private void showPasswordDialog(String ssid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_wifi_password, null);
        builder.setView(dialogView);

        // 获取对话框布局中的视图
        EditText passwordEditText = dialogView.findViewById(R.id.password_edit_text);
        Button connectButton = dialogView.findViewById(R.id.connect_button);

        AlertDialog dialog = builder.create();

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEditText.getText().toString();
                if (!password.isEmpty()) {
                    // 连接WiFi网络
                    connectWiFi(ssid, password);
                    dialog.dismiss();
                } else {
                    Toast.makeText(TestActivity.this, "请输入WiFi密码", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    // 获取加密方式
    private String getEncrypt(WifiManager mWifiManager, ScanResult scanResult) {
        if (mWifiManager != null) {
            String capabilities = scanResult.capabilities;
            if (!TextUtils.isEmpty(capabilities)) {
                if (capabilities.contains("WPA") || capabilities.contains("wpa")) {
                    return "WPA";
                } else if (capabilities.contains("WEP") || capabilities.contains("wep")) {
                    return "WEP";
                } else {
                    return "无密码";
                }
            }
        }
        return "获取失败";
    }

}
