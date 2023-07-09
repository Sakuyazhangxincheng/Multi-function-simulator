package com.example.myapplication.util;

import android.view.KeyEvent;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * 功能：
 * 作者：houzhuo
 * 日期：07月+09日
 */
public class GamepadHelp {
    public static final Map<Character, Integer> keyMap =
            new ImmutableMap.Builder<Character, Integer>()
                    .put('a', KeyEvent.KEYCODE_BUTTON_A)//A
                    .put('b', KeyEvent.KEYCODE_BUTTON_B)//B
                    .put('x', KeyEvent.KEYCODE_BUTTON_X)//X
                    .put('y', KeyEvent.KEYCODE_BUTTON_Y)//Y
                    .put('l', KeyEvent.KEYCODE_BUTTON_A)//L1
                    .put('r', KeyEvent.KEYCODE_BUTTON_A)//R1
                    .put('L', KeyEvent.KEYCODE_BUTTON_A)//L2
                    .put('R', KeyEvent.KEYCODE_BUTTON_A)//R2
                    .put('2', KeyEvent.KEYCODE_BUTTON_THUMBL)//左拇指键
                    .put('4', KeyEvent.KEYCODE_BUTTON_THUMBR)//右拇指键
                    .put('1', KeyEvent.KEYCODE_BUTTON_START)//Start
                    .put('W', KeyEvent.KEYCODE_DPAD_UP)//上
                    .put('S', KeyEvent.KEYCODE_DPAD_DOWN)//下
                    .put('A', KeyEvent.KEYCODE_DPAD_LEFT)//左
                    .put('D', KeyEvent.KEYCODE_DPAD_RIGHT)//右
                    .put('c', KeyEvent.KEYCODE_DPAD_CENTER)//中心
                    .put('0', KeyEvent.KEYCODE_BACK)//返回键
                    .put('M', KeyEvent.KEYCODE_MENU)//菜单键
                    .build();
}
