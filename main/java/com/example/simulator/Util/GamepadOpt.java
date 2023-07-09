package com.example.myapplication.util;

import android.view.KeyEvent;

/**
 * 功能：
 * 作者：houzhuo
 * 日期：07月+09日
 */
public class GamepadOpt {
    public GamepadOpt(){
    }

    public static final KeyEvent downEvent(String value){
        KeyEvent down = new KeyEvent(KeyEvent.ACTION_DOWN,getCode(value));
        return down;
    }
    public static KeyEvent upEvent(String value){
        KeyEvent up = new KeyEvent(KeyEvent.ACTION_UP,getCode(value));
        return up;
    }
    public static int getCode(String value){

        Integer code = GamepadHelp.keyMap.get(value);
        return code;

    }
}
