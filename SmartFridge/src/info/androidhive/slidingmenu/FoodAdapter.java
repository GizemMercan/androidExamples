package info.androidhive.slidingmenu;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Application;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;


public class FoodAdapter extends BaseAdapter {

    public Context context;
    public ArrayList<Food> foodArrayList;
    public ArrayList<Food> orig;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public FoodAdapter(Fragment fragment, ArrayList<Food> foodArrayList) {
        super();
        this.context = fragment.getActivity();
        this.foodArrayList = foodArrayList;
        options = new DisplayImageOptions.Builder()
        		.showImageForEmptyUri(R.drawable.ic_home)
        	    .cacheOnDisc()
        	    .imageScaleType(ImageScaleType.EXACTLY)
        	    .build();
        	    imageLoader= ImageLoader.getInstance();
       imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }


    public class FoodHolder
    {
        TextView name;
        TextView quantity;
        ImageView picture;
        LinearLayout linear;
        ImageView pictureIcon;

    }
    public class FoodHolderWithNumber
    {
        TextView name;
        TextView quantity;
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
            //holder.quantity=(TextView) convertView.findViewById(R.id.txtAge);
            holder.picture = (ImageView) convertView.findViewById(R.id.Listimage);
            holder.pictureIcon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
            holder.linear=(LinearLayout) convertView.findViewById(R.id.linearLayoutOfRow);

           
            convertView.setTag(holder);
        }
        else
        {
            holder=(FoodHolder) convertView.getTag();
        }
        if(position%2 ==0)
        {
			holder.linear.setBackgroundColor(Color.parseColor("#eeeeee"));

        }
        else
        {
			holder.linear.setBackgroundColor(Color.parseColor("#bed2c6"));

        }
        
        if(foodArrayList.get(position).getName().contains("added"))
        {
        	holder.pictureIcon.setImageResource(R.drawable.check);
        }
        else if(foodArrayList.get(position).getName().contains("removed"))
        {
        	holder.pictureIcon.setImageResource(R.drawable.delete);

        }
        holder.name.setText(foodArrayList.get(position).getName());
        imageLoader.displayImage(foodArrayList.get(position).getpictureUrl(), holder.picture, options);

       // holder.quantity.setText(String.valueOf(foodArrayList.get(position).getQuantity()));

        return convertView;

    }
}