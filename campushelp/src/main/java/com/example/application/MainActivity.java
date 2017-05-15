package com.example.application;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.adapter.MyPagerAdapter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private int i;
    //底部三个按钮
    private Button[] bottomBts = new Button[3];
    //三个按钮未被选中时的图片
    private int[] allBottomImgs = new int[]{R.mipmap.bpage1, R.mipmap.dj1, R.mipmap.bpeople1};
    //三个按钮被选中时的图片
    private int[] allBottomImgsSelected = new int[]{R.mipmap.bpage2, R.mipmap.dj, R.mipmap.bpeople2};
    //目标Activity
    private Class[] targetActivities = new Class[]{MainActivity.class, ReleaseActivity.class, PersonalActivity.class};

    //用于做pager的变量
    private ViewPager pager;
    private List<View> allViews = new ArrayList<View>();
    private MyPagerAdapter pagerAdapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);

//3个页面的切换和滑动效果
        this.init(0);
        pager = (ViewPager) findViewById(R.id.pager);
        //分别创建应用中要用到的三个界面
        View mainView = LayoutInflater.from(this).inflate(R.layout.page_main, null);
        this.myView(mainView);
        View releaseView = LayoutInflater.from(this).inflate(R.layout.page_release, null);
        View personalView = LayoutInflater.from(this).inflate(R.layout.page_personal, null);
        //用personalView调用mySet方法
        this.mySet(personalView);

        /*//View myset = LayoutInflater.from(this).inflate(R.layout.personal_set,null);
        this.mySet(myset);
        allViews.add(myset);*/

        allViews.add(mainView);
        allViews.add(releaseView);
        allViews.add(personalView);
        pagerAdapter = new MyPagerAdapter(allViews);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(3);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < bottomBts.length; i++) {
                    if (position == i) {
                        bottomBts[i].setBackgroundResource(allBottomImgsSelected[i]);
                    } else {
                        bottomBts[i].setBackgroundResource(allBottomImgs[i]);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    protected void init(int index) {
        bottomBts[0] = (Button) findViewById(R.id.set_index);
        bottomBts[1] = (Button) findViewById(R.id.set_release);
        bottomBts[2] = (Button) findViewById(R.id.set_personal);

        for (i = 0; i < bottomBts.length; i++) {
            final int temp = i;
            if (i == index) {
                //当前被选中的按钮
                bottomBts[i].setBackgroundResource(allBottomImgsSelected[i]);
            } else {
                //当前未被选中时的按钮
                bottomBts[i].setBackgroundResource(allBottomImgs[i]);
            }
            bottomBts[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    pager.setCurrentItem(temp);
                }
            });
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    /**个人页面设置的跳转方法
     *
     */
    private Button setBtn;
    protected void mySet(View set){
        //设置按钮的跳转
        setBtn = (Button) set.findViewById(R.id.set_btn);
        Log.d("qwe", "_________________________________"+setBtn);
        setBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent in = new Intent(MainActivity.this,SetActivity.class);
                startActivity(in);
            }
        });
    }


    //ListView 显示订单界面
    private ListView list;
    private List<Map<String,Object>> allValues = new ArrayList<Map<String,Object>>();
    private SimpleAdapter adapter;

    protected void myView(View lis) {
        //主页面订单显示界面

        //获取ListView组件对象
        list = (ListView)lis.findViewById(R.id.list);

        for (int i = 0; i < 30; i++) {

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("time", "顺丰快递");
            map.put("pro", "取件地址：北校区");
            map.put("collage", "送至：北区1栋");
            map.put("remark", "态度好，请吃饭");
            allValues.add(map);
        }

        adapter = new SimpleAdapter(this, allValues, R.layout.my_simple_list_item, new String[]{"time", "pro", "collage", "remark"},
                new int[]{R.id.time, R.id.pro, R.id.collage, R.id.remark});
        list.setAdapter(adapter);

    }

}
