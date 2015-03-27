package cs486.cy_curity;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.os.Build;
import cs486.cy_curity.AsyncServerRequest;

public class SettingsActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		SharedPreferences s = getSharedPreferences("settings", MODE_PRIVATE);
		
		String temp = s.getString("hostIp", null);
		if (temp != null) 
		{
			EditText hP = (EditText) findViewById(R.id.hostIp);
			hP.setText(temp);
		}
		temp = s.getString("port", null);
		if (temp != null) 
		{
			EditText p = (EditText) findViewById(R.id.port);
			p.setText(temp);
		}
	}
	
	public void onClick(View v)
	{
		SharedPreferences.Editor editor = getSharedPreferences("settings", MODE_PRIVATE).edit();
		EditText hP = (EditText) findViewById(R.id.hostIp);
		editor.putString("hostIp", hP.getText().toString());
		EditText p = (EditText) findViewById(R.id.port);
		editor.putString("port", p.getText().toString());
		editor.commit();
		
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle item selection
		Intent i = null;
		switch (item.getItemId())
		{
			case R.id.main:
				i = new Intent(this, MainActivity.class);
				break;
		}

		if (i != null)
		{
			startActivity(i);
		}

		return false;
	}
}
