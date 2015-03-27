package cs486.cy_curity;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onClick(View view)
	{
		String str = "invalid";
		switch (view.getId())
		{
		case R.id.unlockButton:
			str = "unlock";
			break;
		case R.id.lockButton:
			str = "lock";
			break;
		}

		CommunicateTask tsk = new CommunicateTask();

		SharedPreferences s = getSharedPreferences("settings", MODE_PRIVATE);

		String temp = s.getString("hostIp", null);
		if (temp != null)
			tsk.IP = temp;
		temp = s.getString("port", null);
		Log.d("portPref", (temp != null) ? temp : "null");
		if (temp != null)
		{
			tsk.PORT = Integer.parseInt(temp);
			Log.d("port", String.valueOf(tsk.PORT));
		}

		try {
			tsk.send(str);
		} catch (Exception e) {}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle item selection
		Intent i = null;
		switch (item.getItemId()) {
            case R.id.settings:
                i = new Intent(this, SettingsActivity.class);
                break;

            case R.id.request:
                i = new Intent(this, RequestActivity.class);
                break;


            case R.id.newMain:
                i = new Intent(this, NewMainActivity.class);
                break;

            case R.id.managePermissions:
                i = new Intent(this, ManagePermissions.class);
                break;

            case R.id.manageRequests:
                i = new Intent(this, ManageRequests.class);
                break;

        }

		if (i != null)
		{
			startActivity(i);
		}

		return false;
	}

	class CommunicateTask extends AsyncServerRequest
	{
		@Override
		protected void onPostExecute(String result)
		{

		}
	}
}
