package com.swufestu.successfulrmb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class rate extends AppCompatActivity {

    float dollar_rate;
    float euro_rate;
    float won_rate;
    float dollar2,euro2,won2;
    EditText dollarText,euroText,wonText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        Intent intent=getIntent();
        float dollar2=intent.getFloatExtra("dollar_rate1",0.35f);
        float euro2=intent.getFloatExtra("euro_rate1",0.28f);
        float won2=intent.getFloatExtra("won_rate1",501f);
        dollarText=findViewById(R.id.dollar_rate);
        euroText=findViewById(R.id.euro_rate);
        wonText=findViewById(R.id.won_rate);
        dollarText.setText(String.valueOf(dollar2));
        euroText.setText(String.valueOf(euro2));
        wonText.setText(String.valueOf(won2));

    }
    public void  openOne(View btn)
    {
        //将修改的rate值传到前一个rmb页面
        Intent config=new Intent(this,rmb.class);
        config.putExtra("dollar_rate_key",dollar_rate);
        config.putExtra("euro_rate_key",euro_rate);
        config.putExtra("won_rate_key",won_rate);

        //将rate数据存入myrate文档里面
        SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putFloat("dollar_rate",dollar_rate);
        editor.putFloat("euro_rate",euro_rate);
        editor.putFloat("won_rate",won_rate);
        editor.apply();
        startActivityForResult(config,1);
        //关闭当前窗口
        finish();
    }

}