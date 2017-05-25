package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application.R;

import java.util.List;
import java.util.Map;

/**
 * Created by 90603 on 2017/5/23.
 */

public class MyAdapterBuy extends BaseAdapter implements View.OnClickListener{
    private Context buy;
    private List<Map<String , Object>>allVallues;
    public MyAdapterBuy(Context ctx, List<Map<String,Object>>allVallues){
        this.buy =ctx;
        this.allVallues=allVallues;
    }
    @Override
    //获取list产生多少条数据
    public int getCount() {
        return allVallues.size();
    }

    @Override
    //返回一行的数据
    public Object getItem(int position) {
        return allVallues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    //
    public View getView(int position, View convertView, ViewGroup parent) {
        /*ViewHolder viewHolder =null;*/
        if (buy == null)
            buy = parent.getContext();
        if (convertView == null){
            //如果当前行没有被创建出来  利用之前的布局文件将行创建
            convertView = LayoutInflater.from(buy).inflate(R.layout.my_prder_list_buy,null);
            /*viewHolder = new ViewHolder();*/
            //*//*viewHolder.meEva = (Button) convertView.findViewById(R.id.me_eva_btn);*//*
            /*viewHolder.Delete= (Button) convertView.findViewById(R.id.delete_btn);
            viewHolder.Eva=(Button)convertView.findViewById(R.id.reply_buy);
            convertView.setTag(viewHolder);*/
        }
        //获取viewHolder实例
        /*viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.Eva.setOnClickListener(this);
        viewHolder.Delete.setOnClickListener(this);*/
        //头像图片
        TextView head = (TextView) convertView.findViewById(R.id.head);
        //设置图片高度
        TextView call = (TextView) convertView.findViewById(R.id.call);
        TextView order_time = (TextView) convertView.findViewById(R.id.order_time);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        TextView complete = (TextView) convertView.findViewById(R.id.complete);

        Map<String,Object>map = allVallues.get(position);
        call.setText(map.get("name").toString());
        order_time.setText(map.get("time").toString());
        price.setText(map.get("grade").toString());
        complete.setText(map.get("state").toString());

        head.setBackgroundResource((int) map.get("img"));
        return convertView;
    }

    @Override
    public void onClick(View v) {
        /*switch (v.getId()){
            //评价按钮的跳转
            case R.id.reply_buy:
               // Toast.makeText(buy,,Toast.LENGTH_SHORT).show();
                break;
            //删除订单的按钮
            case R.id.me_delete_btn:

                break;
        }*/
    }
  /*  static class ViewHolder{
        Button Delete;
        Button Eva;
    }*/
}
