package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.application.R;
import com.example.util.Globals;

import java.util.List;
import java.util.Map;

/**
 * Created by 90603 on 2017/5/21.
 */

public class MyAdapter extends BaseAdapter {

    private Context ctx;
    private List<Map<String , Object>>allVallues;
    public MyAdapter(Context ctx, List<Map<String,Object>>allVallues){
        this.ctx =ctx;
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

        if (convertView == null){
            //如果当前行没有被创建出来  利用之前的布局文件将行创建
            convertView = LayoutInflater.from(ctx).inflate(R.layout.my_simple_list_item,null);
            //设置该行的宽和高
            convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Globals.SCREEN_HEIGHT/9));
        }
        TextView img = (TextView) convertView.findViewById(R.id.head_img);
        //设置图片高度
        img.getLayoutParams().height = Globals.SCREEN_HEIGHT/9;

        TextView time = (TextView) convertView.findViewById(R.id.time);
        TextView pro = (TextView) convertView.findViewById(R.id.pro);
        TextView collage = (TextView) convertView.findViewById(R.id.collage);
        TextView remark = (TextView) convertView.findViewById(R.id.remark);

        Map<String,Object>map =allVallues.get(position);
        time.setText(map.get("time").toString());
        pro.setText(map.get("pro").toString());
        collage.setText(map.get("collage").toString());
        remark.setText(map.get("remark").toString());

        img.setBackgroundResource((int) map.get("img"));
        return convertView;
    }
}
