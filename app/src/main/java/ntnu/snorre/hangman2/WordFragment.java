package ntnu.snorre.hangman2;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WordFragment extends Fragment {


    final static int WRONG_LETTER = -1;
    final static int RIGHT_LETTER = 1;
    final static int WON_ROUND = 0;
    private String currentWord;
    private char[] displayWord;
    private TextView wordDisplayTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word, container, false);
        wordDisplayTxt = view.findViewById(R.id.wordDisplayTxt);
        return view;
    }

    void roundLost() {
        wordDisplayTxt.setText(currentWord);
        wordDisplayTxt.setTextColor(Color.RED);
    }

    private void initDisplay() {
        displayWord = new char[currentWord.length()];
        for (int i = 0; i < currentWord.length(); i++) {
            displayWord[i] = '_';
            if (currentWord.toCharArray()[i] == ' ') {
                displayWord[i] = ' ';
            }
        }
        wordDisplayTxt.setText(displayWord, 0, displayWord.length);
    }

    int checkLetter(char letter) {
        if (currentWord.toUpperCase().indexOf(letter) == -1) {
            return WRONG_LETTER;
        }
        for (int i = 0; i < currentWord.length(); i++) {
            if (currentWord.toUpperCase().toCharArray()[i] == letter) {
                displayWord[i] = currentWord.toCharArray()[i];
                wordDisplayTxt.setText(displayWord, 0, displayWord.length);
                if (new String(displayWord).indexOf('_') == -1) {
                    wordDisplayTxt.setTextColor(Color.GREEN);
                    return WON_ROUND;
                }
            }
        }
        return RIGHT_LETTER;
    }

    void setNewWord(String word) {
        wordDisplayTxt.setTextColor(Color.BLACK);
        currentWord = word;
        initDisplay();
    }
}
