package info.androidhive.slidingmenu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity {

	Button btnRegister;
	
	EditText txtUsernameReg, txtPasswordReg , txtEmail,txtFridgeName,txtFridgeRegCode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		txtUsernameReg = (EditText) findViewById(R.id.txtUsernameReg);
		txtPasswordReg = (EditText) findViewById(R.id.txtPasswordReg); 
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		txtFridgeName = (EditText) findViewById(R.id.txtFridgeName);
		txtFridgeRegCode = (EditText) findViewById(R.id.txtFridgeRegCode); 
		
		btnRegister = (Button) findViewById(R.id.btnRegisterReg);
		
        // Register button click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
        	
			@Override
			public void onClick(View arg0) {
				
	        	String username = txtUsernameReg.getText().toString();
				String password = txtPasswordReg.getText().toString();
	        	String email = txtEmail.getText().toString();
				String fridgeName = txtFridgeName.getText().toString();
				String FridgeRegCode = txtFridgeRegCode.getText().toString();
				
				// TODO yukarýdakiler Web servise kayýt edilecek
				// TODO session açýlýp sessiona kaydedilecek , session java düzenlenecek.
				
				Intent i = new Intent(getApplicationContext(), MainActivity.class);

				startActivity(i);
				finish();
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
