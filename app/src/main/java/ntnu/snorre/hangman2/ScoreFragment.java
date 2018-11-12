package ntnu.snorre.hangman2;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ScoreFragment extends Fragment {


    private TextView winTxt;
    private TextView lossTxt;
    private TextView roundTxt;
    private TextView totalRoundsTxt;

    private int wins = 0;
    private int loss = 0;
    private int round = 1;
    private MediaPlayer correctMP;
    private MediaPlayer wrongMP;
    private Vibrator vibrator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);

        winTxt = view.findViewById(R.id.winTxt);
        lossTxt = view.findViewById(R.id.lossTxt);
        roundTxt = view.findViewById(R.id.roundTxt);
        totalRoundsTxt = view.findViewById(R.id.totalRoundsTxt);
        roundTxt.setText("" + round);

        correctMP = MediaPlayer.create(getContext(), R.raw.correct);
        wrongMP = MediaPlayer.create(getContext(), R.raw.ouff);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        return view;
    }

    void roundWon() {
        correctMP.start();
        wins++;
        winTxt.setText("" + wins);
    }

    void roundLost() {
        wrongMP.start();
        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        loss++;
        lossTxt.setText("" + loss);
    }

    void nextRound() {
        round++;
        roundTxt.setText("" + round);
    }

    void setTotalRounds(int size) {
        totalRoundsTxt.setText("" + size);
    }
}
