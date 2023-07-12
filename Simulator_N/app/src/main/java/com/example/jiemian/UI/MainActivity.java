package com.example.jiemian.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jiemian.R;
import com.example.jiemian.Utils.RockerView;

public class MainActivity extends AppCompatActivity {
   ImageView close;
    RockerView rock;
   TextView tv_area;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
        close=findViewById(R.id.close);
        rock=findViewById(R.id.rock);
        tv_area=findViewById(R.id.tv_area);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_area.setText(String.valueOf(0));


         rock.setOnAngleChangeListener(new RockerView.OnAngleChangeListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void angle(double angle, double x, double y) {
                //计算斜边，由于是直角三角形，可知：勾股数组程a² + b² = c²
                //angle 移动角度 0~90 第一象限  90～180 第二象限 180～270 第三象限 270～360 第四象限

                //如果在原点
                if (angle ==0){
                    tv_area.setText(String.valueOf(0));
                }

                double c = Math.sqrt(x*x+y*y);
                tv_area.setText(String.valueOf(c));
            }


            @Override
            public void onFinish() {

            }
        });
    }
}