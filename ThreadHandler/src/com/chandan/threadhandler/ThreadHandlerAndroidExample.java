package com.chandan.threadhandler;
/* Author @chandan_ch 
 *github: chandanch
 *
 */
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ThreadHandlerAndroidExample extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.android_example_thread_handler);
		
		final Button GetServerData = (Button) findViewById(R.id.GetServerData);
		
		
		// On button click call this listener
		GetServerData.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//ALERT MESSAGE
				Toast.makeText(getBaseContext(),
						"Please wait, connecting to server.",
						Toast.LENGTH_SHORT).show();
				// Create Inner Thread Class
				Thread background = new Thread(new Runnable() {
					
					private final HttpClient Client = new DefaultHttpClient();
			        private String URL = "http://androidexample.com/media/webservice/getPage.php";
					
					// After call for background.start this run method call
					public void run() {
						try {

							String SetServerString = "";
							HttpGet httpget = new HttpGet(URL);
			                ResponseHandler<String> responseHandler = new BasicResponseHandler();
			                SetServerString = Client.execute(httpget, responseHandler);
							threadMsg(SetServerString);

						} catch (Throwable t) {
							// just end the background thread
							Log.i("Animation", "Thread  exception " + t);
						}
					}

					private void threadMsg(String msg) {
						if (!msg.equals(null) && !msg.equals("")) {
							Message msgObj = handler.obtainMessage();
							Bundle b = new Bundle();
							b.putString("message", msg);
							msgObj.setData(b);
							handler.sendMessage(msgObj);
						} 
					}

					// Define the Handler that receives messages from the
					// thread and update the
					// progress
					private final Handler handler = new Handler() {
						public void handleMessage(Message msg) {
							
							String aResponse = msg.getData().getString("message");

							if ((null != aResponse)) {
								//ALERT MESSAGE
								Toast.makeText(
										getBaseContext(),
										"Server Response: "+aResponse,
										Toast.LENGTH_SHORT).show();
							}
							else
							{
								    //ALERT MESSAGE
									Toast.makeText(
											getBaseContext(),
											"Not Got Response From Server.",
											Toast.LENGTH_SHORT).show();
							}	

						}
					};

				});
				// Start Thread 
				background.start();  //After call start method thread called run Method
			}
		});
		
	}

	

}
