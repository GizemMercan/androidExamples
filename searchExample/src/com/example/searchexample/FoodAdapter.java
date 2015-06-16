package com.example.searchexample;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


public class FoodAdapter extends BaseAdapter implements Filterable {

    public Context context;
    public ArrayList<Food> foodArrayList;
    public ArrayList<Food> orig;

    public FoodAdapter(Context context, ArrayList<Food> foodArrayList) {
        super();
        this.context = context;
        this.foodArrayList = foodArrayList;
    }


    public class FoodHolder
    {
        TextView name;
        TextView quantity;
    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Food> results = new ArrayList<Food>();
                if (orig == null)
                    orig = foodArrayList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final Food g : orig) {
                            if (g.getName().toLowerCase()
                                    .startsWith(((String) constraint).toLowerCase().toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                foodArrayList = (ArrayList<Food>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return foodArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FoodHolder holder;
        if(convertView==null)
        {
            convertView=LayoutInflater.from(context).inflate(R.layout.row, parent, false);
            holder=new FoodHolder();
            holder.name=(TextView) convertView.findViewById(R.id.txtName);
            holder.quantity=(TextView) convertView.findViewById(R.id.txtAge);
            convertView.setTag(holder);
        }
        else
        {
            holder=(FoodHolder) convertView.getTag();
        }

        holder.name.setText(foodArrayList.get(position).getName());
        holder.quantity.setText(String.valueOf(foodArrayList.get(position).getQuantity()));

        return convertView;

    }

}