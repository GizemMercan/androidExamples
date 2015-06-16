package info.androidhive.slidingmenu;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import info.androidhive.slidingmenu.model.WebServiceParameterItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class ShoppingListFragment extends Fragment {

	public ShoppingListFragment(){}
	private TextView txtShopping;

	private ListView listViewShoppingList;
	private ArrayList<Food> foodArrayList;
	private FoodAdapter foodAdapter;
	//FTPClient ftp = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		WebServiceCall ws = new WebServiceCall();
		List<WebServiceParameterItem> properties = new ArrayList<WebServiceParameterItem>();
		View rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);
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
		List<String> shoppingList = ws.CallGetWebservice("getShoppingList",properties);

		if(shoppingList.size()>0)
		{
			for(int i=0;i<shoppingList.size();i++)
			{
				String imageName =shoppingList.get(i).toString().replace(" ", "");
				foodArrayList.add(new Food(shoppingList.get(i).toString(),"http://smartfridgeapp.com/images/"+imageName+".jpg"));
			}
			listViewShoppingList=(ListView)rootView.findViewById(R.id.listViewShoppingList);

			foodAdapter=new FoodAdapter(ShoppingListFragment.this, foodArrayList);
			listViewShoppingList.setAdapter(foodAdapter);
		}
		else
		{
			foodArrayList.add(new Food("Dolaba Göre Alabileceðiniz Ürün Bulunamadý","https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRIk9PAfbZnHXAnPQRMf0t15UJk7iaJ_hxzswb5itrHmWVmWnrU"));
			listViewShoppingList=(ListView)rootView.findViewById(R.id.listViewShoppingList);

			foodAdapter=new FoodAdapter(ShoppingListFragment.this, foodArrayList);
			listViewShoppingList.setAdapter(foodAdapter);
		}


		

		return rootView;
	}
}
