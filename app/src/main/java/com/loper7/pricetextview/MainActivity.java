package com.loper7.pricetextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loper7.pricetextviewlibrary.PriceTextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private PriceTextView tv_normal, tv_symbol, tv_unit, tv_sau, tv_none;
    private Button btn_show;
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_normal = (PriceTextView) findViewById(R.id.price_1);
        tv_symbol = (PriceTextView) findViewById(R.id.price_2);
        tv_unit = (PriceTextView) findViewById(R.id.price_3);
        tv_sau = (PriceTextView) findViewById(R.id.price_4);
        tv_none = (PriceTextView) findViewById(R.id.price_5);
        btn_show = (Button) findViewById(R.id.btn_show);
        edit = (EditText) findViewById(R.id.edit_price);


        btn_show.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show:
                tv_none.setText(3.0f, edit.getText().toString());
                tv_normal.parsePrice(edit.getText().toString()).show();
                tv_symbol.parsePrice(edit.getText().toString()).showSymbol("￥");
                tv_unit.parsePrice(edit.getText().toString()).showUnit("元");
                tv_sau.parsePrice(edit.getText().toString()).showSymbol("￥").showUnit("元");
                break;
        }
    }
}
