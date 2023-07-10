package com.example.simulator.Util;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class DialogUtil {
    public static void showDialog(Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setMessage(msg)
                .setPositiveButton("确认", ((dialog, which) -> {
                    dialog.dismiss();
                }))
                .create()
                .show();
    }
}
