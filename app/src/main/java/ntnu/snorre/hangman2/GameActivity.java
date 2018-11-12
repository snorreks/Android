package ntnu.snorre.hangman2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Set;

public class GameActivity extends AppCompatActivity {

    private ImageView img;
    private TypedArray pictureStages;
    private int count = 0;
    private ScoreFragment scoreFragment;
    private WordFragment wordFragment;
    private Dictionary dictionary;
    private boolean doneWRound = false;
    private Button nextBtn;
    private GridView alphabetContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initDictionary();
        initFragments();
        initAlphabetContainer();
        initImage();
        nextBtn = findViewById(R.id.nextBtn);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initAlphabetContainer() {
        alphabetContainer = findViewById(R.id.alphabet_container);
        alphabetContainer.setAdapter(new ButtonAdapter(this, getResources().getStringArray(R.array.alphabet)));
    }

    private void setLettersState(boolean state) {
        for (int i = 0; i < alphabetContainer.getChildCount(); i++) {
            View button = alphabetContainer.getChildAt(i);
            button.setEnabled(state);
        }
    }

    private void initImage() {
        pictureStages = getResources().obtainTypedArray(R.array.pictureStages);
        img = findViewById(R.id.image_container);
        updateImg();
    }

    private void initDictionary() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String language = preferences.getString("language", "en");
        Set<String> categories = preferences.getStringSet("categories", null);
        dictionary = new Dictionary(language, categories);
    }

    private void initFragments() {
        scoreFragment = new ScoreFragment();
        wordFragment = new WordFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.score_container, scoreFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.word_container, wordFragment).commit();
    }

    private void updateImg() {
        img.setImageDrawable(pictureStages.getDrawable(count));
    }

    private void letterClicked(Button btn) {
        btn.setEnabled(false);
        int response = wordFragment.checkLetter(btn.getText().charAt(0));
        if (response == WordFragment.WRONG_LETTER) {
            wrongLetter();
        }
        if (response == WordFragment.WON_ROUND) {
            doneWRound = true;
            scoreFragment.roundWon();
            setLettersState(false);
        }
    }


    public void nextBtnClicked(View v) {
        if (doneWRound) startNewRound();
        else {
            scoreFragment.roundLost();
            wordFragment.roundLost();
            startNewRound();
        }
    }

    public void startNewRound() {
        count = 0;
        doneWRound = false;
        setLettersState(true);
        scoreFragment.nextRound();
        if (dictionary.wordList.size() == 1) nextBtn.setText(getString(R.string.finish));
        if (dictionary.wordList.empty()) {
            this.finish();
            startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
            return;
        }
        wordFragment.setNewWord(dictionary.wordList.pop());
        updateImg();
    }

    private void wrongLetter() {
        count++;
        if (count >= 10) {
            scoreFragment.roundLost();
            wordFragment.roundLost();
            setLettersState(false);
            doneWRound = true;
        }
        updateImg();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (dictionary.wordList.empty()) {
            this.finish();
            Toast.makeText(this, getString(R.string.category_error), Toast.LENGTH_SHORT).show();
            return;
        }
        scoreFragment.setTotalRounds(dictionary.wordList.size());
        wordFragment.setNewWord(dictionary.wordList.pop());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                finish();
                startActivity(new Intent(getApplicationContext(), Settings.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ButtonAdapter extends BaseAdapter {
        private Context mContext;
        private String[] alphabet;

        // Gets the context so it can be used later
        public ButtonAdapter(Context c, String[] alphabet) {
            this.alphabet = alphabet;
            mContext = c;
        }

        // Total number of things contained within the adapter
        public int getCount() {
            return alphabet.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position,
                            View convertView, ViewGroup parent) {
            Button btn;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                btn = new Button(mContext);
            } else {
                btn = (Button) convertView;
            }
            btn.setOnClickListener(e -> letterClicked(btn));
            btn.setText(alphabet[position].toUpperCase());
            return btn;
        }
    }
}