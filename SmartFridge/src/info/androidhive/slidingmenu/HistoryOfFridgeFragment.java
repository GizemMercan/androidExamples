package info.androidhive.slidingmenu;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import info.androidhive.slidingmenu.model.WebServiceParameterItem;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.ListView;

public class HistoryOfFridgeFragment extends Fragment {

	public HistoryOfFridgeFragment(){}
	private TextView myHistoryTxt;

	private ListView listViewHistory;
	private ArrayList<Food> foodArrayList;
	private FoodAdapter foodAdapter;
	private ImageView image;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		WebServiceCall ws = new WebServiceCall();
		List<WebServiceParameterItem> properties = new ArrayList<WebServiceParameterItem>();

		View rootView = inflater.inflate(R.layout.fragment_history_of_fridge, container, false);
		image = (ImageView) rootView.findViewById(R.id.Listimage);

		foodArrayList=new ArrayList<Food>();

		UserSessionManager session;
		session = new UserSessionManager(getActivity());
		HashMap<String, String> user = session.getUserDetails();
		HashMap<String, Integer> userFridge = session.getUserFridge();

		String email = user.get(UserSessionManager.KEY_EMAIL);
		String pass = user.get(UserSessionManager.KEY_PASS);
		int fridgeNumber=userFridge.get(UserSessionManager.KEY_FRIDGE);

		WebServiceParameterItem property1 = new WebServiceParameterItem("fridgeID",fridgeNumber);
		properties.add(property1);
		List<String> history = ws.CallGetWebservice("getFridgeHistory",properties);
		String TempString="";
		String imageName="";
		if(history.size()>0)
		{
			for(int i=0;i<history.size()/5;i++)
			{

				for(int j=0;j<5;j++)
				{
					if(j==1)
					{
						TempString+= history.get(i*5+j).toString()+" ";
						imageName =history.get(i*5+j).toString().replace(" ", "");
					}
					if(j==3)
					{
						if(history.get(i*5+j).toString().equals("1"))
						{
							TempString +="is added into "+ history.get(i*5+j-1)+" - ";

						}else if(history.get(i*5+j).equals("0"))
						{
							TempString +="is removed from "+ history.get(i*5+j-1)+" - ";
						}
					}
					if(j==4)
					{
						String datetime =history.get(i*5+j).toString();
						String []datetimeSplitted = datetime.split("T");
						TempString+=datetimeSplitted[0]+" ";
						String temptime = datetimeSplitted[1];
						String [] time =temptime.split(":");
						TempString+=time[0]+":"+time[1];

					}

				}

				/*if(i % 5 == 0 && i!=0)
			{
				foodArrayList.add(new Food(TempString));
				TempString="";
			}
			if(i % 2 == 0 && i!=0)
			{
				foodArrayList.add(new Food(TempString));
				TempString="";
			}
			if(i % 4 == 0 && i!=0)
			{	
				DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
				Date date=null;
				try {
					 date = format.parse(history.get(i).toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}*/




				//TempString+=(history.get(i).toString()+" ");

				foodArrayList.add(new Food(TempString,"http://smartfridgeapp.com/images/"+imageName+".jpg"));
				TempString="";

			}

			listViewHistory=(ListView)rootView.findViewById(R.id.listViewShoppingList);

			foodAdapter=new FoodAdapter(HistoryOfFridgeFragment.this, foodArrayList);
			listViewHistory.setAdapter(foodAdapter);

		}
		else
		{
			foodArrayList.add(new Food("Dolaba Göre Geçmiþ Kayýt Bulunamadý","https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRIk9PAfbZnHXAnPQRMf0t15UJk7iaJ_hxzswb5itrHmWVmWnrU"));

			listViewHistory=(ListView)rootView.findViewById(R.id.listViewShoppingList);

			foodAdapter=new FoodAdapter(HistoryOfFridgeFragment.this, foodArrayList);
			listViewHistory.setAdapter(foodAdapter);

		}


		return rootView;
	}
}