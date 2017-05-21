package com.example.application;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.MyAdapter;
import com.example.adapter.MyPagerAdapter;
import com.example.util.DoubleDatePickerDialog;
import com.example.util.Globals;
import com.example.util.Util;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private Button outBtn;
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
        Util.allActiveActivities.add(this);
        //调用init方法，初始化，获取当前手机的宽和高
        Globals.init(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);

//3个页面的切换和滑动效果
        this.init(0);
        pager = (ViewPager) findViewById(R.id.pager);
        //分别创建应用中要用到的三个界面
        View mainView = LayoutInflater.from(this).inflate(R.layout.page_main, null);
        this.myView(mainView);
        this.sear(mainView);
        View releaseView = LayoutInflater.from(this).inflate(R.layout.page_release, null);
        this.myDate(releaseView);
        View personalView = LayoutInflater.from(this).inflate(R.layout.page_personal, null);
        //用personalView调用mySet方法
        this.mySet(personalView);
        this.myLss(personalView);
        this.myBuy(personalView);

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
        setBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent in = new Intent(MainActivity.this,SetActivity.class);
                startActivity(in);
            }
        });
    }

    /**
     * 查看发布的订单跳转方法
     */
    private Button setlss;
    protected void myLss(View set){
        setlss= (Button) set.findViewById(R.id.release_btn);
        setlss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,LssueActivity.class);
                startActivity(in);
            }
        });
    }

    /**
     * 查看购买的订单跳转方法
     */
    private Button setBuy;
    protected void myBuy(View set){
        setBuy= (Button) set.findViewById(R.id.buy_btn);
        setBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,BuyActivity.class);
                startActivity(in);
            }
        });
    }



    //跳转到搜索栏
    private TextView search_one;

    protected void sear(View view){
        search_one = (TextView) view.findViewById(R.id.search_one);
        search_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,SearchList.class);
                startActivity(in);
            }
        });
    };



    //ListView 显示订单界面
    private ListView list;
    private List<Map<String,Object>> allValues = new ArrayList<Map<String,Object>>();
    private MyAdapter adapter;
    private int[] allImgs = new int[]{R.mipmap.head };

    protected void myView(View lis) {
        //主页面订单显示界面

        //获取ListView组件对象
        list = (ListView)lis.findViewById(R.id.list);
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("time", "顺丰快递");
            map.put("pro", "取件地址：北校区");
            map.put("collage", "送至：北区1栋");
            map.put("remark", "态度好，请吃饭");
            map.put("img",allImgs[random.nextInt(1)]);
            allValues.add(map);
        }
        //创建自定义Adapyer
        adapter =new MyAdapter(this,allValues);
        //将adapter添加到ListView中
        list.setAdapter(adapter);
      /*  adapter = new SimpleAdapter(this, allValues, R.layout.my_simple_list_item, new String[]{"time", "pro", "collage", "remark"},
                new int[]{R.id.time, R.id.pro, R.id.collage, R.id.remark});
        list.setAdapter(adapter);*/
        //点击一行事件

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"当前第"+position+"行",Toast.LENGTH_LONG).show();
            }
        });
        //长按事件
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });
    }
    TextView et;
    protected void onDestroy(){
        Util.allActiveActivities.remove(this);
        super.onDestroy();
    }
    private EditText name;
    private  EditText takeaddress;
    private  EditText preaddress;
    private  EditText teltPhone;
    private EditText grade;
    private  EditText remarks;
    private Button addOrder;
    private   Handler handler;
    protected void myDate(View v){
        et = (TextView) v.findViewById(R.id.takeDate_a);
        name=(EditText)v.findViewById(R.id.name_a);
        takeaddress=(EditText)v.findViewById(R.id.takeaddress_a);
        preaddress =(EditText)v.findViewById(R.id.preaddress_a);
        teltPhone=(EditText)v.findViewById(R.id.teltPhone_a);
        grade=(EditText)v.findViewById(R.id.grade_a);
        remarks=(EditText)v.findViewById(R.id.remarks_a);
        addOrder=(Button)v.findViewById(R.id.addOrder_o);


        et.setOnClickListener(new View.OnClickListener() {
            Calendar c = Calendar.getInstance();
            @Override
            public void onClick(View v) {
                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                new DoubleDatePickerDialog(MainActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                          int endDayOfMonth) {
                        String textString = String.format("%d-%d-%d~%d-%d-%d", startYear,
                                startMonthOfYear + 1, startDayOfMonth, endYear, endMonthOfYear + 1, endDayOfMonth);
                        et.setText(textString);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
            }
        });
        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=validate(et.getText().toString().trim(),"取件时间");
                if (!msg.equals("")){
                    Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                    return;
                }
                msg=validate(name.getText().toString().trim(),"快递名称");
                if (!msg.equals("")){
                    Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                    return;
                }
                msg=validate(takeaddress.getText().toString().trim(),"取货地址");
                if (!msg.equals("")){
                    Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                    return;
                }
                msg=validate(preaddress.getText().toString().trim(),"送达地址");
                if (!msg.equals("")){
                    Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                    return;
                }
                String regExp = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
                Pattern p = Pattern.compile(regExp);
                final Matcher m = p.matcher(teltPhone.getText().toString().trim());
                if (!m.matches()){
                    Toast.makeText(MainActivity.this,"电话号码格式不正确！",Toast.LENGTH_SHORT).show();
                    return;
                }
                final String[] flag = {""};
            Thread thread=new Thread(){
                @Override
                public void run() {

                    Map<String,String> map=new HashMap<String, String>();
                    map.put("takeDate",et.getText().toString());
                    map.put("name",name.getText().toString());
                    map.put("takeaddress",takeaddress.getText().toString());
                    map.put("preaddress",preaddress.getText().toString());
                    map.put("teltPhone",teltPhone.getText().toString());
                    map.put("grade",grade.getText().toString());
                    map.put("remarks", remarks.getText().toString());
                    map.put("preOrderuUser.id",Util.userId);
                    String url=Util.ip+"order/addOrder?"+fommatPamer(map);
                    try {
                        URL url1= new URL(url);
                        URLConnection urlConnection=url1.openConnection();
                        InputStream is=urlConnection.getInputStream();
                        BufferedReader bf=new BufferedReader(new InputStreamReader(is));
                        flag[0] =bf.readLine();
                        bf.close();
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.v("qweew",flag[0]);
                    if (flag[0].equals("true")){
                        //Toast.makeText(MainActivity.this,"订单发布成功",Toast.LENGTH_SHORT).show();
                        handler.sendEmptyMessage(0);
                    }else {
                        //Toast.makeText(MainActivity.this,"订单发布失败！",Toast.LENGTH_SHORT).show();
                    }
                }
            };
                thread.start();
            }
        });
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==0){
                    et.setText("");
                    name.setText("");
                    takeaddress.setText("");
                    preaddress.setText("");
                    teltPhone.setText("");
                    grade.setText("");
                    remarks.setText("");
                }
            }
        };
    }


    protected String validate(String name,String title){
        String msg="";
        if (name.equals("")){
            msg=msg+title+"不能为空！";
        }
        return msg;
    }
    protected String fommatPamer(Map<String,String> map){
        StringBuffer sb=new StringBuffer();
        for (String item:map.keySet()){
            sb.append(item+"="+map.get(item)+"&");
        }
        return sb.toString().substring(0,sb.toString().length()-1);
    }
}
