package info.androidhive.slidingmenu;

import info.androidhive.slidingmenu.model.WebServiceParameterItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class CookingTipsFragment extends Fragment {
	
	public CookingTipsFragment(){}
	
	private TextView txtCooking;

	private ListView listViewCookingTip;
	private ArrayList<Food> foodArrayList;
	private FoodAdapter foodAdapter;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_cooking_tips, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			}
		WebServiceCall ws = new WebServiceCall();
		List<WebServiceParameterItem> properties = new ArrayList<WebServiceParameterItem>();
		foodArrayList=new ArrayList<Food>();
		UserSessionManager session;
		session = new UserSessionManager(getActivity());
		HashMap<String, String> user = session.getUserDetails();
		HashMap<String, Integer> userFridge = session.getUserFridge();

		// get email
		String email = user.get(UserSessionManager.KEY_EMAIL);
		String pass = user.get(UserSessionManager.KEY_PASS);
		int fridgeNumber=userFridge.get(UserSessionManager.KEY_FRIDGE);
		
		WebServiceParameterItem property1 = new WebServiceParameterItem("fridgeID",fridgeNumber);
		properties.add(property1);
		List<String> recipes = ws.CallGetWebservice("getCookingTips",properties);

		for(int i=0;i<recipes.size();i++)
		{
			if(i%5==1){
			String imageName =recipes.get(i).toString().replace(" ", "");
			foodArrayList.add(new Food(recipes.get(i).toString(),"http://smartfridgeapp.com/images/"+imageName+".jpg"));
			}

		}
		listViewCookingTip=(ListView)rootView.findViewById(R.id.listViewCookingTip);
		

		foodAdapter=new FoodAdapter(CookingTipsFragment.this, foodArrayList);
		listViewCookingTip.setAdapter(foodAdapter);
		listViewCookingTip.setOnItemClickListener(new OnItemClickListener() {
			@Override
		      public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
		        Food selectedFromList =(Food)  myAdapter.getItemAtPosition(myItemInt);
		        System.out.println("item-->"+selectedFromList.getName().toString());
		        
		        Intent myIntent = new Intent(getActivity(), DetailOfRecipeActivity.class);
		        myIntent.putExtra("recipe",selectedFromList.getName().toString());
		       
		        startActivity(myIntent);

		      }                 
		});
		
         
        return rootView;
    }
	
}
