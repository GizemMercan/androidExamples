package info.androidhive.slidingmenu;

import info.androidhive.slidingmenu.model.WebServiceParameterItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseFridgeFragment extends Fragment {


	private TextView textViewFridgeChoosing;
	private Button addButton;
	private Spinner spinner1;
	
	private List<String> fridgeInside;
	private List<String> fridgeList;


	public ChooseFridgeFragment(){}

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
		
		View rootView = inflater.inflate(R.layout.fragment_choose_fridge, container, false);
		
		spinner1 = (Spinner) rootView.findViewById(R.id.spinner1);
		textViewFridgeChoosing =(TextView) rootView.findViewById(R.id.ChooseFridgeTextView);
		addButton=(Button) rootView.findViewById(R.id.buttonChooseFridge);
		WebServiceCall ws=null;
		List<WebServiceParameterItem> properties = null;
		ws = new WebServiceCall();
		properties = new ArrayList<WebServiceParameterItem>();
		
		UserSessionManager session;
		session = new UserSessionManager(getActivity());
		HashMap<String, String> user = session.getUserDetails();
		HashMap<String, Integer> userFridge = session.getUserFridge();

		// get email
		String email = user.get(UserSessionManager.KEY_EMAIL);
		String pass = user.get(UserSessionManager.KEY_PASS);
		int fridgeNumber=userFridge.get(UserSessionManager.KEY_FRIDGE);
		
		WebServiceParameterItem property1 = new WebServiceParameterItem("useremail",email);
		WebServiceParameterItem property2 = new WebServiceParameterItem("pass",pass);
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


		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(dataAdapter);
		spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());

		addButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					UserSessionManager session;
					session = new UserSessionManager(getActivity());
						
					String tempSplittedArray[]=new String[2];
					tempSplittedArray=spinner1.getSelectedItem().toString().split("-");
					int fridgeID=Integer.parseInt(tempSplittedArray[0].toString());
					session.createUserFridgeSession(fridgeID);
					HashMap<String, Integer> userFridge = session.getUserFridge();

					Log.d("FRIDGE KEY---->>",userFridge.get(UserSessionManager.KEY_FRIDGE).toString());

				}
			});


		return rootView;
	}
}
