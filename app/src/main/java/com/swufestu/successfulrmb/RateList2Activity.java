package com.swufestu.successfulrmb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RateList2Activity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private static final String TAG = "RateList2Activity";
    private ArrayList<HashMap<String,String>> listItems;
    private SimpleAdapter listItemAdapter;
    ListView mylist2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list2);

/*        ArrayList<HashMap<String,String>> listItems = new ArrayList<HashMap<String,String>>();
        for (int i=0;i<100;i++){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("ItemTitle","Rate:"+i);
            map.put("ItemDetail","detail:"+i);
            listItems.add(map);
        }

        SimpleAdapter listItemAdapter = new SimpleAdapter(this,
                listItems,
                R.layout.list_item,
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail}
                        );*/

/*        */
        ProgressBar bar = findViewById(R.id.my_progressbar);
        mylist2 = findViewById(R.id.mylist2);
        //mylist2.setOnItemLongClickListener(this);
        mylist2.setOnItemClickListener(this);
        mylist2.setAdapter(listItemAdapter);

         Handler handler =new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(Message msg){
                if (msg.what==9){
                    listItems = (ArrayList<HashMap<String, String>>)msg.obj;
                    /*ArrayList<HashMap<String,String>> retlist=(ArrayList<HashMap<String,String>>) msg.obj;
                    SimpleAdapter listItemAdapter=new SimpleAdapter(RateList2Activity.this,
                            retlist,
                            R.layout.list_item,
                            new String[]{"ItemTitle","ItemDetail"},
                            new int[]{R.id.itemTitle,R.id.itemDetail});*/
                    mylist2 = findViewById(R.id.mylist2);
                    mylist2.setAdapter(listItemAdapter);

                    MyAdapter myAdapter = new MyAdapter(RateList2Activity.this,
                            R.layout.list_item,
                            listItems);
                    mylist2.setAdapter(myAdapter);


                    //
                    bar.setVisibility(View.GONE);
                    mylist2.setVisibility(View.VISIBLE);



                }
                super.handleMessage(msg);
            }
        };
        RateTask task = new RateTask();
        task.setHandler(handler);
        Thread t2 = new Thread(task);
        t2.start();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Object itemAtPosition = mylist2.getItemAtPosition(position);
        HashMap<String,String> map = (HashMap<String, String>) itemAtPosition;
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");
        Log.i(TAG, "onItemClick: titleStr="+titleStr);
        Log.i(TAG, "onItemClick: detailStr="+detailStr);

        Intent first = new Intent(this, CountActivity.class);//this当前activity对象
        first.putExtra("titleStr", titleStr);
        first.putExtra("detailStr", detailStr);

        //startActivity(first);
        startActivity(first);

/*        TextView title=(TextView) view.findViewById(R.id.itemTitle);
        TextView detail=(TextView) view.findViewById(R.id.itemDetail);
        String title2=String.valueOf(title.getText());
        String detail2=String.valueOf(detail.getText());
        Log.i(TAG,"onItemClick: title2="+title2);
        Log.i(TAG,"onItemClick: detail2="+detail2);

        //事件处理、打开窗口，传递*/
    }

   /* @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG,"OnItemLongClick:长按处理");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
            .setMessage("请确认是否删除当前数据")
            .setPositiveButton("是",new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialog,int which){
                    Log.i(TAG,"OnClick:对话框事件处理");

                    myAdapter.remove();
                }
            }).setNetgativeButton("否",null)
        builder.create().show();
        return true;
    }*/
}