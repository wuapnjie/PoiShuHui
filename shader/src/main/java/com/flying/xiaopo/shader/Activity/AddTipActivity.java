package com.flying.xiaopo.shader.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.flying.xiaopo.shader.Bean.TipBean;
import com.flying.xiaopo.shader.ClockDBHelper;
import com.flying.xiaopo.shader.R;
import com.flying.xiaopo.shader.Reciver.TipsReciver;
import com.flying.xiaopo.shader.Utils;
import com.flying.xiaopo.shader.View.CircleRevealLayout;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lenovo on 2015/6/6.
 */
public class AddTipActivity extends Activity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    public static final String TAG = "AddRemind";
    // 动画View
    @InjectView(R.id.circlerevealview)
    CircleRevealLayout circleRevealView;
    //最外层View
    @InjectView(R.id.container_addremind)
    RelativeLayout container_addremind;
    //输入框
    @InjectView(R.id.et_event)
    EditText et_event;
    //重置按钮
    @InjectView(R.id.ibtn_ok)
    ImageButton ibtn_ok;
    //输入框的外层View
    @InjectView(R.id.ll_header_addremind)
    LinearLayout ll_header_addreminder;
    //日期的外层View
    @InjectView(R.id.date_container)
    LinearLayout date_container;
    //时间的外层View
    @InjectView(R.id.time_container)
    LinearLayout time_container;
    //显示设置日期的文本
    @InjectView(R.id.tv_set_date)
    TextView tv_set_date;
    //显示设置时间的文本
    @InjectView(R.id.tv_set_time)
    TextView tv_set_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtip);
        ButterKnife.inject(this);
        final int[] location = getIntent().getIntArrayExtra("location");
        ID = getIntent().getIntExtra("id", -1);
        //Toast.makeText(this, "id:" + ID, Toast.LENGTH_SHORT).show();
        circleRevealView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                circleRevealView.startAnimateFromLocation(location);
            }
        });
        container_addremind.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                container_addremind.getViewTreeObserver().removeOnPreDrawListener(this);
                animateLayout();
                return true;
            }
        });
        init();
    }


    private void animateLayout() {
        ll_header_addreminder.setTranslationY(-Utils.dpToPx(200));
        ibtn_ok.setAlpha(0f);
        ibtn_ok.setTranslationX(-Utils.dpToPx(80));
        date_container.setTranslationX(-500);
        time_container.setTranslationX(-500);
        ll_header_addreminder.animate().translationY(0).setDuration(300).setInterpolator(new AccelerateInterpolator()).setStartDelay(400).start();
        ibtn_ok.animate().alpha(1f).setDuration(300).translationX(0).setInterpolator(new AccelerateInterpolator()).setStartDelay(400).start();
        date_container.animate().translationX(0).setDuration(300).setInterpolator(new AccelerateInterpolator()).setStartDelay(500).start();
        time_container.animate().translationX(0).setDuration(300).setInterpolator(new AccelerateInterpolator()).setStartDelay(550).start();
    }

    private int mYear, mMonth, mDay;
    private int mHour, mMinute;
    private String mTitle;
    private String mDate;
    private String mTime;
    private Calendar mCalendar;
    private int ID;    //判断是新建还是更新

    private void init() {
        if (ID != -1) {
            TipBean bean = new ClockDBHelper(this).getRemind(ID);
            Log.d(TAG, bean.getDate() + " " + bean.getTime() + " " + bean.getTitle());
            //tv_set_date.setText("Date\n" + bean.getDate());
            //tv_set_time.setText("Time\n" + bean.getTime());
            et_event.setText(bean.getTitle());
            mYear = Integer.parseInt(bean.getDate().substring(0, 4));
            mMonth = Integer.parseInt(bean.getDate().substring(bean.getDate().indexOf("/") + 1, bean.getDate().lastIndexOf("/")));
            mDay = Integer.parseInt(bean.getDate().substring(bean.getDate().lastIndexOf("/") + 1));
            mHour = Integer.parseInt(bean.getTime().substring(0, bean.getTime().indexOf(":")));
            mMinute = Integer.parseInt(bean.getTime().substring(bean.getTime().indexOf(":") + 1));
            mDate = mYear + "/" + mMonth + "/" + mDay;
        } else {
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH) + 1;
            mDay = c.get(Calendar.DAY_OF_MONTH);
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

        }
        mDate = mYear + "/" + mMonth + "/" + mDay;
        tv_set_date.setText("Date\n" + mDate);
        if (mMinute < 10) {
            mTime = mHour + ":0" + mMinute;
        } else {
            mTime = mHour + ":" + mMinute;
        }
        tv_set_time.setText("Time\n" + mTime);


    }

    @OnClick(R.id.date_container)
    void setDate() {
        DatePickerDialog dpd = new DatePickerDialog(this, 0, this, mYear, mMonth - 1, mDay);
        dpd.show();
    }

    @OnClick(R.id.time_container)
    void setTime() {
        TimePickerDialog tpd = new TimePickerDialog(this, 0, this, mHour, mMinute, true);
        tpd.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mYear = year;
        mMonth = monthOfYear + 1;
        mDay = dayOfMonth;
        mDate = mYear + "/" + mMonth + "/" + mDay;
        tv_set_date.setText("Date\n" + mDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;
        if (mMinute < 10) {
            mTime = mHour + ":0" + mMinute;
        } else {
            mTime = mHour + ":" + mMinute;
        }
        tv_set_time.setText("Time\n" + mTime);
    }

    @OnClick(R.id.ibtn_ok)
    void save() {
        mTitle = et_event.getText().toString();

        Log.d(TAG, "year:" + mYear + ",month:" + mMonth + ",day:" + mDay + ",hour:" + mHour + ",minute:" + mMinute);
        Log.d(TAG, "title:" + mTitle + ",date:" + mDate + ",time:" + mTime);

        if (et_event.getText().toString().equals("")) {
            Toast.makeText(this, "The event can not be blank!", Toast.LENGTH_SHORT).show();
            return;
        }

        TipBean bean = new TipBean(mTitle, mDate, mTime);
        ClockDBHelper db = new ClockDBHelper(this);

        mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.MONTH, mMonth - 1);
        mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);



        if (ID == -1) {
            ID = db.addRemind(bean);
        } else {
            db.updateReminder(bean, ID);
        }
        new TipsReciver().setAlarm(this, mCalendar, ID);
        //new RemindReciver().setAlarm(this, mCalendar, ID);
        Log.d(TAG, "ID:" + ID);
        finish();
    }
}
