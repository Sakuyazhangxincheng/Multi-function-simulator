package com.android.superli.btremote.ui.adapter;

import android.content.Context;
import android.view.View;

import com.android.base.adapter.SingleAdapter;
import com.android.base.adapter.SuperViewHolder;
import com.android.superli.btremote.R;

public class FqsAdpter extends SingleAdapter<String> {
    public FqsAdpter(Context context) {
        super(context, R.layout.item_fqs);
    }

    @Override
    protected void bindData(SuperViewHolder holder, String item, int position) {
        super.bindData(holder, item, position);
        holder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener!=null){
                    mOnClickListener.onClick(item);
                }

            }
        });
    }

    private OnClickListener mOnClickListener;

    public void setmOnClickListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public interface OnClickListener{
        void onClick(String item);
    }
}
