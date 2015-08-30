package com.flying.xiaopo.shader.Reciver;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

import com.flying.xiaopo.shader.Activity.NotificationStartedActivity;
import com.flying.xiaopo.shader.Bean.TipBean;
import com.flying.xiaopo.shader.ClockDBHelper;
import com.flying.xiaopo.shader.R;

import java.util.Calendar;

/**
 * Created by lenovo on 2015/6/13.
 */
public class TipsReciver extends WakefulBroadcastReceiver {
	private AlarmManager mAlarmManager;
	private PendingIntent mPendingIntent;
	public static final String REMIND_ID = "id";

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Receive", Toast.LENGTH_SHORT).show();
		int recive_id = intent.getIntExtra(REMIND_ID, -1);
		String text;
		if (recive_id != -1) {
			TipBean bean = new ClockDBHelper(context).getRemind(intent.getIntExtra(REMIND_ID, -1));
			text = bean.getTitle();
		} else {
			text = "Sorry";
		}
		Intent mIntent =
				new Intent(context, NotificationStartedActivity.class);
		mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

		PendingIntent pendingIntent = PendingIntent.getActivity(context, recive_id, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
				.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
				.setSmallIcon(R.mipmap.ic_launcher)
				.setColor(Color.parseColor("#523532"))
				.setContentTitle(context.getResources().getString(R.string.app_name))
				.setContentText(text)
				.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
				.setContentIntent(pendingIntent)
				.setAutoCancel(true)
				.setOnlyAlertOnce(true);

		NotificationManager mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mManager.notify(recive_id, mBuilder.build());
	}

	public void setAlarm(Context context, Calendar calendar, int ID) {
		mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		Intent intent = new Intent(context, TipsReciver.class);
		intent.putExtra(REMIND_ID, ID);
		mPendingIntent = PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);

		Calendar c = Calendar.getInstance();
		long currentTime = c.getTimeInMillis();
//        long diffTime = calendar.getTimeInMillis() - currentTime;

		mAlarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), mPendingIntent);

	}

	public void cancelAlarm(Context context, int ID) {
		mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		Intent intent = new Intent(context, TipsReciver.class);

		mPendingIntent = PendingIntent.getBroadcast(context, ID, intent, 0);
		mAlarmManager.cancel(mPendingIntent);
	}
}
