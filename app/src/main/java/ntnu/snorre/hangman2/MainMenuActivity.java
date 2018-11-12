package ntnu.snorre.hangman2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

public class MainMenuActivity extends AppCompatActivity {

    static MediaPlayer backgroundMusic;
    static boolean languageChanged;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        initLanguage();
        setContentView(R.layout.activity_main_menu);
        if (backgroundMusic == null) initBackgroundMusic();
        initButtons();
    }

    private void initLanguage() {
        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(preferences.getString("language", "en")));
        res.updateConfiguration(conf, res.getDisplayMetrics());
    }

    private void initBackgroundMusic() {
        backgroundMusic = MediaPlayer.create(this, R.raw.background_music);
        backgroundMusic.setLooping(true);
        if (preferences.getBoolean("music", false))
            backgroundMusic.start();
    }

    private void initButtons() {
        findViewById(R.id.startBtn).setOnClickListener(e -> startActivity(new Intent(getApplicationContext(), GameActivity.class)));
        findViewById(R.id.helpBtn).setOnClickListener(e -> startActivity(new Intent(getApplicationContext(), HelpActivity.class)));
        findViewById(R.id.settingsBtn).setOnClickListener(e -> startActivity(new Intent(getApplicationContext(), Settings.class)));
        findViewById(R.id.endBtn).setOnClickListener(e -> {
            finish();
            System.exit(0);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), Settings.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (languageChanged) {
            languageChanged = false;
            finish();
            startActivity(getIntent());
        }
    }

}