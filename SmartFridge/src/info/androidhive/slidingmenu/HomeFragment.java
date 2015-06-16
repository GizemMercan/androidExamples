package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import info.androidhive.slidingmenu.FoodAdapter.FoodHolder;
import info.androidhive.slidingmenu.model.WebServiceParameterItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;


public class HomeFragment extends Fragment {

	/*private ImageView image;
	private ExpandableListView elv;
	private TextView myHomeTxt;
	private ArrayList<Food> foodArrayList;
	private ArrayList<ArrayList<Food>> foodArrayListChild;
	private FoodExpandListAdapter foodExpandListAdapter;
	public static String[] groups=null;
	public static String[][] children=null;*/

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	List<String> listDataHeaderID;
	HashMap<String, List<String>> listDataChild;
	private TextView myHomeTxt;
	WebServiceCall ws;
	List<WebServiceParameterItem> properties;
	private List<String> fridgeList;
	private List<String> fridgeInside ;

	public HomeFragment(){}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		

		ws = new WebServiceCall();
		properties = new ArrayList<WebServiceParameterItem>();

		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);
		myHomeTxt=(TextView)rootView.findViewById(R.id.display);
		prepareListData();

		listAdapter = new ExpandableListAdapter(this.getActivity(), listDataHeader, listDataChild);

		expListView.setAdapter(listAdapter);
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				Toast.makeText(getActivity(),
						listDataHeader.get(groupPosition) + " Expanded",
						Toast.LENGTH_SHORT).show();
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				Toast.makeText(getActivity(),
						listDataHeader.get(groupPosition) + " Collapsed",
						Toast.LENGTH_SHORT).show();

			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(
						getActivity(),
						listDataHeader.get(groupPosition)
						+ " : "
						+ listDataChild.get(
								listDataHeader.get(groupPosition)).get(
										childPosition), Toast.LENGTH_SHORT)
										.show();
				return false;
			}
		});

		//foodArrayList=new ArrayList<Food>();


		setHasOptionsMenu(true);

		return rootView;
	}


	@Override
	public void onCreateOptionsMenu(
	      Menu menu, MenuInflater inflater) {
		menu.clear();

	   inflater.inflate(R.menu.main, menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	   // handle item selection
	   switch (item.getItemId()) {
	      case R.id.add_item:
	    	  Toast.makeText(getActivity(), "add item",
	    	            Toast.LENGTH_LONG).show();
	    	  Bundle args = new Bundle();
              args.putString("Menu", "You pressed add fridge button.");
              Fragment detail = new AddFridgeFragment();
              detail.setArguments(args);
              FragmentManager fragmentManager = getFragmentManager();
              fragmentManager.beginTransaction().replace(R.id.frame_container, detail).commit();
	      
				
	         return true;
	      case R.id.choose_item:
		      
	    	  Toast.makeText(getActivity(), "chose item",
	    	            Toast.LENGTH_LONG).show();
	    	  Bundle argsChoose = new Bundle();
	    	  argsChoose.putString("Menu", "You pressed choose fridge button.");
            Fragment detailChoose = new ChooseFridgeFragment();
            detailChoose.setArguments(argsChoose);
            FragmentManager fragmentManagerChoose = getFragmentManager();
            fragmentManagerChoose.beginTransaction().replace(R.id.frame_container, detailChoose).commit();
	    	       
	     
	         return true;
	      case R.id.action_settings:
	      
	    	  Toast.makeText(getActivity(), "Selected menu",
	    	            Toast.LENGTH_LONG).show();
	    	       
	     
	         return true;
	      default:
	         return super.onOptionsItemSelected(item);
	   }
	}
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataHeaderID = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

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
		for(int i=1;i<fridgeList.size()+2;i++)
		{
			if(i % 2 ==0 && i!=0 )
			{
				//list.add(fridgeList.get(i-2).toString()+"-"+fridgeList.get(i-1).toString());
				listDataHeader.add(fridgeList.get(i-1).toString());
				listDataHeaderID.add(fridgeList.get(i-2).toString());
			}
		}

		for(int k=0;k<listDataHeaderID.size();k++)
		{
			System.out.println("idler"+listDataHeaderID.get(k).toString());
		}





		for(int k=0;k<listDataHeaderID.size();k++)
		{
			ws=null;
			List<WebServiceParameterItem> propertiesforProduct=null;
			ws = new WebServiceCall();
			propertiesforProduct= new ArrayList<WebServiceParameterItem>();

			WebServiceParameterItem property3 = new WebServiceParameterItem("fridgeID",Integer.parseInt(listDataHeaderID.get(k).toString()));
			propertiesforProduct.add(property3);
			fridgeInside = new ArrayList<String>();
			fridgeInside= ws.CallGetWebservice("getInsideOfFridge",propertiesforProduct);
			List<String> top250 = new ArrayList<String>();

			for(int j=0;j<fridgeInside.size();j++)
			{
				top250.add(fridgeInside.get(j).toString());
				//foodArrayListChild.get(1).add(new Food(fridgeInside.get(i).toString(), 1));

			}
			listDataChild.put(listDataHeader.get(k), top250);
			Log.d("counter ve top250",k+" "+top250);

		}


	}



}
/*List<String> top250 = new ArrayList<String>();
		top250.add("elma");
		top250.add("elma2");
		top250.add("elma3");
		top250.add("elma4");
		listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
		listDataChild.put(listDataHeader.get(1), top250);
		listDataChild.put(listDataHeader.get(2), top250);*/
