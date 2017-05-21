package com.example.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.LauncherApps;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by 90603 on 2017/5/21.
 */

public class ContentAdapter extends BaseAdapter  implements View.OnClickListener {
    private List<Map<String,Object>> mapList;
    private Context context;
    private Callback callback;
    public  interface  Callback{
        public  void  click(View view);
    }
    public ContentAdapter(Context context, List<Map<String,Object>> mapList, Callback callback){
        this.context=context;
        this.mapList=mapList;
        this.callback=callback;
    }
    @Override

    public int getCount() {
        return mapList.size();
    }

    @Override
    public Object getItem(int position) {
        return  mapList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return null;
    }

    @Override
    public void onClick(View view) {
        callback.click(view);
    }

}
