package ntnu.snorre.hangman2;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefFragment()).commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PrefFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefrences);
            final SwitchPreference musicPrefrence = (SwitchPreference) findPreference("music");
            musicPrefrence.setOnPreferenceChangeListener((preference, newValue) -> {
                if (musicPrefrence.isChecked()) {
                    MainMenuActivity.backgroundMusic.pause();
                    musicPrefrence.setChecked(false);
                } else {
                    MainMenuActivity.backgroundMusic.start();
                    musicPrefrence.setChecked(true);
                }
                return false;
            });
            final ListPreference languagePrefrence = (ListPreference) findPreference("language");
            languagePrefrence.setOnPreferenceChangeListener((preference, newValue) -> {
                languagePrefrence.setDefaultValue(newValue);
                MainMenuActivity.languageChanged = true;
                return true;
            });
        }
    }
}