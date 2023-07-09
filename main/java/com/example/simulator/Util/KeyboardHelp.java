package com.example.myapplication.util;

import android.view.KeyEvent;

import java.util.HashMap;
import java.util.Map;
import com.google.common.collect.ImmutableMap;
/**
 * 功能：
 * 作者：houzhuo
 * 日期：07月+06日
 */
public class KeyboardHelp {
    public static final Map<Character, Integer> keyMap =
            new ImmutableMap.Builder<Character, Integer>()
                    .put('a', KeyEvent.KEYCODE_A)
                    .put('b', KeyEvent.KEYCODE_B)
                    .put('c', KeyEvent.KEYCODE_C)
                    .put('d', KeyEvent.KEYCODE_D)
                    .put('e', KeyEvent.KEYCODE_E)
                    .put('f', KeyEvent.KEYCODE_F)
                    .put('g', KeyEvent.KEYCODE_G)
                    .put('h', KeyEvent.KEYCODE_H)
                    .put('i', KeyEvent.KEYCODE_I)
                    .put('j', KeyEvent.KEYCODE_J)
                    .put('k', KeyEvent.KEYCODE_K)
                    .put('l', KeyEvent.KEYCODE_L)
                    .put('m', KeyEvent.KEYCODE_M)
                    .put('n', KeyEvent.KEYCODE_N)
                    .put('o', KeyEvent.KEYCODE_O)
                    .put('p', KeyEvent.KEYCODE_P)
                    .put('q', KeyEvent.KEYCODE_Q)
                    .put('r', KeyEvent.KEYCODE_R)
                    .put('s', KeyEvent.KEYCODE_S)
                    .put('t', KeyEvent.KEYCODE_T)
                    .put('u', KeyEvent.KEYCODE_U)
                    .put('v', KeyEvent.KEYCODE_V)
                    .put('w', KeyEvent.KEYCODE_W)
                    .put('x', KeyEvent.KEYCODE_X)
                    .put('y', KeyEvent.KEYCODE_Y)
                    .put('z', KeyEvent.KEYCODE_Z)
                    .put('1', KeyEvent.KEYCODE_1)
                    .put('2', KeyEvent.KEYCODE_2)
                    .put('3', KeyEvent.KEYCODE_3)
                    .put('4', KeyEvent.KEYCODE_4)
                    .put('5', KeyEvent.KEYCODE_5)
                    .put('6', KeyEvent.KEYCODE_6)
                    .put('7', KeyEvent.KEYCODE_7)
                    .put('8', KeyEvent.KEYCODE_8)
                    .put('9', KeyEvent.KEYCODE_9)
                    .put('0', KeyEvent.KEYCODE_0)
                  //  .put(' ', KeyEvent.KEYCODE_SPACE)
                    .put('-', KeyEvent.KEYCODE_MINUS)
                    .put('=', KeyEvent.KEYCODE_EQUALS)
                    .put('(', KeyEvent.KEYCODE_LEFT_BRACKET)
                    .put(')', KeyEvent.KEYCODE_RIGHT_BRACKET)
                    .put('+', KeyEvent.KEYCODE_PLUS)
                    .put('*', KeyEvent.KEYCODE_STAR)
                    .put(',', KeyEvent.KEYCODE_COMMA)
                    .put(';', KeyEvent.KEYCODE_SEMICOLON)
                    .put('@', KeyEvent.KEYCODE_AT)
                    .put('E', KeyEvent.KEYCODE_ENTER)
                    .put('S', KeyEvent.KEYCODE_SPACE)//Enter
                    .put('D',KeyEvent.KEYCODE_DEL)//Delete
                    .put('T', KeyEvent.KEYCODE_TAB)//TAB
                    .put('B', KeyEvent.KEYCODE_DEL)//backspace
                    .put('Z',KeyEvent.KEYCODE_SHIFT_LEFT)// Shift
                    .put('C',KeyEvent.KEYCODE_CTRL_LEFT)//CTRL
                    .build();

}
