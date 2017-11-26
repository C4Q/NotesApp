package c4q.nyc.notesapp;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * SettingsActivity automatically generates settings UI from preference resource.
 * There is a newer and better way of managing preferences using fragments.
 * I'm using this for simplicity sake.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
