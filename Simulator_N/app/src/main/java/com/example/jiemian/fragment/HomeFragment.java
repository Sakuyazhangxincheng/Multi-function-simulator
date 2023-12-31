package com.example.jiemian.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.jiemian.R;
import com.example.jiemian.UI.AddcaidanActivity;
import com.example.jiemian.UI.LoginActivity;
import com.example.jiemian.UI.MainActivity;
import com.example.jiemian.UI.ShebeiActivity;
import com.example.jiemian.base.LazyFragment;
import com.example.jiemian.bean.Jishi;
import com.example.jiemian.sqlite.JishiDbutils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;


public class HomeFragment extends LazyFragment {
    @BindView(R.id.gv)
    GridView gvShow;
    @BindView(R.id.shebei)
    LinearLayout shebei;

    Medicadapter medicadapter;
    Jishi jishi=new Jishi();
    Jishi jishi1=new Jishi();
    Jishi jishi2=new Jishi();
    Jishi jishi3=new Jishi();
    Jishi jishi4=new Jishi();
    Jishi jishi5=new Jishi();
    String isSave = "1";
    List<Jishi>list=new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void loadData() {

        //全局变量  取
        SharedPreferences preferences = getContext().getSharedPreferences("user",MODE_PRIVATE);
        String isSave = preferences.getString("name", "1");
        jishi5.setImage("https://img1.baidu.com/it/u=3242282073,1731117204&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500.jpg");
        jishi5.setTimu("自定义");
        jishi4.setImage("https://img1.baidu.com/it/u=631157378,2913566711&fm=253&fmt=auto&app=138&f=JPEG?w=256&h=256.jpg");
        jishi4.setTimu("游戏手柄");
        jishi3.setImage("https://img0.baidu.com/it/u=874767426,3247444924&fm=253&fmt=auto&app=138&f=PNG?w=500&h=500.jpg");
        jishi3.setTimu("PPT");
        jishi2.setImage("https://img2.baidu.com/it/u=2551651175,1063495489&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500.jpg");
        jishi2.setTimu("遥控器");
        jishi1.setImage("https://img0.baidu.com/it/u=2910013549,650001775&fm=253&fmt=auto&app=138&f=JPEG?w=705&h=500.jpg");
        jishi1.setTimu("鼠标");
        jishi.setImage("https://img1.baidu.com/it/u=2510052768,1377509185&fm=253&fmt=auto&app=138&f=JPEG?w=256&h=256.jpg");
        jishi.setTimu("键盘");





        if(isSave.equals("1")){
            JishiDbutils.getInstance(getActivity()).insert(jishi5);
            JishiDbutils.getInstance(getActivity()).insert(jishi4);
            JishiDbutils.getInstance(getActivity()).insert(jishi3);
            JishiDbutils.getInstance(getActivity()).insert(jishi2);
            JishiDbutils.getInstance(getActivity()).insert(jishi1);
            JishiDbutils.getInstance(getActivity()).insert(jishi);

            isSave="2";
            //全局变量  存
            SharedPreferences preferences1 = getActivity().getSharedPreferences("user",MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences1.edit();
            edit.putString("name",isSave);
            edit.commit();
        }
        list=JishiDbutils.getInstance(getActivity()).load();
        Collections.reverse(list);

        medicadapter=new Medicadapter(getActivity(), list);
        gvShow.setAdapter(medicadapter);

        shebei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ShebeiActivity.class));
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        list=JishiDbutils.getInstance(getActivity()).load();
        Collections.reverse(list);
        medicadapter=new Medicadapter(getActivity(),list);
        gvShow.setAdapter(medicadapter);
    }

    class Medicadapter extends BaseAdapter{
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
              convertView=LayoutInflater.from(getActivity()).inflate(R.layout.item_memo,null);
              viewHolder.iv_pic=convertView.findViewById(R.id.ivPic);
              viewHolder.tv_biaoti=convertView.findViewById(R.id.tv_biaoti);
              viewHolder.ll=convertView.findViewById(R.id.ll);
              convertView.setTag(viewHolder);
          }else {
              viewHolder= (ViewHolder) convertView.getTag();
          }
           viewHolder.tv_biaoti.setText(listdata.get(position).getTimu());
              Glide.with(getActivity()).load(listdata.get(position).getImage()).into(viewHolder.iv_pic);
              viewHolder.ll.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if (listdata.get(position).getTimu().equals("自定义")){
                          Intent intent=new Intent(getActivity(), AddcaidanActivity.class);
                          intent.putExtra("data",1);
                          startActivityForResult(intent,102);
                      }else if((listdata.get(position).getTimu().equals("游戏手柄"))){
                          Intent intent=new Intent(getActivity(), MainActivity.class);
                          startActivityForResult(intent,102);
                      }

                  }
              });


           return convertView;
       }

       class ViewHolder{
          ImageView iv_pic;
          TextView tv_biaoti;
          LinearLayout ll;
       }
   }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        list=JishiDbutils.getInstance(getActivity()).load();
        Collections.reverse(list);
        medicadapter=new Medicadapter(getActivity(),list);
        gvShow.setAdapter(medicadapter);
    }

}