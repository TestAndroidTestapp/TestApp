package com.exampletest.testapp;

import java.util.Calendar;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class TestService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		final int frequency = intent.getIntExtra("frequency", 20);
		final Calendar c = Calendar.getInstance();

		final Handler handler = new Handler();
		Runnable runable = new Runnable() {

			@Override
			public void run() {
				try {
					final Calendar c = Calendar.getInstance();
					long curr_time = c.getTimeInMillis();
					double y1 = Math.sin(curr_time);
					double y2 = Math.cos(curr_time) * Math.cos(curr_time);
					double y = y1 + y2;

					Intent intt = new Intent(
							"com.exampletest.testapp.TEST_DATA");
					intt.putExtra("value", y * 100); // умножим на 100 дл€
														// лучшего отображени€ в
														// спидометре
					sendBroadcast(intt);

					handler.postDelayed(this, frequency);
				} catch (Exception e) {
					// TODO: handle exception
					Log.d("[TestService] ERROR", e.getMessage());
				}

			}
		};
		handler.postDelayed(runable, frequency);

		return super.onStartCommand(intent, flags, startId);
	}

}
