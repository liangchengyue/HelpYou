package com.example.application;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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
        this.imgs(mainView);
        this.sear(mainView);
        View releaseView = LayoutInflater.from(this).inflate(R.layout.page_release, null);
        this.myDate(releaseView);
        View personalView = LayoutInflater.from(this).inflate(R.layout.page_personal, null);
        //用personalView调用mySet方法
        this.mySet(personalView);
        this.myLss(personalView);
        this.myBuy(personalView);
 this.Camera(personalView);


        /*//View myset = LayoutInflater.from(this).inflate(R.layout.personal_set,null);
        this.mySet(myset);
        allViews.add(myset);*/
        getUserinfo();

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
    private TextView userImg;
    private BitmapDrawable drawable;
    private Handler handler1;
    protected void mySet(View set){
//        userImg=(TextView)set.findViewById(R.id.userImg);

//        Thread thread=new Thread(){
//            @Override
//            public void run() {
//                try {
//                  Bitmap  bitmaps = Util.getBitmap(Util.ip+"ui/userimg/defaultuserimage.png");
//                    drawable = new BitmapDrawable(bitmaps);
//                    drawable.setTileModeXY(Shader.TileMode.REPEAT , Shader.TileMode.REPEAT );
//                    drawable.setDither(true);
//                    handler1.sendEmptyMessage(0);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        thread.start();
        handler1=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==0){
                    userImg.setBackgroundDrawable(drawable);
                }
            }
        };

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

    /**
     * 获取用户信息
     */
    protected void getUserinfo(){

    }
    //ListView 显示订单界面
    private ListView list;
    private List<Map<String,Object>> allValues = Util.orders;
    public static MyAdapter adapter;
    private int[] allImgs = new int[]{R.mipmap.head };

    protected void myView(View lis) {
        //主页面订单显示界面

        //获取ListView组件对象
        list = (ListView)lis.findViewById(R.id.list);
        Random random = new Random();
     /*   for (int i = 0; i <allValues.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("time", "顺丰快递");
            map.put("pro", "取件地址：北校区");
            map.put("collage", "送至：北区1栋");
            map.put("remark", "态度好，请吃饭");
            map.put("img",allImgs[random.nextInt(1)]);
            allValues.add(map);
        }*/

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
                Intent intent=new Intent(MainActivity.this,AccordorderActivity.class);
                intent.putExtra("index",position);
                startActivity(intent);
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
                    if (flag[0].equals("true")){
                       Thread thread1=new Thread(){
                           @Override
                           public void run() {
                               String  url = Util.ip + "order/queryOrderList";
                               try {
                                   URL url2 = new URL(url);
                                   URLConnection uc = url2.openConnection();
                                   InputStream is = uc.getInputStream();
                                   BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                                   String json = bf.readLine();
                                   bf.close();
                                   is.close();
                                   if (!json.equals("]")){
                                       JSONArray jsonArray = new JSONArray(json.toString());
                                       List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
                                       for (int i = 0; i < jsonArray.length(); i++) {
                                           JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                           Map<String, Object> map = new HashMap<String, Object>();
                                           map.put("id", jsonObject1.getString("id"));
                                           map.put("time", jsonObject1.getString("takeDate"));
                                           map.put("collage", jsonObject1.getString("takeaddress"));
                                           map.put("pro", jsonObject1.getString("preaddress"));
                                           map.put("remark", jsonObject1.getString("remarks"));
                                           map.put("img", R.mipmap.head);
                                           map.put("name", jsonObject1.getString("name"));
                                           map.put("phone", jsonObject1.getString("teltPhone"));
                                           map.put("grade", jsonObject1.getString("grade"));
                                           mapList.add(map);
                                       }

                                       Util.orders = mapList;
                                       handler.sendEmptyMessage(1);
                                   }
                               } catch (Exception e) {
                                   e.printStackTrace();
                               }
                           }
                       };
                        thread1.start();
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
                if (msg.what==1){
                    adapter.notifyDataSetChanged();
                }
            }
        };
    }




    /*   图片轮播
    *
    *
    * */
    private View view1, view2, view3;
    private ViewPager viewPager;  //对应的viewPager
    private List<View> viewList;//view数组

    protected void imgs(View view){
        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        LayoutInflater inflater=getLayoutInflater();
        view1 = inflater.inflate(R.layout.layout1, null);
        view2 = inflater.inflate(R.layout.layout2,null);
        view3 = inflater.inflate(R.layout.layout3, null);

        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);


        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {

                return arg0 == arg1;
            }

            @Override
            public int getCount() {

                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {

                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                container.addView(viewList.get(position));


                return viewList.get(position);
            }
        };


        viewPager.setAdapter(pagerAdapter);
        /*    private ImageView imgPlay;
        private int[] imgs = new int[]{R.mipmap.img_1, R.mipmap.img_2, R.mipmap.img_3 };
        private Handler handler1;
        private static int imgindex = 0;*/

        /*imgPlay = (ImageView)view.findViewById(R.id.play_img);

        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    handler1.sendEmptyMessage(1);
                    imgindex++;
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
        handler1 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                imgPlay.setBackgroundResource(imgs[imgindex % imgs.length]);
            }
        };*/

    };




    /*
    *
    *
    *
    *
    * 头像上传模块
    *
    *
    *
    *
    * */

    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLERY_REQUEST_CODE = 2;
    private static int CROP_REQUEST_CODE =3 ;
    private ImageView imageView;




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAMERA_REQUEST_CODE){
            //判断用户是否点击了取消
            if(data == null)
            {
                return;
            }//如果用户点击的是确定按钮
            else {
                //从data中取出数据
                Bundle extras = data.getExtras();
                if(extras !=null){
                    //保存用户拍摄的数据
                    Bitmap bm = extras.getParcelable("data");
//                    ImageView imageView = (ImageView) findViewById(R.id.imageview );
//                    //把拍摄的数据显示在imageview中
//                    imageView.setImageBitmap(bm);
                    //使用savaBitmap方法将bm中的uri获取并转换
                    Uri uri = savaBitmap(bm);
                    startImageZoom(uri);


                }
            }
        }
        //判断用户是否从裁剪界面返回
        else if (requestCode == GALLERY_REQUEST_CODE)
        {
            //用户点击取消
            if(data ==null)
            {
                return;
            }
            Uri uri;
            uri=data.getData();
            //判断从图库中读取图片后调用裁剪界面
            Uri FileUri = convertUri(uri);
            startImageZoom(FileUri);
        }
        else if(requestCode==CROP_REQUEST_CODE)
        {
            if (data == null){
                return;
            }
            Bundle extres = data.getExtras();
            Bitmap bm = extres.getParcelable("data");
            ImageView imageview = (ImageView) findViewById(R.id.imageview);
            imageview.setImageBitmap(bm);
        }
    }


    protected void Camera(View view) {


        //通过摄像头获取头像
//            Button button = (Button) findViewById(R.id.btn_camera);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    //打开图库
//                    startActivityForResult(intent,CAMERA_REQUEST_CODE);
//                }
//            });


        //点击头像跳转到打开图库
        //ImageView imageView = (ImageView) findViewById(R.id.imageview);
        Button btn_gallery = (Button) view.findViewById(R.id.btn_gallery);
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开图库界面
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //设定打开的文件类型
                intent.setType("image/*");
                //
                startActivityForResult(intent,GALLERY_REQUEST_CODE);
            }
        });
    }
    //打开裁剪界面
    private void startImageZoom(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        //裁剪的类型
        intent.setDataAndType(uri,"image/*");
        //设置裁剪的比例和长宽
        intent.putExtra("crop","true");
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("outputX",150);
        intent.putExtra("outputY",150);
        intent.putExtra("return-data",true);
        //通过这个码打开裁剪界面
        startActivityForResult(intent,CROP_REQUEST_CODE);
    }





    //创建文件夹
    private Uri savaBitmap(Bitmap bm)
    {
        File temDir = new File(Environment.getExternalStorageDirectory() + "/com.tanchao");
        if(!temDir.exists()){

            temDir.mkdirs();

        }
        File img = new File(temDir.getAbsoluteFile() + "avater.png");
        try {
            //获取到bitmap类型的URI，然后添加到输出流中
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG,85,fos);
            try {
                fos.flush();
                fos.close();
                //返回bitmap类型的返回值
                return Uri.fromFile(img);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }




    private Uri convertUri(Uri uri)
    {
        InputStream is = null;
        try {
            //将原来content的uri读取出来，并转换为bitmap的uri
            is = getContentResolver().openInputStream(uri);
            //这个工厂类
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            try {
                is.close();
                return savaBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }


    }





///////////////////////////////////////////////








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
            try {
                sb.append(item+"="+ URLEncoder.encode(map.get(item),"UTF-8")+"&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString().substring(0,sb.toString().length()-1);
    }
}
