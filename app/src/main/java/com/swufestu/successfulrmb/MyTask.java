package com.swufestu.successfulrmb;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyTask implements Runnable{
    Handler handler;
    public MyTask(Handler handler){this.handler=handler;}
    private static final String TAG = "MyTask";
    List<String> retlist =new ArrayList<String>();

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
    public void run() {
        try {
            Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Elements table = doc.getElementsByTag("table");
            Element firstTable = table.get(1);
            Log.i(TAG, "run: tables="+firstTable);
            Elements trs = firstTable.getElementsByTag("tr");
            trs.remove(0);
            for(Element tr:trs){

                String td1=tr.getElementsByTag("td").get(0).text();
                String td2=tr.getElementsByTag("td").get(4).text();
                retlist.add(td1+"--->"+td2);
                Log.i(TAG, "run: td1="+td1+"\t td2="+td2);
            }
            Message msg = handler.obtainMessage(7);
            msg.obj = retlist;
            handler.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

