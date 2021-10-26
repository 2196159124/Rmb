package com.swufestu.successfulrmb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends ListActivity {

    /*private static final String TAG = "MyListActivity";
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_list);
        //构造数据
        /*List<String> list1 = new ArrayList<String>();
        for (int i = 1; i < 100; i++) {
            list1.add("item" + i);
        }


        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list1);
        setListAdapter(adapter);*/
/*
        MyTask mtask = new MyTask(handler);
        Thread t = new Thread(mtask);
        t.start();

        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (msg.what == 7) {
                    List<String> list2 = (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(
                            MyListActivity.this,
                            android.R.layout.simple_list_item_1,
                            list2);
                    setListAdapter(adapter);
                }

            }
        };



    }
    /*public void run () {
        List<String> retList = new ArrayList<String>();
        try {

            Thread.sleep(3000);
            Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Log.i(TAG, "run:title=" + doc.title());

            Element firstTable = doc.getElementsByTag("table").first();

            Elements trs = firstTable.getElementsByTag("tr");
            trs.remove(0);
            for (Element tr : trs) {
                //Log.i(TAG,"run:tr="+tr);
                Elements tds = tr.getElementsByTag("td");

                String td1 = tds.get(0).text();
                String td2 = tds.get(4).text();
                Log.i(TAG, "run:td1=" + td1 + "--->" + td2);
                retList.add(td1 + "--->" + td2);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Message msg = handler.obtainMessage(5);
        msg.obj = retList;
        handler.sendMessage(msg);
    }*/

    Handler handler;
    private static final String TAG = "MyListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_list_activites);
        //构造数据
//        List<String> list1=new ArrayList<String>();
//        for (int i=1;i<100;i++){
//            list1.add("item"+i);
//        }
//        String[] list_data={"one","two","three","four"};
//        ListAdapter adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list_data);
//        setListAdapter(adapter);

        handler =new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(Message msg){
                if (msg.what==7){
                    List<String> list2=(List<String>) msg.obj;
                    ListAdapter adapter=new ArrayAdapter<String>(MyListActivity.this, android.R.layout.simple_list_item_1,list2);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
        MyTask mtask=new MyTask(handler);
        Thread td=new Thread(mtask);
        td.start();
    }

}

