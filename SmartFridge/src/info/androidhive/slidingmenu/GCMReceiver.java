package info.androidhive.slidingmenu;

import android.content.Context;

import com.google.android.gcm.GCMBroadcastReceiver;

public class GCMReceiver  extends GCMBroadcastReceiver { 
	@Override
	protected String getGCMIntentServiceClassName(Context context) { 
		return "info.androidhive.slidingmenu.GCMIntentService"; 
	} 
}
