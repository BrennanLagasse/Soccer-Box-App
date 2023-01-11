package android.thesoccerbox.smartbox;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import java.util.UUID;

import android.os.CountDownTimer;

public class ConnectionErrorFragment extends Fragment {

    private Button mReconnectButton;

    private UUID mGameId;
    private boolean[] mRooms;
    private double mTargetTime;
    private double mGameTime;
    private boolean mDefaultPath;
    private int mNumGames;
    private int mDelayBetweenGames;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Collect all the raw data from the intent (no processing)
        mRooms = getActivity().getIntent().getBooleanArrayExtra(ConnectionErrorActivity.EXTRA_ROOMS);
        mGameId = (UUID) getActivity().getIntent()
                .getSerializableExtra(ConnectionErrorActivity.EXTRA_GAME_ID);
        mTargetTime = getActivity().getIntent().getDoubleExtra(ConnectionErrorActivity.EXTRA_TARGET_TIME, 5);
        mGameTime = getActivity().getIntent().getDoubleExtra(ConnectionErrorActivity.EXTRA_GAME_TIME, 60);
        mDefaultPath = getActivity().getIntent().getBooleanExtra(ConnectionErrorActivity.EXTRA_PATH, true);
        mNumGames = getActivity().getIntent().getIntExtra(ConnectionErrorActivity.EXTRA_NUM_GAMES, 1);
        mDelayBetweenGames = getActivity().getIntent().getIntExtra(ConnectionErrorActivity.EXTRA_DELAY_BETWEEN_GAMES, 90);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_error, container, false);

        mReconnectButton = v.findViewById(R.id.reconnect_button);

        mReconnectButton.setEnabled(false);

        // Set a timer before reconnect is available again
        new CountDownTimer(5000, 1000){
            public void onTick(long millisUntilFinished){
                mReconnectButton.setText(String.valueOf("Retry in " + millisUntilFinished / 1000 + "s"));
            }
            public void onFinish(){
                mReconnectButton.setText(R.string.connection_error_retry);
                mReconnectButton.setEnabled(true);
            }
        }.start();

        // Change status of button 1 when selected
        mReconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Attempt to reconnect
                Log.d(TAG, "********************************* Attempting to Reconnect");
                Intent intent = LiveGameActivity.newIntent(getActivity(), mRooms, mGameId, mTargetTime, mGameTime, mDefaultPath, mNumGames, mDelayBetweenGames);
                startActivity(intent);
            }
        });

        return v;
    }

}
