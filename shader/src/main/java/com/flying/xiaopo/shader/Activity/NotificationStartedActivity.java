package com.flying.xiaopo.shader.Activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.flying.xiaopo.shader.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lenovo on 2015/7/19.
 */
public class NotificationStartedActivity extends Activity {
    @InjectView(R.id.btn_cancel_tip)
    Button btn_cancel_tip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_started);
        ButterKnife.inject(this);
    }
    @OnClick(R.id.btn_cancel_tip)
    void cancelTip(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
