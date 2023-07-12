package com.example.jiemian.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiemian.R;
import com.example.jiemian.bean.Jishi;
import com.example.jiemian.bean.User;
import com.example.jiemian.fragment.HomeFragment;
import com.example.jiemian.sqlite.JishiDbutils;
import com.example.jiemian.sqlite.SqliteDBUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShebeiActivity extends AppCompatActivity {
    @BindView(R.id.gv)
    GridView gvShow;
    Medicadapter medicadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shebei);
        ButterKnife.bind(this);
        medicadapter=new  Medicadapter(ShebeiActivity.this, JishiDbutils.getInstance(ShebeiActivity.this).load());
        gvShow.setAdapter(medicadapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        medicadapter=new  Medicadapter(ShebeiActivity.this, JishiDbutils.getInstance(ShebeiActivity.this).load());
        gvShow.setAdapter(medicadapter);
    }

    class Medicadapter extends BaseAdapter {
        private Context context;
        private List<Jishi>listdata;
        public Medicadapter(Context context,List<Jishi>listdata){
            this.context=context;
            this.listdata=listdata;
        }
        @Override
        public int getCount() {
            return listdata.size();
        }

        @Override
        public Object getItem(int position) {
            return listdata.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
             ViewHolder viewHolder=null;
            if(convertView==null){
                viewHolder=new ViewHolder();
                convertView= LayoutInflater.from(ShebeiActivity.this).inflate(R.layout.item_she,null);
                viewHolder.iv_pic=convertView.findViewById(R.id.tupian);
                viewHolder.tv_biaoti=convertView.findViewById(R.id.biaoti);
                viewHolder.ll=convertView.findViewById(R.id.ll);
                delete_pic = convertView.findViewById(R.id.shanchu);
                modify_pic = convertView.findViewById(R.id.xiugai);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= ( ViewHolder) convertView.getTag();
            }
            viewHolder.tv_biaoti.setText(listdata.get(position).getTimu());
            Glide.with(ShebeiActivity.this).load(listdata.get(position).getImage()).into(viewHolder.iv_pic);
            delete_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    change(listdata.get(position));
                }
            });


            return convertView;
        }

        class ViewHolder{
            ImageView iv_pic;
            TextView tv_biaoti;
            LinearLayout ll;
        }

        ImageView delete_pic;
        ImageView modify_pic;
    }

    private void change(Jishi user) {
        AlertDialog.Builder builder=new AlertDialog.Builder(ShebeiActivity.this);
        builder.setTitle("提示")
                .setMessage("确定删除吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JishiDbutils.getInstance(ShebeiActivity.this).delete(ShebeiActivity.this,user.getId()+"");
                        medicadapter=new  Medicadapter(ShebeiActivity.this, JishiDbutils.getInstance(ShebeiActivity.this).load());
                        gvShow.setAdapter(medicadapter);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
}