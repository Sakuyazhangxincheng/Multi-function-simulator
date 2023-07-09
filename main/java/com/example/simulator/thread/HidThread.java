package com.example.simulator.thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class HidThread {
    private static final int PORT = 8888; // 设置一个自定义的端口号
    private class WifiHidThread extends Thread {
        private Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;
        private String ip;

        @Override
        public void run() {
            try {
                InetAddress serverAddress = InetAddress.getByName(ip); // 将服务器IP地址替换为实际的IP地址
                socket = new Socket(serverAddress, PORT);

                // 获取输入流和输出流
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                // 在此处处理与WiFi HID设备的通信
                // 可以使用InputStream和OutputStream来读写数据
                int t = 0;
                while (t < 100) {
                    outputStream.write(0x04);
                    outputStream.flush();
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
    public void startWifiHidCommunication(String ip) {
        WifiHidThread thread = new WifiHidThread();
        thread.ip = ip;
        thread.start();
    }

    public void closeCommunication(WifiHidThread thread) {
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
