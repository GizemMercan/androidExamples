package info.androidhive.slidingmenu;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {
	/**
	 * Base URL of the Demo Server (such as http://my_host:8080/gcm-demo)
	 */
	
	/*static final String SERVER_URL = "http://10.0.2.2:10739/WebService1.asmx/PostRegId";*/
	static final String SERVER_URL ="http://192.168.43.147:3333/Service1.asmx/Post_AndroidRegId";
	//AIzaSyBIMq9LMVIteSOlIEgQ4wcjRin1Qz1KXbk api key
	
	/**
	 * Google API project id registered to use GCM.
	 */
	static final String SENDER_ID = "217886568461";

	/**
	 * Tag used on log messages.
	 */
	static final String TAG = "Sample";

	/**
	 * Intent used to display a message in the screen.
	 */
	static final String DISPLAY_MESSAGE_ACTION = "info.androidhive.slidingmenu.DISPLAY_MESSAGE";

	/**
	 * Intent's extra that contains the message to be displayed.
	 */
	static final String EXTRA_MESSAGE = "message";

	/**
	 * Notifies UI to display a message.
	 * <p>
	 * This method is defined in the common helper because it's used both by the
	 * UI and the background service.
	 * 
	 * @param context
	 *            application's context.
	 * @param message
	 *            message to be displayed.
	 */
	static void displayMessage(Context context, String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}
}
