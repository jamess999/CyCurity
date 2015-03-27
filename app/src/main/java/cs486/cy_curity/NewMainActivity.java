package cs486.cy_curity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.util.Log;

import java.util.Map;

public class NewMainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainback);
        SharedPreferences sp = getSharedPreferences("docks", MODE_PRIVATE);
        Map<String, ?> allEntries = sp.getAll();
        LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout);

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            //entry.getValue().toString()
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setWeightSum(8);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            TextView t = new TextView(this);
            LinearLayout.LayoutParams param =new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
            param.weight = 4;
            param.rightMargin = 5;
            param.leftMargin = 5;
            param.topMargin = 5;
            t.setLayoutParams (param);
            t.setText(entry.getKey());
            t.setTextAppearance(this, android.R.style.TextAppearance_Large);
            linearLayout.addView(t);



            ToggleButton b = new ToggleButton(this);
            param =new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
            param.weight = 2;
            param.rightMargin = 5;
            param.topMargin = 5;
            b.setTag(entry.getKey());
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String str;
                    if(((ToggleButton) v).isChecked()) {
                        str = "lock,";
                    } else {
                        str = "unlock,";
                    }

                    CommunicateTask tsk = new CommunicateTask();

                    SharedPreferences s = getSharedPreferences("docks", MODE_PRIVATE);

                    String temp = s.getString((String) v.getTag(), null);
                    String[] info = temp.split(";");
                    // 0 title
                    // 1 ip
                    // 2 user
                    // 3 pass
                    tsk.PORT = 8244;
                    tsk.IP = info[1];
                    str = str + info[2] + ";" + info[3];
                    try {
                        tsk.send(str);
                    } catch (Exception e) {}
                }
            });
            b.setLayoutParams (param);
            b.setTextOn("Locked");
            b.setTextOff("Open");
            b.setChecked(false);
            linearLayout.addView(b);


            Button b1 = new Button(this);
            param =new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
            param.weight = 2;
            param.rightMargin = 5;
            param.topMargin = 5;
            b1.setText("Edit");
            b1.setTag(entry.getKey());
            b1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), RequestActivity.class);
                    i.putExtra("Edit",(String) v.getTag());
                    startActivity(i);
                }
            });
            b1.setLayoutParams (param);
            linearLayout.addView(b1);

            layout.addView(linearLayout);
        }

	}

	public void onClick(View view)
	{
        Intent i = new Intent(this, RequestActivity.class);
        startActivity(i);
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
