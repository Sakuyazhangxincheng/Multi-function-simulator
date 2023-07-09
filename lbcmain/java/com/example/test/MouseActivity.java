package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MouseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouse);

        Button btnLeftClick = findViewById(R.id.btnLeftClick);
        Button btnMiddleClick = findViewById(R.id.btnMiddleClick);
        Button btnRightClick = findViewById(R.id.btnRightClick);
        FrameLayout touchpad = findViewById(R.id.touchpad);

        btnLeftClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在左键按钮点击事件中调用鼠标模拟的方法
                int x = (int) v.getX();
                int y = (int) v.getY();
                MouseSimulator.simulateMouseClick(v.getContext(), x, y);
            }
        });

        btnMiddleClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在中键按钮点击事件中调用鼠标模拟的方法
                int x = (int) v.getX();
                int y = (int) v.getY();
                MouseSimulator.simulateMouseClick(v.getContext(), x, y);
            }
        });

        btnRightClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在右键按钮点击事件中调用鼠标模拟的方法
                int x = (int) v.getX();
                int y = (int) v.getY();
                MouseSimulator.simulateMouseClick(v.getContext(), x, y);
            }
        });

        touchpad.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 触摸板按下事件
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 触摸板移动事件
                        break;
                    case MotionEvent.ACTION_UP:
                        // 触摸板抬起事件
                        break;
                }

                return true;
            }
        });
    }
}