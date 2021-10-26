package com.swufestu.successfulrmb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RateListActivity extends AppCompatActivity {
    List<String> data = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list);

        ListView listView = findViewById(R.id.mylist1);

        for(int i = 0;i<10;i++){
            data.add("item"+i);
        }
        ListAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                data);
        listView.setAdapter(adapter);





        //Handler handler = handleMessage(Message){
           // super.handleMessage(msg);



        //}
    }
}