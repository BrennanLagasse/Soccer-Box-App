package android.thesoccerbox.smartbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class PlayerFragment extends Fragment {

    Button mOnePlayerButton;
    Button mTwoPlayerButton;
    Button mTestButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_players, container, false);

        mOnePlayerButton = v.findViewById(R.id.one_player_button);
        mTwoPlayerButton = v.findViewById(R.id.two_player_button);
        mTestButton = v.findViewById(R.id.test_button);

        // When the one player button is pressed, go to the one player game list
        mOnePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Link this to the one player game list
            }
        });

        // When the two player button is pressed, go to the two player game list
        mTwoPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Link this to the two player game list
            }
        });

        // When the test button is pressed, go to the test list
        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Link this to the test list
            }
        });



        return v;
    }
}
