package com.exampletest.testapp;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	static final String PREFS_NAME = "com.exampletest.testapp";
	private SharedPreferences mSettings;
	private BroadcastReceiver rec_data;
	public SpeedometerView speedometer;
	public LineGraphSeries<DataPoint> mSeries;
	public double graphLastXValue = 0d;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		rec_data = new TestReceiver();
		registerReceiver(rec_data, new IntentFilter(
				"com.exampletest.testapp.TEST_DATA"));

		mSettings = getSharedPreferences(PREFS_NAME, 0);
		Intent intt_serv = new Intent(this, TestService.class);
		intt_serv.putExtra("frequency", mSettings.getInt("frequency", 20));
		startService(intt_serv);

		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		unregisterReceiver(rec_data);
		super.onPause();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// orientation
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		speedometer = (SpeedometerView) findViewById(R.id.speedometer);

		speedometer.setLabelConverter(new SpeedometerView.LabelConverter() {
			@Override
			public String getLabelFor(double progress, double maxProgress) {
				return String.valueOf((int) Math.round(progress));
			}
		});

		// configure value range and ticks
		speedometer.setMaxSpeed(200);
		speedometer.setMajorTickStep(20);
		speedometer.setMinorTicks(2);

		// Configure value range colors
		speedometer.addColoredRange(20, 100, Color.GREEN);
		speedometer.addColoredRange(100, 140, Color.YELLOW);
		speedometer.addColoredRange(140, 200, Color.RED);

		GraphView graph = (GraphView) findViewById(R.id.graph);
		mSeries = new LineGraphSeries<DataPoint>();
		graph.addSeries(mSeries);
		graph.getViewport().setXAxisBoundsManual(true);
		graph.getViewport().setMinX(0);
		graph.getViewport().setMaxX(40);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intt = new Intent(MainActivity.this, SettingsActivity.class);
			startActivity(intt);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
