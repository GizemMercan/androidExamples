package info.androidhive.slidingmenu;

import static info.androidhive.slidingmenu.CommonUtilities.SERVER_URL;
import static info.androidhive.slidingmenu.CommonUtilities.TAG;
import static info.androidhive.slidingmenu.CommonUtilities.displayMessage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import android.content.Context;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

import info.androidhive.slidingmenu.CommonUtilities;
import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.model.WebServiceParameterItem;

public class ServerUtilities {
	private static final int MAX_ATTEMPTS = 2;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();

    /**
     * Register this account/device pair within the server.
     *
     * @return whether the registration succeeded or not.
     */
    static boolean register(final Context context, final String regId) throws IOException {
        Log.i(TAG, "registering device (regId = " + regId + ")");
        
        List<WebServiceParameterItem> params = new ArrayList<WebServiceParameterItem>();
        params.add(new WebServiceParameterItem("regid",regId));
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("regid", regId);
        
        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        // Once GCM returns a registration id, we need to register it in the
        // demo server. As the server might be down, we will retry it a couple
        // times.
        for (int i = 1; i < MAX_ATTEMPTS; i++) {
            Log.d(TAG, "Attempt #" + i + " to register");
           
                //displayMessage(context, context.getString(R.string.server_registering, i, MAX_ATTEMPTS));
                //post(serverUrl, params);
                WebServiceCall call = new WebServiceCall();
                call.CallInsertUpdateWebservice("Post_AndroidRegId", params);
                GCMRegistrar.setRegisteredOnServer(context, true);
                //String message = context.getString(R.string.server_registered);
                //CommonUtilities.displayMessage(context, message);
                return true;
        }
        //String message = context.getString(R.string.server_register_error,MAX_ATTEMPTS);
        //CommonUtilities.displayMessage(context, message);
        return false;
    }

    /**
     * Unregister this account/device pair within the server.
     */
    static void unregister(final Context context, final String regId) {
        Log.i(TAG, "unregistering device (regId = " + regId + ")");
        String serverUrl = SERVER_URL + "/unregister";
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
        try {
            post(serverUrl, params);
            GCMRegistrar.setRegisteredOnServer(context, false);
            String message = context.getString(R.string.server_unregistered);
            CommonUtilities.displayMessage(context, message);
        } catch (IOException e) {
            // At this point the device is unregistered from GCM, but still
            // registered in the server.
            // We could try to unregister again, but it is not necessary:
            // if the server tries to send a message to the device, it will get
            // a "NotRegistered" error message and should unregister the device.
            String message = context.getString(R.string.server_unregister_error,
                    e.getMessage());
            CommonUtilities.displayMessage(context, message);
        }
    }

    /**
     * Issue a POST request to the server.
     *
     * @param endpoint POST address.
     * @param params request parameters.
     *
     * @throws IOException propagated from POST.
     */
    private static void post(String endpoint, Map<String, String> params)
            throws IOException {
        URL url;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
        // constructs the POST body using the parameters
        while (iterator.hasNext()) {
            Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
        String body = bodyBuilder.toString();
        Log.v(TAG, "Posting '" + body + "' to " + url);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int status = conn.getResponseCode();
            if (status != 200) {
              throw new IOException("Post failed with error code " + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
      }
}
