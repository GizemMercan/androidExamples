package info.androidhive.slidingmenu;



import info.androidhive.slidingmenu.model.WebServiceParameterItem;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

public class DetailOfRecipeActivity extends Activity 
{
	private ListView listViewCookingTip;
	private ArrayList<Food> foodArrayList;
	private FoodAdapter foodAdapter;
	private TextView recipeText;
	private TextView descText;
	private TextView ingrText;
	private TextView prepTimeText;
	private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_of_recipe);
        if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			}
        options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.ic_home)
	    .cacheOnDisc()
	    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
	    .build();
	    imageLoader= ImageLoader.getInstance();
imageLoader.init(ImageLoaderConfiguration.createDefault(getBaseContext()));

		img=(ImageView)findViewById(R.id.imageView1);
        recipeText =(TextView)findViewById(R.id.RecipeNameTextView);
        descText=(TextView)findViewById(R.id.descTextView);
        ingrText =(TextView)findViewById(R.id.ingredientsTextView);
        prepTimeText=(TextView)findViewById(R.id.prepTimeTextView);
        
        WebServiceCall ws = new WebServiceCall();
		List<WebServiceParameterItem> properties = new ArrayList<WebServiceParameterItem>();
		foodArrayList=new ArrayList<Food>();
		Intent myIntent = getIntent(); // gets the previously created intent
		String recipeName = myIntent.getStringExtra("recipe"); // will return "FirstKeyValue"
		WebServiceParameterItem property1 = new WebServiceParameterItem("recipeName",recipeName);
		properties.add(property1);
		List<String> recipes = ws.CallGetWebservice("getRecipes",properties);
		recipeText.setText(recipeName);
		
		imageLoader.displayImage("http://smartfridgeapp.com/images/"+recipeName.replace(" ", "")+"Big.jpg", img, options);

		descText.setText(Html.fromHtml("Description: <b>"+recipes.get(0).toString()+"</b>")); //DESCRIPTION
		ingrText.setText(Html.fromHtml("Ingredients: <b>"+recipes.get(1).toString()+"</b>")); //INGREDIENTS
		prepTimeText.setText(Html.fromHtml("Preparation Time: <b>"+recipes.get(2).toString()+"</b>")); //PREP_TIME


    }
    

}