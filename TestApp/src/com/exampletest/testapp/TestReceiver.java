package com.exampletest.testapp;

import com.jjoe64.graphview.series.DataPoint;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class TestReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle extras = intent.getExtras();

		if (extras != null) {

			final double value = extras.getDouble("value");

			Log.d("RECEIVER VALUE ", String.valueOf(value));

			((MainActivity) context).runOnUiThread(new Runnable() {

				@Override
				public void run() {
					((MainActivity) context).speedometer.setSpeed(value);

					((MainActivity) context).graphLastXValue += 1d;
					((MainActivity) context).mSeries.appendData(new DataPoint(
							((MainActivity) context).graphLastXValue, value),
							true, 40);
				}
			});

		}

	}

}
