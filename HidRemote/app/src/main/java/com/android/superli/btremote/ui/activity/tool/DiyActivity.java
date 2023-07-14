package com.android.superli.btremote.ui.activity.tool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import com.android.superli.btremote.R;
import com.android.superli.btremote.ui.activity.SelectActivity;

import java.util.ArrayList;

public class DiyActivity extends AppCompatActivity {


    Boolean isEdit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        float buttonX = -1, buttonY = -1;

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_diy);

        FrameLayout diyLayout = findViewById(R.id.diyLayout);
        Button editButton = findViewById(R.id.editButton);
        Button addButton = findViewById(R.id.addButton);
        addButton.setEnabled(false);
        addButton.setVisibility(View.GONE);

        //跳转事件
        Intent addIntent = new Intent(this, SelectActivity.class);

        //所有数据
        ArrayList<String> dataList= new ArrayList<String>();
        ArrayList<Float> xList = new ArrayList<Float>();
        ArrayList<Float> yList = new ArrayList<Float>();


        //获取select页面传回来的数据
        ArrayList<String> newDataList = this.getIntent().getStringArrayListExtra("dataList");
        float[] newXArray = this.getIntent().getFloatArrayExtra("xArray");
        float[] newYArray = this.getIntent().getFloatArrayExtra("yArray");
        System.out.println(211);
        if(newDataList!=null&&newXArray!=null&&newYArray!=null){
            //判断组件数据
            //传回来的数据赋值到本地
            dataList = newDataList;


            int flag = 0;
            for (String value: dataList) {
                if(value.equals("TouchPad")){

                }else if(value.equals("scrollBar")){

                }else{

                    Button button = new Button(this);
                    button.setText(value);//设置按钮文本
                    button.setPadding(10, 0, 10, 0);//内边距
                    button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);//字体大小
                    button.setMinimumWidth(0);//设置按钮最小默认大小
                    button.setMinimumHeight(0);//设置按钮最小默认大小
                    button.setX(newXArray[flag]);
                    button.setY(newYArray[flag]);
                    if(value.contains("\n")){
                        button.setTranslationY(-24f);
                    }//调整多行文本对齐方式

                    button.setOnClickListener(v -> {
                        System.out.println(((Button) v).getText().toString());
                        System.out.println(1);
                    });

                    int finalFlag = flag;
                    button.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            // 设置按钮可拖动
                            v.setOnTouchListener(new View.OnTouchListener() {
                                private float lastX, lastY;
                                boolean isFirst = true;

                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    switch (event.getAction()) {
//                                        case MotionEvent.ACTION_DOWN:
//                                            // 记录手指按下时的坐标
//                                            lastX = event.getRawX();
//                                            lastY = event.getRawY();
//                                            System.out.println(lastX);
//                                            break;
                                        case MotionEvent.ACTION_MOVE:
                                            if(isFirst){
                                                lastX = event.getRawX();
                                                lastY = event.getRawY();
                                                isFirst = false;
                                            }
                                            // 计算手指移动的距离
                                            float deltaX = event.getRawX() - lastX;
                                            float deltaY = event.getRawY() - lastY;

                                            // 更新按钮的位置
                                            v.setX(v.getX() + deltaX);
                                            v.setY(v.getY() + deltaY);

                                            // 更新lastX和lastY
                                            lastX = event.getRawX();
                                            lastY = event.getRawY();
                                            break;
                                        case MotionEvent.ACTION_UP:
                                            // 清除按钮的触摸事件监听器
                                            newXArray[finalFlag] = v.getX();
                                            newYArray[finalFlag] = v.getY();
                                            v.setOnTouchListener(null);
                                            break;
                                    }
                                    return true;
                                }
                            });
                            return true;
                        }
                    });

                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(200,200);
                    diyLayout.addView(button,params);

                }
                flag++;
            }
            //将数据传到select页面
            addIntent.putStringArrayListExtra("dataList", dataList);
            addIntent.putExtra("xArray",newXArray);
            addIntent.putExtra("yArray",newYArray);

        }else {
            //将数据传到select页面
            addIntent.putStringArrayListExtra("dataList", dataList);
        }



        //编辑按钮的绑定事件
        editButton.setOnClickListener(v -> {
            if(isEdit){
                isEdit = false;
                editButton.setText("编辑");
                addButton.setEnabled(false);
                addButton.setVisibility(View.GONE);
            }else {
                isEdit = true;
                editButton.setText("退出编辑");
                addButton.setEnabled(true);
                addButton.setVisibility(View.VISIBLE);
            }
        });

        //添加按钮的绑定事件
        addButton.setOnClickListener(v -> {

            startActivity(addIntent);
            finish();
        });



    }



}