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
import java.util.HashMap;
import java.util.List;

public class RateTask implements Runnable{

/*    public RateTask(Handler handler){
        this.handler=handler;
    }*/
    private static final String TAG = "RateTask";
    private Handler handler;
    private ArrayList<HashMap<String,String>> listItems;



    public void setHandler(Handler handler) {
        this.handler = handler;
    }


    /*    List<HashMap<String,String>> retlist =new ArrayList<HashMap<String,String>>();


        @Override
        public void run() {
            try {
                Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
                Elements table = doc.getElementsByTag("table");
                Element firstTable = table.get(1);
                Log.i(TAG, "run: tables="+firstTable);
                Elements trs = firstTable.getElementsByTag("tr");
                trs.remove(0);
                for(Element tr:trs){

                    String td1=tr.getElementsByTag("td1").get(0).text();
                    String td2=tr.getElementsByTag("td2").get(4).text();
                    Log.i(TAG, "run: td1="+td1+"--->"+"\t td2="+td2);

                    HashMap<String,String> map= new HashMap<String,String>();
                    map.put("ItemTitle",td1);
                    map.put("ItemDetail",td2);
                    retlist.add(map);

                }
                Message msg = handler.obtainMessage(7);
                msg.obj = retlist;
                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public void setHandler(Handler handler) {
        }*/
    @Override
    public void run() {
        List<String> retlist = new ArrayList<String>();
        listItems = new ArrayList<HashMap<String,String>>();


        try {
            Document doc = Jsoup.connect("https://usd-cny.com/").get();
            Log.i(TAG,"run:title="+doc.title());

            Element firstTable = doc.getElementsByTag("table").first();

            Elements trs = firstTable.getElementsByTag("tr");
            trs.remove(0);
            for(Element tr:trs){


                Elements tds = tr.getElementsByTag("td");

                String td1 = tds.get(0).text();
                String td2 = tds.get(4).text();
                Log.i(TAG,"run:td1="+td1 +"--->"+td2);

                if (!(td2.equals(""))){
                    HashMap<String,String> map = new HashMap<String,String>();

                    float rate = 100f/Float.parseFloat(td2);
                    retlist.add(td1+"------>"+String.valueOf(rate));
                    map.put("ItemTitle",td1);
                    map.put("ItemDetail",String.valueOf(rate));
                    listItems.add(map);
                } else {

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("ItemTitle", td1);
                    map.put("ItemDetail", td2);
                    listItems.add(map);
                    retlist.add(td1 + "--->" + td2);
                }

            }
            Message msg = handler.obtainMessage(9,listItems);

            handler.sendMessage(msg);
            Log.i(TAG,"run:消息已发送");



        }catch(IOException e){
            e.printStackTrace();
        }




    }


}
