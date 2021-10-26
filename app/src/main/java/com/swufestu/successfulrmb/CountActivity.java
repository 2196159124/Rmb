package com.swufestu.successfulrmb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CountActivity extends AppCompatActivity {

    TextView country;
    EditText input_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        country = findViewById(R.id.rate_country);
        input_num = findViewById(R.id.input_rmb);
        String input = input_num.getText().toString();
        Intent intent = getIntent();
        String titleStr = intent.getStringExtra("titleStr");
        String detailStr = intent.getStringExtra("detailStr");
        country.setText(titleStr);

    }

    public void cal(View btn) {
        country = findViewById(R.id.rate_country);
        input_num = findViewById(R.id.input_rmb);
        String input = input_num.getText().toString();
        if (input.equals("")) {
            Toast.makeText(CountActivity.this, "Please input number!", Toast.LENGTH_SHORT).show();
            Toast dialog;
        } else {
            float rmb_num = Float.parseFloat(input);
            Intent intent = getIntent();
            String titleStr = intent.getStringExtra("titleStr");
            String detailStr = intent.getStringExtra("detailStr");
            String result = String.valueOf(rmb_num * Float.parseFloat(detailStr));
            country.setText(titleStr + ":" + result);

        }


    }
}

