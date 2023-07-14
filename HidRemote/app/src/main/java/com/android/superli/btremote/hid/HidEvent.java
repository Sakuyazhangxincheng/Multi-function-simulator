package com.android.superli.btremote.hid;

public class HidEvent {

    public enum tcpType {
        onConnecting, onConnected, onDisConnected
    }

    public tcpType mtcpType;

    public HidEvent(tcpType mtcpType) {
        this.mtcpType = mtcpType;
    }
}
