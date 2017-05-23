package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.application.EvaluationActivity;
import com.example.application.LssueActivity;
import com.example.application.R;

import java.util.List;

/**
 * Created by 90603 on 2017/5/22.
 */

public class MyLssAdapter extends BaseAdapter implements View.OnClickListener{
    //上下文
    private Context context;
    //数据项
    private List<String>data;
    public MyLssAdapter(List<String> data) {
        this.data =data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        
        return data.get(i);
    }

    @Override
    public long getItemId(int  i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       ViewHolder viewHolder = null;

        if(context == null)
            context =viewGroup.getContext();
        if (view  == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_prder_list_lss,null);
            viewHolder = new ViewHolder();
            viewHolder.call_me= (TextView) view.findViewById(R.id.call_me);
            viewHolder.order_time_me= (TextView) view.findViewById(R.id.order_time_me);
            viewHolder.price_me= (TextView) view.findViewById(R.id.price_me);
            viewHolder.complete_me= (TextView) view.findViewById(R.id.complete_me);
            viewHolder.me_delete_btn= (Button) view.findViewById(R.id.me_delete_btn);
            viewHolder.me_eva_btn= (Button) view.findViewById(R.id.me_eva_btn);
            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) view.getTag();
        //设置删除按钮tag标记
        viewHolder.me_delete_btn.setTag(R.id.btn);
        viewHolder.me_delete_btn.setTag(R.id.me_eva_btn);
        viewHolder.me_delete_btn.setOnClickListener(this);
        //设置评价按钮tag标记
        viewHolder.me_eva_btn.setTag(R.id.btn);
        viewHolder.me_eva_btn.setTag(R.id.me_eva_btn);
        viewHolder.me_eva_btn.setOnClickListener(this);
        //设置文本tag标记
        viewHolder.call_me.setText(data.get(i));
        viewHolder.call_me.setTag(R.id.tv);
        viewHolder.order_time_me.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.me_delete_btn:
                Toast.makeText(context,"点击删除按钮",Toast.LENGTH_SHORT).show();
                break;
            //评价按钮的跳转
            case  R.id.me_eva_btn:
                Intent intent = new Intent();
                intent.setClass(context,EvaluationActivity.class);
                context.startActivity(intent);
                break;
            //
            case  R.id.call_me:
                Toast.makeText(context,"我是文本",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    static class ViewHolder{
        TextView call_me;
        TextView order_time_me;
        TextView price_me;
        TextView complete_me;
        Button me_delete_btn;
        Button me_eva_btn;
    }
}
