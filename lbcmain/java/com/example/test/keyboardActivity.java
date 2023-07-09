package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.Objects;

public class keyboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        int ColumnCount = 14;
        GridLayout keyboardGrid = findViewById(R.id.keyboardGrid);
        keyboardGrid.setColumnCount(ColumnCount);
        keyboardGrid.setRowCount(6);
        // 创建一个二维数组来表示键盘按键布局
        String[][] keyboardLayout = {
                {"Esc","F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12"},
                {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "=", "Backspace"},
                {"Tab", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "[", "]", "\\"},
                {"Caps", "A", "S", "D", "F", "G", "H", "J", "K", "L", ";", "'", "Enter"},
                {"Shift", "Z", "X", "C", "V", "B", "N", "M", ",", ".", "/", "Shift"},
                {"Ctrl", "Fn", "Win", "Alt", "Space", "Alt", "Ctrl"}
        };

        getSupportActionBar().hide();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;



        //遍历数组
        int[] perRow = {13,13,14,13,12,7};
        int sum = 0,row = 0;
        for (String[] keyValues: keyboardLayout){

            // 计算按钮的缩放比例
            float buttonWidthRatio = (0.07f);  // 按钮宽度占屏幕宽度的比例
            float buttonHeightRatio = 0.15f;  // 按钮高度占屏幕高度的比例

            // 根据屏幕尺寸和比例计算按钮的宽度和高度
            int buttonWidth = (int) (screenWidth * buttonWidthRatio);
            int buttonHeight = (int) (screenHeight * buttonHeightRatio);

            for(String keyValue: keyValues){

                Button button = new Button(this);
                button.setText(keyValue);
                button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);//字体大小
                button.setMinimumWidth(1);
                button.setMinimumHeight(1);
                button.setWidth(buttonWidth);
                button.setHeight(buttonHeight);
                button.setOnClickListener(v -> {
                    // 处理键盘按钮点击事件
                    System.out.println(((Button) v).getText().toString());
                    System.out.println(1);
                });
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                /*params.rowSpec = GridLayout.spec(col,1f);
                // 第一个0代表第0行
                // 第二个1代表占了多少行
                // 第三个3f代表权重是多少
                params.columnSpec = GridLayout.spec(row, 1f);*/
                int temp = 0;
                for(int i = 0;i<row;i++){
                    temp += perRow[i];
                }
                if(Objects.equals(keyboardLayout[row][sum - temp], "Space")){
                    params.rowSpec = GridLayout.spec(row);  // 每行16个按钮
                    params.columnSpec = GridLayout.spec(sum - temp,3);  // 每列一个按钮
                }else {
                    params.rowSpec = GridLayout.spec(row);  // 每行16个按钮
                    params.columnSpec = GridLayout.spec(sum - temp);  // 每列一个按钮
                }
                keyboardGrid.addView(button, params);
                sum++;
            }
            row++;
        }
    }
}