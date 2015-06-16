package com.example.webservicedeneme;

import java.io.IOException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.ksoap2.*;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.xmlpull.v1.XmlPullParserException;


@SuppressLint("NewApi")
public class MainActivity extends Activity {

	private static final String SOAP_ACTION = "http://smartfridgeapp.com/webservices/i3";
	private static final String METHOD_NAME = "i3";
	private static final String NAMESPACE = "http://smartfridgeapp.com/webservices/";
	private static final String URL = "http://smartfridgeapp.com/Service1.asmx";
	//private static final String URL = "http://192.168.43.45:3333/Service1.asmx";
	//192.168.2.16

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);      
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.activity_main);

		final Button btn_goster = (Button) findViewById(R.id.btn_goster);
		final EditText edt_goster = (EditText) findViewById(R.id.edt_goster);

		btn_goster.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				//request.addProperty("productName", "Milk");
				// request.addProperty("isAdding", true);
				String deneme="APA91bE6WbsgiEKFiBqjm1PR6uSKxGzXkbfaD5JT7BFVuN7OEPooSTdfW8XXBMorzwe4lXb9METk4mKdtISfpHiHTRsf_sRUffhaVC4TgMPUTcrtMXBIfSlPRG8Fc9HaFZS7gGLkv_12V9Toz0smRQjAlmrmWtUS7tS7q1KedYuf5MHKizxc0j0";
				//request.addProperty("regID", deneme);
				request.addProperty("TagID", "gizem");
				request.addProperty("TagData", "ipek");

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);

				AndroidHttpTransport httpTransport = new AndroidHttpTransport(URL);

				SoapPrimitive result = null;
				try {
					httpTransport.call(SOAP_ACTION, envelope);
					result = (SoapPrimitive) envelope.getResponse();

					//edt_goster.setText(result.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					edt_goster.setText(e.getMessage());
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					edt_goster.setText(e.getMessage());
				}

			}

		});

	}


}
