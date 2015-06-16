package info.androidhive.slidingmenu;



import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.SearchView;

public class SearchActivity extends Activity implements SearchView.OnQueryTextListener
{
    private SearchView mSearchView;
    private ListView mListView;
    private ArrayList<Food> foodArrayList;
    private FoodAdapter foodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        mSearchView=(SearchView) findViewById(R.id.searchView1);
        mListView=(ListView) findViewById(R.id.listView1);

        foodArrayList=new ArrayList<Food>();
//        foodArrayList.add(new Food("ARMUT", 24));
//        foodArrayList.add(new Food("AYVA", 24));
//        foodArrayList.add(new Food("ARI", 28));
//        foodArrayList.add(new Food("SÜT", 28));
//        foodArrayList.add(new Food("YUMURTA", 23));
//        foodArrayList.add(new Food("DOMATES", 24));
//        foodArrayList.add(new Food("PEYNIR", 24));
//        foodArrayList.add(new Food("SALATALIK", 28));
//        foodArrayList.add(new Food("PORTAKAL", 28));
//        foodArrayList.add(new Food("LIMON", 23));

        //foodAdapter=new FoodAdapter(SearchActivity.this, foodArrayList);
        mListView.setAdapter(foodAdapter);

        mListView.setTextFilterEnabled(true);
        setupSearchView();


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


}