package android.thesoccerbox.smartbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.UUID;

public class GameSettingsFragment extends Fragment {

    private Game mGame;

    private TextView mTitleView;
    private Button mSynchButton;
    private Button mStartButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID gameId = (UUID) getActivity().getIntent()
                .getSerializableExtra(GameSettingsActivity.GAME_ID);
        mGame = GameManager.get(getActivity()).getGame(gameId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        mTitleView = view.findViewById(R.id.settings_title_text);
        mTitleView.setText((mGame.getTitle() + " Settings"));

        mSynchButton = view.findViewById(R.id.synchronous_button);

        // Set initial position and button enabled status based on if alternatives exist
        if(mGame.getSynch() && mGame.getAsynch()) {
            mSynchButton.setEnabled(true);
            mSynchButton.setText(getString(R.string.synchronous_status));
        }
        else if(mGame.getSynch()) {
            mSynchButton.setText(getString(R.string.synchronous_status));
        }
        else if(mGame.getAsynch()) {
            mSynchButton.setText(getString(R.string.asynchronous_status));
        }

        //Add event listener to change the status if the button is enabled
        mSynchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSynchButton.getText().equals(getString(R.string.synchronous_status))) {
                    mSynchButton.setText(getString(R.string.asynchronous_status));
                }
                else {
                    mSynchButton.setText(getString(R.string.synchronous_status));
                }
            }
        });

        mStartButton = view.findViewById(R.id.start_button);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Use mGame to send relevant information to the live page
                Intent intent = LiveGameActivity.newIntent(getActivity(), mGame.getId());
                startActivity(intent);
            }
        });

        return view;
    }
}
