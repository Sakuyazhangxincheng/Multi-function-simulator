package com.example.jiemian.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiemian.R;
import com.example.jiemian.UI.AddcaidanActivity;
import com.example.jiemian.Utils.utils1;
import com.example.jiemian.base.LazyFragment;
import com.example.jiemian.bean.Jishi;
import com.example.jiemian.bean.Kuaijie;

import java.util.List;

import butterknife.BindView;

public class KuaijieFragment extends LazyFragment {
    @BindView(R.id.listdata)
    ListView gvShow;
    Medicadapter medicadapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kuaijie;
    }

    @Override
    protected void loadData() {
        medicadapter=new  Medicadapter(getActivity(), utils1.goodStbList());
        gvShow.setAdapter(medicadapter);
    }

    class Medicadapter extends BaseAdapter {
        private Context context;
        private List<Kuaijie> listdata;
        public Medicadapter(Context context,List<Kuaijie>listdata){
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
                viewHolder=new  ViewHolder();
                convertView=LayoutInflater.from(getActivity()).inflate(R.layout.item_kuaijie,null);
                viewHolder.dandu=convertView.findViewById(R.id.dandu);
                viewHolder.touxiang=convertView.findViewById(R.id.touxiang);
                viewHolder.wujiaoxing=convertView.findViewById(R.id.wujiaoxing);
                viewHolder.tv_biaoti=convertView.findViewById(R.id.tv_biaoti);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= ( ViewHolder) convertView.getTag();
            }
            viewHolder.tv_biaoti.setText(listdata.get(position).getBiaoti());
            viewHolder.touxiang.setText(listdata.get(position).getTouxiang());
            viewHolder.wujiaoxing.setText(listdata.get(position).getWujiaoxing());
            viewHolder.dandu.setText(listdata.get(position).getDandu());
            return convertView;
        }

        class ViewHolder{
            TextView dandu,touxiang,wujiaoxing,tv_biaoti;
        }
    }

}