package android.thesoccerbox.smartbox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import static android.content.ContentValues.TAG;

public class PlayerFragment extends Fragment {

    private boolean[] mRooms;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "PlayerFragment created :)");
        super.onCreate(savedInstanceState);

        mRooms = getActivity().getIntent().getBooleanArrayExtra(GameTypeActivity.EXTRA_ROOMS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_players, container, false);

        Button mOnePlayerButton;
        Button mTwoPlayerButton;
        Button mTestButton;

        mOnePlayerButton = v.findViewById(R.id.one_player_button);
        mTwoPlayerButton = v.findViewById(R.id.two_player_button);
        mTestButton = v.findViewById(R.id.test_button);

        // When the one player button is pressed, go to the one player game list
        mOnePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Going to GameList :)");
                Intent intent = GameListActivity.newIntent(getActivity(), mRooms, 1);
                startActivity(intent);
            }
        });

        // When the two player button is pressed, go to the two player game list
        mTwoPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = GameListActivity.newIntent(getActivity(), mRooms, 2);
                startActivity(intent);
            }
        });

        // When the test button is pressed, go to the test list
        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = GameListActivity.newIntent(getActivity(), mRooms, 0);
                startActivity(intent);
            }
        });



        return v;
    }
}
