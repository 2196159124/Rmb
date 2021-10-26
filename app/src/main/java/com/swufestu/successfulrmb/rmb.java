package com.swufestu.successfulrmb;

import static android.media.CamcorderProfile.get;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class rmb extends AppCompatActivity implements Runnable {

    EditText rmb1;
    float dollar_rate = 0.35f;
    float euro_rate = 0.28f;
    float won_rate = 501f;
    double rmb;
    TextView result;
    private static final String TAG = "rmb";
    Handler handler;//不要写任何方法，只能定义

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rmb);

        result = findViewById(R.id.outputrmb);

        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG,"handleMessage:收到消息");
                if (msg.what == 6) {
                    Bundle bdl=(Bundle)msg.obj;
                    dollar_rate = bdl.getFloat("key_dollar");
                    euro_rate = bdl.getFloat("key_euro");
                    won_rate = bdl.getFloat("key_won");
                    Toast.makeText(rmb.this,"汇率已更新",Toast.LENGTH_SHORT).show();
                    //String str = (String) msg.obj;

                    //result.setText(str);
                }
                super.handleMessage(msg);
            }
        };
        //启动线程

        Thread thread = new Thread(this);
        thread.start();//this.run

        //将myrate文档里面的rate数据取出
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        dollar_rate = sharedPreferences.getFloat("dollar_rate", 0.0f);
        euro_rate = sharedPreferences.getFloat("euro_rate", 0.0f);
        won_rate = sharedPreferences.getFloat("won_rate", 0.0f);



    }

    public void rmb(@NonNull View add) {
        rmb1 = findViewById(R.id.inputrmb);
        rmb = Double.valueOf(rmb1.getText().toString());
        TextView out = findViewById(R.id.outputrmb);
        /*if(rmb1.length()>0) {*/
        if (add.getId() == R.id.dollar) {
            rmb += rmb * dollar_rate;
        } else if (add.getId() == R.id.euro) {
            rmb += rmb * euro_rate;
        } else {
            rmb += rmb * won_rate;
        }
        out.setText(String.valueOf(rmb));
    }

    /*else
    {
        Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
    }
}*/
    public void openOne(View btn) {
        Intent config = new Intent(this, rate.class);
        config.putExtra("dollar_rate1", dollar_rate);
        config.putExtra("euro_rate1", euro_rate);
        config.putExtra("won_rate1", won_rate);
        startActivityForResult(config, 1);
    }

    public void config() {
        Intent intent = getIntent();
        float dollar_rate = intent.getFloatExtra("dollar_rate_key", 0.0f);
        float euro_rate = intent.getFloatExtra("euro_rate_key", 0.0f);
        float won_rate = intent.getFloatExtra("won_rate_key", 0.0f);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == 2) {
            dollar_rate = data.getFloatExtra("dollar_rate_key", 0.1f);
            euro_rate = data.getFloatExtra("euro_rate_key", 0.1f);
            won_rate = data.getFloatExtra("won_rate_key", 0.1f);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            //菜单事件代码
            config();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void run() {
        Log.i(TAG, "run...");
        //延迟
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //发送消息
        /*Message msg = handler.obtainMessage(6);
        //message=6
        msg.obj = "Hello from run";
        handler.sendMessage(msg);*/

/*
        //获取网络数据
        URL url = null;
        try {
            url = new URL("https://www.360.cn/");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();
            String html = inputStream2String(in);
            Log.i(TAG, "run: html=" + html);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        Bundle bundle = new Bundle();
        try {
            Document doc = Jsoup.connect("https://usd-cny.com/").get();
            Log.i(TAG,"run:title="+doc.title());
           /* Elements tables = doc.getElementsByTag("table");
            Element firstTable = tables.first();*/
            Element firstTable = doc.getElementsByTag("table").first();
            //Log.i(TAG,"run:table="+firstTable);
            /*for(Element item:firstTable.getElementsByClass("bz")){
                Log.i(TAG,"run:item="+item.text());
            }*/
            Elements trs = firstTable.getElementsByTag("tr");
            trs.remove(0);
            for(Element tr:trs){
                //Log.i(TAG,"run:tr="+tr);
                Elements tds = tr.getElementsByTag("td");
                /*Element td1 = tds.get(0);
                Element td2 = tds.get(4);
                //Log.i(TAG,"run:tds.count="+tds.size());
                Log.i(TAG,"run:td1="+td1.text() + "\t td2="+td2.text());*/
                String td1 = tds.get(0).text();
                String td2 = tds.get(4).text();
                Log.i(TAG,"run:td1="+td1 +"--->"+td2);
                if("美元".equals(td1)){
                    bundle.putFloat("key_dollar",100/Float.parseFloat(td2));

                }else if ("欧元".equals(td1)){
                    bundle.putFloat("key_euro",100/Float.parseFloat(td2));

                }else if ("韩币".equals(td1)){
                    bundle.putFloat("key_won",100/Float.parseFloat(td2));

                }

            }

/*
            Elements tds = firstTable.getElementsByTag("td");
            for(int i= 0;i<tds.size();i+=5){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+1);
                Log.i(TAG,"run:td1="+td1.text()+"\t td2="+td2.text());

            }
            Elements ths = firstTable.getElementsByTag("th");
            for(Element th : ths){
                Log.i(TAG,"run:th="+th);
                Log.i(TAG,"run:th.html="+th.html());
                Log.i(TAG,"run:th.text="+th.text());
            }
            Element th2 = ths.get(1);
            Log.i(TAG,"run:th2="+th2);*/


        }catch(IOException e){
            e.printStackTrace();
        }

        Message msg = handler.obtainMessage(6);
        //message=6

        msg.obj = bundle;
        handler.sendMessage(msg);
        Log.i(TAG,"run:消息已发送");


    }

    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "utf-8");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }


}