package cs486.cy_curity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class RequestActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        SharedPreferences s = getSharedPreferences("docks", MODE_PRIVATE);
        Intent i = getIntent();
        String key = i.getStringExtra("Edit");
        if(key != null)
        {
            String value = s.getString(key,null);
            String[] info = value.split(";");
            // 0 title
            // 1 ip
            // 2 user
            // 3 pass
            EditText text = (EditText) findViewById(R.id.title);
            if(info != null && info.length == 4) {
                if (info[0] != null)
                    text.setText(info[0]);
                text = (EditText) findViewById(R.id.host);
                if (info[1] != null)
                    text.setText(info[1]);
                text = (EditText) findViewById(R.id.username);
                if (info[2] != null)
                    text.setText(info[2]);
                text = (EditText) findViewById(R.id.password);
                if (info[3] != null)
                    text.setText(info[3]);
            }

            LinearLayout layout = (LinearLayout)findViewById(R.id.container);
            Button b1 = new Button(this);
            LinearLayout.LayoutParams param =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            param.rightMargin = 5;
            param.topMargin = 5;
            b1.setText("Delete");
            b1.setTag(key);
            b1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = getSharedPreferences("docks", MODE_PRIVATE).edit();
                    if((String) v.getTag() != null)
                    {
                        editor.remove((String) v.getTag());
                    }
                    editor.commit();
                    //Log.d(t.toString(),setting);
                    Intent i = new Intent(getApplicationContext(), NewMainActivity.class);
                    startActivity(i);
                }
            });
            b1.setLayoutParams (param);
            layout.addView(b1);
        }


    }
	
	public void onClick(View v)
	{
		SharedPreferences.Editor editor = getSharedPreferences("docks", MODE_PRIVATE).edit();

        Intent input = getIntent();
        String key = input.getStringExtra("Edit");
        if(key != null)
        {
            editor.remove(key);
        }

        EditText t = (EditText) findViewById(R.id.title);
		EditText hP = (EditText) findViewById(R.id.host);
        EditText u = (EditText) findViewById(R.id.username);
        EditText p = (EditText) findViewById(R.id.password);

        String setting = t.getText().toString() + ";" + hP.getText().toString() + ";" + u.getText().toString() + ";" + p.getText().toString();
        editor.putString(t.getText().toString(),setting);

		editor.commit();

        CommunicateTask tsk = new CommunicateTask();
        tsk.IP = hP.getText().toString();
        Log.d("HOST IP", hP.getText().toString());
        tsk.PORT = 8244;
        String str = "request," + u.getText().toString() + ";" + p.getText().toString();
        try {
            tsk.send(str);
        } catch (Exception e) {}

		//Log.d(t.toString(),setting);
		Intent i = new Intent(this, NewMainActivity.class);
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
				i = new Intent(this, NewMainActivity.class);
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
