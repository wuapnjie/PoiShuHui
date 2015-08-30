package com.flying.xiaopo.intentdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener {
    private Button btn_explict, btn_implict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_explict = (Button) findViewById(R.id.btn_explict);
        btn_implict = (Button) findViewById(R.id.btn_implicit);
        btn_explict.setOnClickListener(this);
        btn_implict.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_explict:
                Intent ex_intent = new Intent(this, SecondActivity.class);
                startActivity(ex_intent);
                break;
            case R.id.btn_implicit:
                Intent im_Intent = new Intent();
                im_Intent.setAction(Intent.ACTION_SEND);
                startActivity(im_Intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "requestCode is" + requestCode + ",resultCode is" + resultCode, Toast.LENGTH_SHORT).show();
    }
}
