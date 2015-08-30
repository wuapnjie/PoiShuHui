package com.flying.xiaopo.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends ActionBarActivity {
    public static final int REQUEST_ENABLE_BT = 100;
    @InjectView(R.id.tv_device)
    TextView tv_device;
    @InjectView(R.id.btn_search)
    Button btn_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


    }

    @OnClick(R.id.btn_search)
    void click_search(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter==null){
            Toast.makeText(this,"设备不支持蓝牙",Toast.LENGTH_SHORT).show();
        }
        if (!mBluetoothAdapter.isEnabled()){
            Toast.makeText(this,"蓝牙未打开",Toast.LENGTH_SHORT).show();
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent,REQUEST_ENABLE_BT);
        }

        StringBuffer stringBuffer = new StringBuffer();

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size()>0){
            for (BluetoothDevice device:pairedDevices){
                stringBuffer.append(device.getName()+"\n"+device.getAddress()+"\n");
            }
        }

        Toast.makeText(this,"Search",Toast.LENGTH_SHORT).show();

        tv_device.setText(stringBuffer.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_ENABLE_BT&&resultCode==RESULT_OK){
            Toast.makeText(this,"蓝牙已经打开",Toast.LENGTH_SHORT).show();
        }else if(requestCode==REQUEST_ENABLE_BT&&resultCode==RESULT_CANCELED){
            Toast.makeText(this,"蓝牙未打开",Toast.LENGTH_SHORT).show();
        }
    }
}
