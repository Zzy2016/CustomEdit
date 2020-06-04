package com.example.customedit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1, btn2, btn3, btn4, btn5;
    CustomEditText customEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        customEditText = (CustomEditText) findViewById(R.id.edit);
        customEditText.post(new Runnable() {
            @Override
            public void run() {
                customEditText.setMaxWidth(customEditText.getMeasuredWidth() - customEditText.getPaddingLeft() - customEditText.getPaddingRight());
            }
        });
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                customEditText.changeSize(0);
                break;
            case R.id.btn2:
                customEditText.changeSize(1);
                break;
            case R.id.btn3:
                customEditText.changeSize(2);
                break;
            case R.id.btn4:
                customEditText.insertImgFormCA("image");
                break;
            case R.id.btn5:
                Log.e("文章内容", customEditText.getContentList());
                break;
        }
    }
}
