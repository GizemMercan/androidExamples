package info.androidhive.slidingmenu;

import info.androidhive.slidingmenu.model.WebServiceParameterItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddFridgeFragment extends Fragment {

	private TextView textViewFridgeName;
	private EditText editTextFridgeName;
	private TextView textViewFridgeKey;
	private EditText editTextFridgeKey;
	private TextView textViewBrand;
	private EditText editTextBrand;
	private TextView textViewFridgeAdding;
	private ProgressDialog progress;
	private Button addButton;
	private String fridgeName="";
	private String fridgeKey="";
	private String fridgeBrand="";

	public AddFridgeFragment(){}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*
		 UserSessionManager session;
		session = new UserSessionManager(getActivity());
		HashMap<String, String> user = session.getUserDetails();
		// get email
		String email = user.get(UserSessionManager.KEY_EMAIL);
		String pass = user.get(UserSessionManager.KEY_PASS);
		*/
		
		View rootView = inflater.inflate(R.layout.fragment_add_fridge, container, false);
		
		textViewFridgeName =(TextView) rootView.findViewById(R.id.textViewFridgeName);
		textViewFridgeKey =(TextView) rootView.findViewById(R.id.textViewFridgeKey);
		textViewBrand =(TextView) rootView.findViewById(R.id.textViewBrand);
		textViewFridgeAdding =(TextView) rootView.findViewById(R.id.ChooseFridgeTextView);
		editTextFridgeName=(EditText) rootView.findViewById(R.id.editTextFridgeName);
		editTextFridgeKey=(EditText) rootView.findViewById(R.id.editTextFridgeKey);
		editTextBrand=(EditText) rootView.findViewById(R.id.editTextBrand);
		addButton=(Button) rootView.findViewById(R.id.buttonAddFridge);
		addButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					// Get username, password from EditText
					fridgeName = editTextFridgeName.getText().toString();
					fridgeKey = editTextFridgeKey.getText().toString();
					fridgeBrand = editTextBrand.getText().toString();
					final ProgressDialog myPd_ring=ProgressDialog.show(getActivity(), "Please wait", "Fridge Adding please wait..", true);
					myPd_ring.setCancelable(true);
					new Thread(new Runnable() {  
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try
							{
								Thread.sleep(1000);
							}catch(Exception e){}
							myPd_ring.dismiss();
						}
					}).start();
					 WebServiceCall ws=null;
					List<WebServiceParameterItem> properties = null;
					ws = new WebServiceCall();
					properties = new ArrayList<WebServiceParameterItem>();
					WebServiceParameterItem property1 = new WebServiceParameterItem("fridgeName",fridgeName);
					WebServiceParameterItem property2 = new WebServiceParameterItem("fridgeKey",fridgeKey);
					WebServiceParameterItem property3 = new WebServiceParameterItem("brand",fridgeBrand);

					properties.add(property1);
					properties.add(property2);
					properties.add(property3);
					 
					 ws.CallInsertUpdateWebservice("InsertFridge",properties);
					 textViewFridgeAdding.setTextColor(Color.GREEN);
					 textViewFridgeAdding.setText("Adding is successful");

				}
			});


		return rootView;
	}
}
