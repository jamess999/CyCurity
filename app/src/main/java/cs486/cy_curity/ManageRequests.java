package cs486.cy_curity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Map;

public class ManageRequests extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_requests);

        SharedPreferences sp = getSharedPreferences("docks", MODE_PRIVATE);
        Map<String, ?> allEntries = sp.getAll();
        LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout);

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            CommunicateTask tsk = new CommunicateTask(this);
            String temp = entry.getValue().toString();
            String[] info = temp.split(";");
            // 0 title
            // 1 ip
            // 2 user
            // 3 pass
            tsk.PORT = 8244;
            tsk.IP = info[1];
            try {
                tsk.send("requests," + entry.getKey() + ";;" + info[2] + ";" + info[3]);
            } catch (Exception e) {
            }
        }
    }

	public void onClick(View view)
	{

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
        private Context mContext;
        public CommunicateTask (Context context)
        {
            mContext = context;
        }
		@Override
		protected void onPostExecute(String result)
		{
            if(result == null)
            {
                return;
            }
            LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout);
            Log.d("request values", result);
            String[] input = result.split(",");
            for(int i=1; i<input.length; i++)
            {
                String[] req = input[i].split(";");
                //0 user
                //1 pass
                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setWeightSum(8);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                TextView t = new TextView(mContext);
                LinearLayout.LayoutParams param =new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
                param.weight = 4;
                param.rightMargin = 5;
                param.leftMargin = 5;
                param.topMargin = 5;
                t.setLayoutParams (param);
                t.setText(req[0] + " @ " + input[0]);
                t.setTextAppearance(mContext, android.R.style.TextAppearance_Large);
                linearLayout.addView(t);

                Button b1 = new Button(mContext);
                param =new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
                param.weight = 2;
                param.rightMargin = 5;
                param.topMargin = 5;
                b1.setText("Allow");
                b1.setTag(input[0] + ";;;" + input[i]);
                b1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        SharedPreferences sp = getSharedPreferences("docks", MODE_PRIVATE);
                        RespondTask tsk = new RespondTask();
                        String input = (String) v.getTag();
                        String[] args = input.split(";;;");
                        String temp = sp.getString(args[0], null);
                        String[] info = temp.split(";");
                        // 0 title
                        // 1 ip
                        // 2 user
                        // 3 pass
                        tsk.PORT = 8244;
                        tsk.IP = info[1];
                        try {
                            tsk.send("accept," + args[1]);
                        } catch (Exception e) {
                        }
                        LinearLayout lin = (LinearLayout) v.getParent();
                        lin.setVisibility(View.GONE);
                    }
                });
                b1.setLayoutParams (param);
                linearLayout.addView(b1);

                Button b2 = new Button(mContext);
                param =new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
                param.weight = 2;
                param.rightMargin = 5;
                param.topMargin = 5;
                b2.setText("Deny");
                b2.setTag(input[0] + ";;;" + input[i]);
                b2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v)
                    {
                        SharedPreferences sp = getSharedPreferences("docks", MODE_PRIVATE);
                        RespondTask tsk = new RespondTask();
                        String input = (String) v.getTag();
                        String[] args = input.split(";;;");
                        String temp = sp.getString(args[0], null);
                        String[] info = temp.split(";");
                        // 0 title
                        // 1 ip
                        // 2 user
                        // 3 pass
                        tsk.PORT = 8244;
                        tsk.IP = info[1];
                        try {
                            tsk.send("deny," + args[1]);
                        }
                        catch (Exception e)
                        {
                        }
                        LinearLayout lin = (LinearLayout) v.getParent();
                        lin.setVisibility(View.GONE);
                    }
                });
                b2.setLayoutParams (param);
                linearLayout.addView(b2);

                layout.addView(linearLayout);
            }
        }
	}

    class RespondTask extends AsyncServerRequest
    {
        @Override
        protected void onPostExecute(String result) {


        }

    }
}
