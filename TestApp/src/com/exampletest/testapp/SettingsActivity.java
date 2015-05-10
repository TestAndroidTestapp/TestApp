package com.exampletest.testapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends Activity {
	static final String PREFS_NAME = "com.exampletest.testapp";
	EditText etFrequency;
	Button btnSave;
	SharedPreferences mSettings;

	private void loadPrefs() {
		etFrequency.setText(String.valueOf(mSettings.getInt("frequency", 20)));
	}

	private void savePrefs() {
		SharedPreferences.Editor editor = mSettings.edit();
		editor.putInt("frequency",
				Integer.valueOf(etFrequency.getText().toString()));
		editor.commit();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		etFrequency = (EditText) findViewById(R.id.etFrequency);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				savePrefs();
				SettingsActivity.this.finish();
			}

		});

		mSettings = getSharedPreferences(PREFS_NAME, 0);
		loadPrefs();

	}

}
