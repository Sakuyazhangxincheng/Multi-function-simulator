package com.android.superli.btremote.ui.activity.tool;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.superli.btremote.R;
import com.android.superli.btremote.hid.HidConsts;
import com.android.superli.btremote.utils.RockerView;
import com.android.superli.btremote.utils.VibrateUtil;

public class ShoubingActivity extends AppCompatActivity {
    private boolean XUp = true; // X 抬起
    private boolean YUp = true; // Y 抬起
    private boolean AUp = true; // A 抬起
    private boolean BUp = true; // B 抬起
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoubing);

        ImageView close = findViewById(R.id.close);
        RockerView rock = findViewById(R.id.rock);
//        TextView tv_area = findViewById(R.id.tv_area);

        //X Y A B
        ImageView btn_X = findViewById(R.id.btn_X);
        ImageView btn_Y = findViewById(R.id.btn_Y);
        ImageView btn_A = findViewById(R.id.btn_A);
        ImageView btn_B = findViewById(R.id.btn_B);

        RelativeLayout rl_X = findViewById(R.id.zheng);
        RelativeLayout rl_Y = findViewById(R.id.san);
        RelativeLayout rl_A = findViewById(R.id.r_close);
        RelativeLayout rl_B = findViewById(R.id.yuan);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       btn_X.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {
               if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                   XUp = false;
                   HidConsts.XBtnDown();
                   rl_X.setBackgroundResource(R.drawable.shape_key_unsel_c5);
                   VibrateUtil.vibrate();
                   XUp = true;
                   HidConsts.XBtnUp();
                   rl_X.setBackgroundResource(R.drawable.btn_bg);
                   System.out.println(111);
               }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                   XUp = true;
                   HidConsts.XBtnUp();
                   rl_X.setBackgroundResource(R.drawable.btn_bg);
                   System.out.println(111);
               }
               return false;
           }
       });

        btn_Y.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    YUp = false;
                    HidConsts.YBtnDown();
                    rl_Y.setBackgroundResource(R.drawable.shape_key_unsel_c5);
                    VibrateUtil.vibrate();
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    YUp = true;
                    HidConsts.YBtnUp();
                    rl_Y.setBackgroundResource(R.drawable.btn_bg);
                }
                return false;
            }
        });

        btn_A.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    AUp = false;
                    HidConsts.ABtnDown();
                    rl_A.setBackgroundResource(R.drawable.shape_key_unsel_c5);
                    VibrateUtil.vibrate();
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    AUp = true;
                    HidConsts.ABtnUp();
                    rl_A.setBackgroundResource(R.drawable.btn_bg);
                }
                return false;
            }
        });

        btn_B.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    BUp = false;
                    HidConsts.BBtnDown();
                    rl_B.setBackgroundResource(R.drawable.shape_key_unsel_c5);
                    VibrateUtil.vibrate();
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    BUp = true;
                    HidConsts.BBtnUp();
                    rl_B.setBackgroundResource(R.drawable.btn_bg);
                }
                return false;
            }
        });

        //tv_area.setText(String.valueOf(0));
        rock.setOnAngleChangeListener(new RockerView.OnAngleChangeListener() {
            @Override
            public void onStart() {}

            @Override
            public void angle(double angle, double x, double y) {
                //计算斜边，由于是直角三角形，可知：勾股数组程a² + b² = c²
                //angle 移动角度 0~90 第一象限  90～180 第二象限 180～270 第三象限 270～360 第四象限
                double X1 = x-230;
                double Y1 = y-230;

                if(X1 > 230) X1 = 230;
                else if (X1 < -230) X1 = -230;

                if(Y1 > 230) Y1 = 230;
                else if (Y1 < -230) Y1 = -230;

                HidConsts.RSInfo(X1,Y1,0,0,!XUp,!YUp,!AUp,!BUp);
            }

            @Override
            public void onFinish() {}
        });
    }
}