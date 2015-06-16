package info.androidhive.slidingmenu;

import java.util.ArrayList;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;

import android.view.View;

import android.widget.Button;

import android.widget.TextView;


public class SettingsFragment extends Fragment {

	TextView textView;
	ArrayList<String> FoodList;
	public SettingsFragment(){}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_settings, container, false);


		textView = (TextView) rootView.findViewById(R.id.txtPrefs);

		

		Bundle args = new Bundle();
		args.putString("Menu", "You pressed add fridge button.");
		Fragment detail = new PrefsActivity();
		detail.setArguments(args);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.frame_container, detail).commit();



		/*Log.d("bundale", "önce");
		Bundle args2 = new Bundle();
		args2.putStringArrayList("notification_food", getNotificationFoods());    
		SearchFragment newFragment = new SearchFragment ();
		newFragment.setArguments(args2);
		Log.d("bundale", "sonra");*/
		





		return rootView;
	}

	private void displaySharedPreferences() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());

		String username = prefs.getString("username", "Default NickName");
		String passw = prefs.getString("password", "Default Password");
		boolean checkBox[]=new boolean [8];
		checkBox[0] = prefs.getBoolean("checkBoxMilk", false);
		checkBox[1] = prefs.getBoolean("checkBoxMeat", false);
		checkBox[2] = prefs.getBoolean("checkBoxChicken", false);
		checkBox[3] = prefs.getBoolean("checkBoxButter", false);
		checkBox[4] = prefs.getBoolean("checkBoxJam", false);
		checkBox[5] = prefs.getBoolean("checkBoxOrangeJuice", false);
		checkBox[6] = prefs.getBoolean("checkBoxTomato", false);
		checkBox[7] = prefs.getBoolean("checkBoxCarrot", false);


		String listPrefs = prefs.getString("listpref", "Default list prefs");


		StringBuilder builder = new StringBuilder();
		builder.append("Username: " + username + "\n");
		builder.append("Password: " + passw + "\n");
		/*builder.append("Süt in: " + String.valueOf(checkBox[0]) + "\n");
		builder.append("Elma in: "+String.valueOf(checkBox[1]) + "\n");
		builder.append("Cilek in: "+String.valueOf(checkBox[2])+ "\n" );
		builder.append("Armut in: "+String.valueOf(checkBox[3]) + "\n");*/
		FoodList= new ArrayList<String>();
		if(checkBox[0])
			FoodList.add("Milk");
		if(checkBox[1])
			FoodList.add("Meat");

		if(checkBox[2])
			FoodList.add("Chicken");

		if(checkBox[3])
			FoodList.add("Butter");
		if(checkBox[4])
			FoodList.add("Jam");
		if(checkBox[5])
			FoodList.add("Orange Juice");

		if(checkBox[6])
			FoodList.add("Tomato");

		if(checkBox[7])
			FoodList.add("Carrot");



		for(int i=0;i<FoodList.size();i++)
		{
			System.out.println("-->"+FoodList.get(i));
			builder.append(" " + FoodList.get(i));

		}
		builder.append("\nList preference: " + listPrefs);

		textView.setText(builder.toString());
	}
	public ArrayList<String> getNotificationFoods()
	{
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


		return FoodList2;
		
	}
}
