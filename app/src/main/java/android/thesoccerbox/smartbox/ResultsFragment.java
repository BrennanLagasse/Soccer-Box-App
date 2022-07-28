package android.thesoccerbox.smartbox;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResultsFragment extends Fragment {

    private Game mGame;
    private int[] mScores;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Receive intent with relevant information
        UUID gameId = (UUID) getActivity().getIntent()
                .getSerializableExtra(ResultsActivity.GAME_ID);
        mGame = GameManager.get(getActivity()).getGame(gameId);

        mScores = getActivity().getIntent().getIntArrayExtra(ResultsActivity.SCORES);

        // WIP!!! Run any code related to saving data online here
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.results_fragment, container, false);

        View mRoom1 = view.findViewById(R.id.room_one_scores);
        View mRoom2 = view.findViewById(R.id.room_two_scores);
        View mRoom3 = view.findViewById(R.id.room_three_scores);
        View mRoom4 = view.findViewById(R.id.room_four_scores);

        List<View> rooms = new ArrayList<View>();
        rooms.add(mRoom1);
        rooms.add(mRoom2);
        rooms.add(mRoom3);
        rooms.add(mRoom4);

        for(int i = 0; i < rooms.size(); i++) {
            View room = rooms.get(i);
            TextView title = room.findViewById(R.id.room_number);
            TextView p1 = room.findViewById(R.id.score_p1);
            TextView p2 = room.findViewById(R.id.score_p2);
            title.setText(getString(R.string.room_title, Integer.toString(i+1)));
            if(mGame.getNumPlayers() > 0) {
                p1.setText(Integer.toString(mScores[i * 2]));
                if(mGame.getNumPlayers() > 1) {
                    p2.setText(Integer.toString(mScores[i * 2 + 1]));
                }
            }
        }

        Button mPlayAgain;
        Button mNewGame;
        Button mShutDown;

        // If the user asks to play again, return to the settings page and send game choice
        mPlayAgain = view.findViewById(R.id.play_again_button);
        mPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = GameSettingsActivity.newIntent(getActivity(), mGame.getId());
                startActivity(intent);
            }
        });


        // If the user selects new game, go back to the start (BoxActivity)
        mNewGame = view.findViewById(R.id.new_game);
        mNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BoxActivity.class);
                startActivity(intent);
            }
        });

        // Watch the shutdown button and send command to the pi
        mShutDown = view.findViewById(R.id.shut_down);
        mShutDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Alert the user that the shutdown sequence started
                Toast.makeText(getActivity(), "Shutting Down", Toast.LENGTH_SHORT).show();

                //WIP!!! Add shutdown code
            }
        });

        return view;
    }

}
