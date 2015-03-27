package cs486.cy_curity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import java.net.UnknownHostException;
import android.os.AsyncTask;

public class AsyncServerRequest extends AsyncTask<String, Void, String>
{

	// CHANGE INFO HERE
	public String IP = "173.240.196.14";
	public int PORT = 9465;

	public void send(String input)
	{
		String[] sendInput = new String[1];
		sendInput[0] = input;
		execute(input);
	}

	@Override
	protected String doInBackground(String... params)
	{
		// Fetch the JSON
		Socket socket = null;

		try
		{
			InetAddress serverAddr = InetAddress.getByName(IP);

			socket = new Socket(serverAddr, PORT);

		} catch (UnknownHostException e1)
		{
			e1.printStackTrace();
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}

		try
		{
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())), true);
			out.println(params[0]);

		} catch (UnknownHostException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		String retval = null;

		try
		{
			BufferedReader inFromServer = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			retval = inFromServer.readLine();

		} catch (UnknownHostException e1)
		{
			e1.printStackTrace();
		} catch (IOException e1)
		{
			e1.printStackTrace();
		} catch (NullPointerException e1)
        {
            retval = "No Connection";
        }

		return retval;
	}

}
