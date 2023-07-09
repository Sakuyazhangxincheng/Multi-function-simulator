package com.example.myapplication.util;

import static androidx.core.view.KeyEventDispatcher.dispatchKeyEvent;

import android.view.KeyEvent;
import java.util.Arrays;
/**
 * 功能：
 * 作者：houzhuo
 * 日期：07月+05日
 */
public  class KeyboardOpt {

    public KeyboardOpt(){
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

        Integer code = KeyboardHelp.keyMap.get(value);
        return code;

    }
}
