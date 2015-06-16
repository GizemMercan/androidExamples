package info.androidhive.slidingmenu;

import info.androidhive.slidingmenu.model.WebServiceParameterItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AfterLoginActivity extends Activity {


	// User Session Manager Class
	UserSessionManager session;

	// Button Logout
	Button btnLogout;
	Button btnHome;
	
	private TextView textViewFridgeChoosing;
	private Button addButton;
	private Spinner spinner1;
	
	private List<String> fridgeInside;
	private List<String> fridgeList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_after_login);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			}

		// Session class instance
		session = new UserSessionManager(getApplicationContext());

		TextView lblName = (TextView) findViewById(R.id.lblName);
		TextView lblEmail = (TextView) findViewById(R.id.lblEmail);

		// Button logout
		btnLogout = (Button) findViewById(R.id.btnLogout);
		btnHome = (Button) findViewById(R.id.btnHome);
		spinner1 = (Spinner) findViewById(R.id.spinner2);
		textViewFridgeChoosing =(TextView) findViewById(R.id.ChooseFridgeTextView);
		

	/*	Toast.makeText(getApplicationContext(), 
				"User Login Status: " + session.isUserLoggedIn(), 
				Toast.LENGTH_LONG).show();*/



		// Check user login
		// If User is not logged in , This will redirect user to LoginActivity.
		if(session.checkLogin())
			finish();

		// get user data from session
		else
		{
			
			HashMap<String, String> user = session.getUserDetails();

			// get name
			String name = user.get(UserSessionManager.KEY_NAME);

			// get email
			String email = user.get(UserSessionManager.KEY_EMAIL);

			String pass = user.get(UserSessionManager.KEY_PASS);
			Log.d("password", pass);

			WebServiceCall ws=null;
			List<WebServiceParameterItem> properties = null;
			ws = new WebServiceCall();
			properties = new ArrayList<WebServiceParameterItem>();
			
			final UserSessionManager session;
			session = new UserSessionManager(getApplicationContext());
			HashMap<String, String> user2 = session.getUserDetails();
			HashMap<String, Integer> userFridge = session.getUserFridge();

			// get email
			String email2 = user2.get(UserSessionManager.KEY_EMAIL);
			String pass2 = user2.get(UserSessionManager.KEY_PASS);
			int fridgeNumber=userFridge.get(UserSessionManager.KEY_FRIDGE);
			
			WebServiceParameterItem property1 = new WebServiceParameterItem("useremail",email2);
			WebServiceParameterItem property2 = new WebServiceParameterItem("pass",pass2);
			properties.add(property1);
			properties.add(property2);
			fridgeList = new ArrayList<String>();
			fridgeList= ws.CallGetWebservice("getAllFridge",properties);
			List<String> list=new ArrayList<String>();
			String TempString="";
			for(int i=1;i<fridgeList.size()+2;i++)
			{
				if(i % 2 ==0 && i!=0 )
					list.add(fridgeList.get(i-2).toString()+"-"+fridgeList.get(i-1).toString());

			}


			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
					android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner1.setAdapter(dataAdapter);
			spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
			// Show user data on activity
			lblName.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
			lblEmail.setText(Html.fromHtml("Email: <b>" + email + "</b>"));

		}

		btnLogout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// Clear the User session data
				// and redirect user to LoginActivity
				session.logoutUser();
				finish();
			}
		});
		btnHome.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// Clear the User session data
				// and redirect user to LoginActivity
				UserSessionManager session;
				session = new UserSessionManager(getApplicationContext());
					
				String tempSplittedArray[]=new String[2];
				tempSplittedArray=spinner1.getSelectedItem().toString().split("-");
				int fridgeID=Integer.parseInt(tempSplittedArray[0].toString());
				session.createUserFridgeSession(fridgeID);
				HashMap<String, Integer> userFridge = session.getUserFridge();

				Log.d("FRIDGE KEY---->>",userFridge.get(UserSessionManager.KEY_FRIDGE).toString());
				
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				// Add new Flag to start new Activity
				//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


				startActivity(i);
			}
		});


	
	}
	

}