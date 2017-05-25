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
 * Created by 梁城月 on 2017/5/25.
 */

public class MessageAdapter extends BaseAdapter {
    private Context ctx;
    private List<Map<String , Object>> allVallues;
    public MessageAdapter(Context ctx, List<Map<String,Object>>allVallues){
        this.ctx =ctx;
        this.allVallues=allVallues;
    }
    @Override
    public int getCount() {
        return allVallues.size();
    }

    @Override
    public Object getItem(int position) {
        return allVallues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            //如果当前行没有被创建出来  利用之前的布局文件将行创建
            convertView = LayoutInflater.from(ctx).inflate(R.layout.message,null);
            //设置该行的宽和高
            convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Globals.SCREEN_HEIGHT/9));
        }
        TextView date= (TextView) convertView.findViewById(R.id.date_msg);
        TextView msg=(TextView)convertView.findViewById(R.id.msg_msg);

        Map<String,Object> map=allVallues.get(position);
        date.setText(map.get("commentDate").toString());
        msg.setText(map.get("content").toString());
        return convertView;
    }
}
