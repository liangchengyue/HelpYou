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

import com.example.application.EvaluationActivity;
import com.example.application.R;
import com.example.util.Globals;

import java.util.List;
import java.util.Map;

/**
 * Created by 90603 on 2017/5/23.
 */

public class MyAdapterLssue extends BaseAdapter implements View.OnClickListener{
    private Context lss;
    private List<Map<String , Object>>allVallues;
    public MyAdapterLssue(Context ctx, List<Map<String,Object>>allVallues){
        this.lss =ctx;
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
       /* ViewHolder viewHolder = null;*/
        if (lss == null)
            lss = parent.getContext();
        if (convertView == null){
            //如果当前行没有被创建出来  利用之前的布局文件将行创建
            convertView = LayoutInflater.from(lss).inflate(R.layout.my_prder_list_lss,null);
/*            viewHolder = new ViewHolder();
            viewHolder.meEva = (Button) convertView.findViewById(R.id.me_eva_btn);
            viewHolder.meDelete= (Button) convertView.findViewById(R.id.me_delete_btn);
            convertView.setTag(viewHolder);*/
        }
        //获取viewHolder实例
/*        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.meEva.setTag(R.id.btn);
        viewHolder.meDelete.setTag(R.id.btn);
        viewHolder.meDelete.setOnClickListener(this);
        viewHolder.meEva.setOnClickListener(this);*/
        //头像图片
        TextView head_me = (TextView) convertView.findViewById(R.id.head_me);
        //设置图片高度
        TextView call_me = (TextView) convertView.findViewById(R.id.call_me);
        TextView order_time_me = (TextView) convertView.findViewById(R.id.order_time_me);
        TextView price_me = (TextView) convertView.findViewById(R.id.price_me);
        TextView complete_me = (TextView) convertView.findViewById(R.id.complete_me);

        Map<String,Object>map = allVallues.get(position);
        call_me.setText(map.get("name").toString());
        order_time_me.setText(map.get("time").toString());
        price_me.setText(map.get("grade").toString());
        complete_me.setText(map.get("state").toString());

        head_me.setBackgroundResource((int) map.get("img"));
        return convertView;
    }

    @Override
    public void onClick(View v) {
       /* switch (v.getId()){
            //评价按钮的跳转
            case R.id.me_eva_btn:
                Intent in = new Intent(lss, EvaluationActivity.class);
                lss.startActivity(in);
                break;
            //删除订单的按钮
            case R.id.me_delete_btn:
                break;
        }*/
    }

/*    static class ViewHolder{
        Button meEva;
        Button meDelete;

    }*/
}
