package android.thesoccerbox.smartbox;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResultsIntermediateFragment extends Fragment {

    private boolean[] mRooms;
    private Game mGame;
    private double mTargetTime;
    private double mGameTime;
    private boolean mPath;
    private int mNumGames;
    private int mDelayBetweenGames;
    private int[] mScores;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRooms = getActivity().getIntent().getBooleanArrayExtra(ResultsIntermediateActivity.EXTRA_ROOMS);

        // Receive intent with relevant information
        UUID gameId = (UUID) getActivity().getIntent()
                .getSerializableExtra(ResultsIntermediateActivity.EXTRA_GAME_ID);
        mGame = GameManager.get(getActivity()).getGame(gameId);

        mTargetTime = getActivity().getIntent().getDoubleExtra(ResultsIntermediateActivity.EXTRA_TARGET_TIME, 5);
        mGameTime = getActivity().getIntent().getDoubleExtra(ResultsIntermediateActivity.EXTRA_GAME_TIME, 60);
        mPath = getActivity().getIntent().getBooleanExtra(ResultsIntermediateActivity.EXTRA_PATH, false);
        mNumGames = getActivity().getIntent().getIntExtra(ResultsIntermediateActivity.EXTRA_NUM_GAMES, 2);
        mDelayBetweenGames = getActivity().getIntent().getIntExtra(ResultsIntermediateActivity.EXTRA_DELAY_BETWEEN_GAMES, 90);

        mScores = getActivity().getIntent().getIntArrayExtra(ResultsIntermediateActivity.SCORES);

        // WIP!!! Run any code related to saving data online here
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.results_fragment_intermediate, container, false);

        Log.d(TAG, "***TT Results: " + mTargetTime);

        // Set up time until next game
        TextView nextGameDelay = view.findViewById(R.id.next_game_time);

        new CountDownTimer(mDelayBetweenGames * 1000L, 1000){
            public void onTick(long millisUntilFinished){
                nextGameDelay.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }
            public void onFinish(){
                // Decrement the number of remaining games and send back to the game runner
                Intent intent = LiveGameActivity.newIntent(
                        getActivity(),
                        mRooms,
                        mGame.getId(),
                        mTargetTime,
                        mGameTime,
                        mPath,
                        mNumGames - 1,
                        mDelayBetweenGames);
                startActivity(intent);
            }
        }.start();

        // Set up scores
        View mRoom1 = view.findViewById(R.id.room_one_scores);
        View mRoom2 = view.findViewById(R.id.room_two_scores);
        View mRoom3 = view.findViewById(R.id.room_three_scores);
        View mRoom4 = view.findViewById(R.id.room_four_scores);

        List<View> rooms = new ArrayList<>();
        rooms.add(mRoom1);
        rooms.add(mRoom2);
        rooms.add(mRoom3);
        rooms.add(mRoom4);

        for(int i = 0; i < rooms.size(); i++) {
            View room = rooms.get(i);
            TextView title = room.findViewById(R.id.room_number);
            TextView scores = room.findViewById(R.id.scores);

            if(mRooms[i]) {
                title.setText(getString(R.string.room_title, Integer.toString(i + 1)));
                if (mGame.getNumPlayers() > 0) {
                    scores.setText(Integer.toString(mScores[i * 2]));
                    if (mGame.getNumPlayers() > 1) {
                        scores.setText((scores.getText() + "\t\t\t\t\t" + Integer.toString(mScores[i * 2 + 1])));
                    }
                }
            }
        }

        return view;
    }
}
