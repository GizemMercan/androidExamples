package info.androidhive.slidingmenu;

import static info.androidhive.slidingmenu.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static info.androidhive.slidingmenu.CommonUtilities.EXTRA_MESSAGE;
import static info.androidhive.slidingmenu.CommonUtilities.SENDER_ID;
import static info.androidhive.slidingmenu.CommonUtilities.SERVER_URL;
import info.androidhive.slidingmenu.model.WebServiceParameterItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	Button btnLogin,btnRegister;

	EditText txtUsername, txtPassword;

	private String TAG = " PushActivity";	

	AsyncTask<Void, Void, Void> mRegisterTask;

	// User Session Manager Class
	UserSessionManager session;
	private List<String> userList;
	private List<String> userNameSurnameList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		checkNotNull(SERVER_URL, "SERVER_URL");
		checkNotNull(SENDER_ID, "SENDER_ID");
		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);
		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		GCMRegistrar.checkManifest(this);
		setContentView(R.layout.activity_login); 

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		registerReceiver(mHandleMessageReceiver,
				new IntentFilter(DISPLAY_MESSAGE_ACTION));
		final String regId = GCMRegistrar.getRegistrationId(this);

		if (regId.equals("")) {
			// Automatically registers application on startup.
			GCMRegistrar.register(this, SENDER_ID);
		} else {
			// Device is already registered on GCM, check server.
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.
				//msg.append(getString(R.string.already_registered) + "\n");
			} else {
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						boolean registered = false;
						try {
							registered = ServerUtilities.register(context, regId);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// At this point all attempts to register with the app
						// server failed, so we need to unregister the device
						// from GCM - the app will try to register again when
						// it is restarted. Note that GCM will send an
						// unregistered callback upon completion, but
						// GCMIntentService.onUnregistered() will ignore it.
						if (!registered) {
							GCMRegistrar.unregister(context);
						}
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
			}
		}

		// User Session Manager
		session = new UserSessionManager(getApplicationContext());                

		// get Email, Password input text
		txtUsername = (EditText) findViewById(R.id.txtUsername);
		txtPassword = (EditText) findViewById(R.id.txtPassword); 

		Toast.makeText(getApplicationContext(), 
				"User Login Status: " + session.isUserLoggedIn(), 
				Toast.LENGTH_LONG).show();


		// User Login button
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnRegister = (Button) findViewById(R.id.btnRegister);


		// Login button click event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// Get username, password from EditText
				String email = txtUsername.getText().toString();
				String password = txtPassword.getText().toString();
				//email="GMERCAN91@GMAIL.COM";
				//password="GIZ";
				// Validate if username, password is filled				
				if(email.trim().length() > 0 && password.trim().length() > 0){
					/*web service den cekilen kýsým olacak sonra sifre ve username karsýlastýrýlacak*/
					WebServiceCall ws = new WebServiceCall();
					List<WebServiceParameterItem> properties = new ArrayList<WebServiceParameterItem>();
					WebServiceParameterItem property1 = new WebServiceParameterItem("username",email);
					WebServiceParameterItem property2 = new WebServiceParameterItem("password",password);
					properties.add(property1);
					properties.add(property2);
					userList= ws.CallGetWebservice("getUserConfirmation",properties);


					if(userList.get(0).equals("1")){


						userNameSurnameList= ws.CallGetWebservice("getUserInformation",properties);

						String myName="";
						myName=userNameSurnameList.get(0)+" "+userNameSurnameList.get(1);
						//myEmail="GMERCAN91@GMAIL.COM";
						//web service den diger bilgiler cekilip ekrana hosgeldin mesajýyla birlikte basýlabilir.
						session.createUserLoginSession(myName, email,password);
						// Starting MainActivity
						Intent i = new Intent(getApplicationContext(), MainActivity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

						// Add new Flag to start new Activity
						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(i);

						finish();

					}else{

						// username / password doesn't match
						Toast.makeText(getApplicationContext(), "Username/Password is incorrect", Toast.LENGTH_LONG).show();

					}				
				}else{

					// user didn't entered username or password
					Toast.makeText(getApplicationContext(), "Please enter username and password", Toast.LENGTH_LONG).show();

				}

			}
		});

		// Register button click event
		btnRegister.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
				//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				// Add new Flag to start new Activity
				//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);

			}
		});
	} 
	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		unregisterReceiver(mHandleMessageReceiver);
		GCMRegistrar.onDestroy(getApplicationContext());
		super.onDestroy();
	}

	private void checkNotNull(Object reference, String name) {
		if (reference == null) {
			throw new NullPointerException(
					getString(R.string.error_config, name));
		}
	}

	private final BroadcastReceiver mHandleMessageReceiver =
			new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
		}
	};
}