package com.example.application;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Page extends AppCompatActivity {

    private ImageView imgPlay;
    private int[] imgs = new int[]{R.mipmap.img_1, R.mipmap.img_2, R.mipmap.img_3 };
    private Handler handler1;
    private static int imgindex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_main);

 /*       imgPlay = (ImageView) findViewById(R.id.play_img);

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
        };*/
    /*    thread.start();
        handler1 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                imgPlay.setBackgroundResource(imgs[imgindex % imgs.length]);
            }
        };*/


    }
}