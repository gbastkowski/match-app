package de.digitalstep.matchapp.android;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Creates an activity to edit preferences. The UI is defined in
 * {@code xml/preferences.xml}
 * 
 * @author Gunnar Bastkowski (gunnar@digitalstep.de)
 */
public class EditPreferencesActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

}
