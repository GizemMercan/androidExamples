package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import info.androidhive.slidingmenu.model.WebServiceParameterItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.SearchView;
import android.app.SearchManager;
import android.content.SharedPreferences;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;


public class SearchFragment extends Fragment implements OnQueryTextListener {

	public SearchFragment(){}

	private SearchView mSearchView;
	private ListView mListView;
	private ArrayList<Food> foodArrayList;
	private FoodAdapterWithNumber foodAdapter;
	private ProgressDialog progress;
	private Spinner spinner1;
	private Button btnSubmit;

	private List<String> fridgeInside;
	private List<String> fridgeList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


		View rootView = inflater.inflate(R.layout.fragment_search, container, false);
		/*web service den dolabýn içindekileri getir göster */

		/*Notification listesi de geliyor :)*/
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		String username = prefs.getString("username", "Default NickName");
		String passw = prefs.getString("password", "Default Password");
		boolean checkBox[]=new boolean [8];
		ArrayList<String> FoodList2;

		checkBox[0] = prefs.getBoolean("checkBoxMilk", false);
		checkBox[1] = prefs.getBoolean("checkBoxMeat", false);
		checkBox[2] = prefs.getBoolean("checkBoxChicken", false);
		checkBox[3] = prefs.getBoolean("checkBoxButter", false);
		checkBox[4] = prefs.getBoolean("checkBoxJam", false);
		checkBox[5] = prefs.getBoolean("checkBoxOrangeJuice", false);
		checkBox[6] = prefs.getBoolean("checkBoxTomato", false);
		checkBox[7] = prefs.getBoolean("checkBoxCarrot", false);
		String listPrefs = prefs.getString("listpref", "Default list prefs");
		FoodList2= new ArrayList<String>();
		if(checkBox[0])
			FoodList2.add("Milk");
		if(checkBox[1])
			FoodList2.add("Meat");

		if(checkBox[2])
			FoodList2.add("Chicken");

		if(checkBox[3])
			FoodList2.add("Butter");
		if(checkBox[4])
			FoodList2.add("Jam");
		if(checkBox[5])
			FoodList2.add("Orange Juice");

		if(checkBox[6])
			FoodList2.add("Tomato");

		if(checkBox[7])
			FoodList2.add("Carrot");


		for(int i=0;i<FoodList2.size();i++)
		{
			System.out.println("-->"+FoodList2.get(i));

		}



		////////////////////////////

		spinner1 = (Spinner) rootView.findViewById(R.id.spinner1);
		btnSubmit = (Button) rootView.findViewById(R.id.btnSubmit);
		mSearchView=(SearchView)rootView.findViewById(R.id.searchView1);
		mListView=(ListView) rootView.findViewById(R.id.listView1);

		WebServiceCall ws=null;
		List<WebServiceParameterItem> properties = null;
		ws = new WebServiceCall();
		properties = new ArrayList<WebServiceParameterItem>();
		
		UserSessionManager session;
		session = new UserSessionManager(getActivity());
		HashMap<String, String> user = session.getUserDetails();
		// get email
		String email = user.get(UserSessionManager.KEY_EMAIL);
		String pass = user.get(UserSessionManager.KEY_PASS);
		
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


		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Toast.makeText(getActivity(),
						"OnClickListener : " + 
								"\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()),Toast.LENGTH_SHORT).show();

				final ProgressDialog myPd_ring=ProgressDialog.show(getActivity(), "Please wait", "Loading please wait..", true);
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
				String tempSplittedArray[]=new String[2];
				tempSplittedArray=spinner1.getSelectedItem().toString().split("-");
				properties = new ArrayList<WebServiceParameterItem>();
				WebServiceParameterItem property1 = new WebServiceParameterItem("fridgeID",Integer.parseInt(tempSplittedArray[0].toString()));
				properties.add(property1);
				fridgeInside = new ArrayList<String>();  
				fridgeInside= ws.CallGetWebservice("getInsideOfFridgeWithExpirationDate",properties);
				foodArrayList=new ArrayList<Food>();
				foodAdapter=new FoodAdapterWithNumber(SearchFragment.this, null);
				for(int i=1;i<fridgeInside.size()+2;i++)
				{
					if(fridgeInside.size()!=0 && i % 2 ==0)				
					{
						String imageName =fridgeInside.get(i-2).toString().replace(" ", "");
						foodArrayList.add(new Food(fridgeInside.get(i-2).toString(),"http://smartfridgeapp.com/images/"+imageName+".jpg",fridgeInside.get(i-1).toString()));
					}
				}

				foodAdapter=new FoodAdapterWithNumber(SearchFragment.this, foodArrayList);
				mListView.setAdapter(foodAdapter);
				mListView.setTextFilterEnabled(true);

			}

		});


		setupSearchView();


		/*foodArrayList.add(new Food("ARMUT", 24));
		foodArrayList.add(new Food("AYVA", 24));
		foodArrayList.add(new Food("ARI", 28));
		foodArrayList.add(new Food("SÜT", 28));
		foodArrayList.add(new Food("YUMURTA", 23));
		foodArrayList.add(new Food("DOMATES", 24));
		foodArrayList.add(new Food("PEYNIR", 24));
		foodArrayList.add(new Food("SALATALIK", 28));
		foodArrayList.add(new Food("PORTAKAL", 28));
		foodArrayList.add(new Food("LIMON", 23));*/



		return rootView;
	}


	private void setupSearchView()
	{
		mSearchView.setIconifiedByDefault(false);
		mSearchView.setOnQueryTextListener(this);
		mSearchView.setSubmitButtonEnabled(true);
		mSearchView.setQueryHint("Search Here");
	}

	@Override
	public boolean onQueryTextChange(String newText)
	{

		if (TextUtils.isEmpty(newText)) {
			mListView.clearTextFilter();
		} else {
			mListView.setFilterText(newText);
		}
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query)
	{
		return false;
	}
	/////////////////////////////////
	SearchView.OnQueryTextListener listener;

	// This event fires 1st, before creation of fragment or any views
	// The onAttach method is called when the Fragment instance is associated with an Activity. 
	// This does not mean the Activity is fully initialized.


	/*@Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    this.listener = (SearchView.OnQueryTextListener) activity;
}*/
	OnQueryTextListener activityCallback;

	public interface OnQueryTextListener {
		//public void onButtonClick(int position, String text);
		public boolean onQueryTextChange(String newText);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			activityCallback = (OnQueryTextListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " OnQueryTextListener a implement edilmeli!");
		}
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
}
