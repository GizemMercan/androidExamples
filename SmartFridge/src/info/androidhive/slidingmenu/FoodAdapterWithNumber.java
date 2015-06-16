package info.androidhive.slidingmenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

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


public class FoodAdapterWithNumber extends BaseAdapter implements Filterable {

	public Context context;
	public ArrayList<Food> foodArrayList;
	public ArrayList<Food> orig;
	private DisplayImageOptions options;
	private ImageLoader imageLoader;

	public FoodAdapterWithNumber(Fragment searchFragment, ArrayList<Food> foodArrayList) {
		super();
		this.context = searchFragment.getActivity();
		this.foodArrayList = foodArrayList;
		options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.ic_home)
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
		TextView expDate;
		LinearLayout linear;
	}
	public class FoodHolderWithNumber
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
			holder.expDate=(TextView) convertView.findViewById(R.id.txtExpDate);
			holder.linear=(LinearLayout)convertView.findViewById(R.id.linearLayoutOfRow);
			// holder.quantity=(TextView) convertView.findViewById(R.id.txtAge);
			holder.picture = (ImageView) convertView.findViewById(R.id.Listimage);
			convertView.setTag(holder);
		}
		else
		{
			holder=(FoodHolder) convertView.getTag();
		}

		holder.name.setText(foodArrayList.get(position).getName());
		int result=0;
		result=findDiffExpirationDay(foodArrayList.get(position).getLeftDay());

		if(result<=2 && result>0 )
		{
			//System.out.println(Math.abs(result)+" günden az kalmýþ :(");
			holder.expDate.setText("It should be consumed within "+Math.abs(result)+" days :(");
			holder.linear.setBackgroundColor(Color.parseColor("#FEFFA3"));

		}
		else if(result<=0)
		{
			//System.out.println(result+" bozulmuþ çoktan :'(");
			holder.expDate.setText(result+" It is already off, don't consume :'(");
			holder.linear.setBackgroundColor(Color.parseColor("#FFBDBD"));
			
			
			/*USE OF COLORS*/
			// holder.expDate.setBackgroundColor(Color.RED);
			//holder.expDate.setBackgroundColor(0xFFFFFF00);
			//holder.linear.setBackgroundColor(Color.parseColor("#AEF26D"));

		}
		else 
		{
			//System.out.println(Math.abs(result)+" iki günden fazla kalmýþ :)");
			holder.expDate.setText(Math.abs(result)+" days left :)");
			holder.linear.setBackgroundColor(Color.parseColor("#E1F7D5"));
			

		}
		// holder.quantity.setText(String.valueOf(foodArrayList.get(position).getQuantity()));
		imageLoader.displayImage(foodArrayList.get(position).getpictureUrl(), holder.picture, options);

		return convertView;

	}
	public static int findDiffExpirationDay(String expireDate)
	{
		SimpleDateFormat formatuzun = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		//get current date time with Date()
		Date currDate = new Date();

		Date d1 = null;
		long diff;
		long diffDays = 0 ;

		try {
			d1 = formatuzun.parse(expireDate);


			diff = d1.getTime() - currDate.getTime();


			diffDays = diff / (24 * 60 * 60 * 1000);

			//System.out.print(diffDays + " days ");
			/*int result=0;
				result=findDiffExpirationDay(dateStart);

				if(result<=2 && result>0 )
				{
					System.out.println(Math.abs(result)+" günden az kalmýþ :(");
				}
				else if(result<=0)
				{
					System.out.println(result+" bozulmuþ çoktan :'(");

				}
				else 
				{
					System.out.println(Math.abs(result)+" iki günden fazla kalmýþ :)");

				}*/

		} catch (Exception e) {
			e.printStackTrace();
		}

		return (int)diffDays;

	}


}