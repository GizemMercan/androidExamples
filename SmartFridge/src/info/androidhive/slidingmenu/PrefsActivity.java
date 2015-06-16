package info.androidhive.slidingmenu;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class PrefsActivity extends PreferenceFragment{
	 
@SuppressWarnings("deprecation")
@Override
public void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   addPreferencesFromResource(R.xml.prefs);
}
}