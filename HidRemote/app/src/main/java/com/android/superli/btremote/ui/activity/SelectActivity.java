package com.android.superli.btremote.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;

import com.android.superli.btremote.R;
import com.android.superli.btremote.ui.activity.tool.DiyActivity;

import java.util.ArrayList;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select);

        GridLayout selectLayout = findViewById(R.id.SelectLayout);
        selectLayout.setRowCount(21);
        selectLayout.setColumnCount(4);

        Boolean isEmpty = true;
        //坐标数据
        ArrayList<Float> xList = new ArrayList<Float>();
        ArrayList<Float> yList = new ArrayList<Float>();

        //跳转事件
        Intent addIntent = new Intent(this, DiyActivity.class);
        //addIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        // 从 Intent 中获取 ArrayList 数据
        ArrayList<String> dataList = this.getIntent().getStringArrayListExtra("dataList");
        float[] newXArray = this.getIntent().getFloatArrayExtra("xArray");
        float[] newYArray = this.getIntent().getFloatArrayExtra("yArray");
        if(dataList!=null&&newYArray!=null){

            for (float value : newXArray) {
                xList.add(value);
            }
            for (float value : newYArray) {
                yList.add(value);
            }
        }

        String[] allButton = {
                "Esc","F1", "F2", "F3",
                "F4", "F5", "F6", "F7",
                "F8", "F9", "F10", "F11",
                "F12","Del", "!\n1", "@\n2",
                "#\n3", "$\n4", "%\n5", "^\n6",
                "&\n7", "*\n8", "(\n9", ")\n0",
                "_\n-", "+\n=", "Backspace", "Tab",
                "Q", "W", "E", "R",
                "T", "Y", "U", "I",
                "O", "P", "{\n[", "{\n]",
                "|\n\\", "Caps", "A", "S",
                "D", "F", "G", "H",
                "J", "K", "L", ":\n;",
                "\"\n'", "Enter", "Shift", "Z",
                "X", "C", "V", "B",
                "N", "M", "<\n,",">\n.",
                "?\n/", "Shift", "Ctrl", "Fn",
                "Win", "Alt", "Space", "Alt",
                "Ctrl","mLeft","mMiddle","mRight",
                "scrollBar","TouchPad","voiceUp","voiceDown",
                "lightUp","lightDown"
        };

        for(String value:allButton){
            Button button = new Button(this);
            button.setText(value);//设置按钮文本
            button.setPadding(10, 0, 10, 0);//内边距
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);//字体大小
            button.setMinimumWidth(0);//设置按钮最小默认大小
            button.setMinimumHeight(0);//设置按钮最小默认大小
            if(value.contains("\n")){
                button.setTranslationY(-24f);
            }//调整多行文本对齐方式
            button.setOnClickListener(v -> {
                dataList.add(value);
                xList.add(0f);
                yList.add(0f);
                addIntent.putStringArrayListExtra("dataList",dataList);
                float[] xArray = new float[xList.size()];
                float[] yArray = new float[yList.size()];
                for (int i = 0; i < xList.size(); i++) {
                    xArray[i] = xList.get(i);
                }
                for (int i = 0; i < yList.size(); i++) {
                    yArray[i] = yList.get(i);
                }
                addIntent.putExtra("xArray",xArray);
                addIntent.putExtra("yArray",yArray);

                startActivity(addIntent);
                finish();
            });
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED,1);  // 每行16个按钮
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED,1);  // 每列一个按钮
            selectLayout.addView(button, params);
        }
    }
}