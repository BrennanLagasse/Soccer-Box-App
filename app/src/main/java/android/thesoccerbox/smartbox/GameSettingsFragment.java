package android.thesoccerbox.smartbox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.UUID;

import static android.content.ContentValues.TAG;

public class GameSettingsFragment extends Fragment {

    private Game mGame;
    private boolean[] mRooms;

    private TextView mTitleView;
    private Button mSyncButton;
    private Button mStartButton;

    private View mGameNumberView;
    private EditText mValue1;
    private Button mMinus1;
    private Button mPlus1;

    private View mGameTimeView;
    private EditText mValue2;
    private Button mMinus2;
    private Button mPlus2;

    private View mGameDelayView;
    private EditText mValue3;
    private Button mMinus3;
    private Button mPlus3;

    private View mTargetTimeView;
    private EditText mValue4;
    private Button mMinus4;
    private Button mPlus4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID gameId = (UUID) getActivity().getIntent()
                .getSerializableExtra(GameSettingsActivity.GAME_ID);
        mGame = GameManager.get(getActivity()).getGame(gameId);

        mRooms = getActivity().getIntent().getBooleanArrayExtra(GameSettingsActivity.EXTRA_ROOMS);
        Log.d(TAG, ("GSF Room 1: " + mRooms[0]));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        mTitleView = view.findViewById(R.id.settings_title_text);
        mTitleView.setText((mGame.getTitle() + " Settings"));

        // Game Number
        {
            mGameNumberView = view.findViewById(R.id.num_games_selection);

            TextView mTitle = mGameNumberView.findViewById(R.id.value_label_text);
            mTitle.setText(getString(R.string.game_number_prompt));

            mValue1 = mGameNumberView.findViewById(R.id.value_text);

            mMinus1 = mGameNumberView.findViewById(R.id.minus_button);
            mMinus1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int val = Integer.parseInt(mValue1.getText().toString());

                    if (val > 1) {
                        String newVal = Integer.toString(val - 1);
                        mValue1.setText(newVal);
                    }
                }
            });

            mPlus1 = mGameNumberView.findViewById(R.id.plus_button);
            mPlus1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int val = Integer.parseInt(mValue1.getText().toString());
                    String newVal = Integer.toString(val + 1);
                    mValue1.setText(newVal);
                }
            });
        }

        //Game Time
        {
            mGameTimeView = view.findViewById(R.id.game_time_selection);

            TextView mTitle = mGameTimeView.findViewById(R.id.value_label_text);
            mTitle.setText(getString(R.string.game_time_prompt));

            mValue2 = mGameTimeView.findViewById(R.id.value_text);
            mValue2.setText(getString(R.string.default_game_length));

            mMinus2 = mGameTimeView.findViewById(R.id.minus_button);
            mMinus2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int val = Integer.parseInt(mValue2.getText().toString());

                    if (val > 1) {
                        String newVal = Integer.toString(val - 5);
                        mValue2.setText(newVal);
                    }
                }
            });

            mPlus2 = mGameTimeView.findViewById(R.id.plus_button);
            mPlus2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int val = Integer.parseInt(mValue2.getText().toString());
                    String newVal = Integer.toString(val + 5);
                    mValue2.setText(newVal);
                }
            });
        }

        //After Game Delay
        {
            mGameDelayView = view.findViewById(R.id.game_delay_selection);

            TextView mTitle = mGameDelayView.findViewById(R.id.value_label_text);
            mTitle.setText(getString(R.string.game_delay_prompt));

            mValue3 = mGameDelayView.findViewById(R.id.value_text);
            mValue3.setText(getString(R.string.default_delay_length));

            mMinus3 = mGameDelayView.findViewById(R.id.minus_button);
            mMinus3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int val = Integer.parseInt(mValue3.getText().toString());

                    if (val > 1) {
                        String newVal = Integer.toString(val - 10);
                        mValue3.setText(newVal);
                    }
                }
            });

            mPlus3 = mGameDelayView.findViewById(R.id.plus_button);
            mPlus3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int val = Integer.parseInt(mValue3.getText().toString());
                    String newVal = Integer.toString(val + 10);
                    mValue3.setText(newVal);
                }
            });
        }

        //Target Time
        {
            mTargetTimeView = view.findViewById(R.id.target_time_selection);

            TextView mTitle = mTargetTimeView.findViewById(R.id.value_label_text);
            mTitle.setText(getString(R.string.target_time_prompt));

            mValue4 = mTargetTimeView.findViewById(R.id.value_text);
            mValue4.setText(getString(R.string.default_target_time));

            mMinus4 = mTargetTimeView.findViewById(R.id.minus_button);
            mMinus4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int val = Integer.parseInt(mValue4.getText().toString());

                    if (val > 1) {
                        String newVal = Integer.toString(val - 1);
                        mValue4.setText(newVal);
                    }
                }
            });

            mPlus4 = mTargetTimeView.findViewById(R.id.plus_button);
            mPlus4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int val = Integer.parseInt(mValue4.getText().toString());
                    String newVal = Integer.toString(val + 1);
                    mValue4.setText(newVal);
                }
            });
        }

        mSyncButton = view.findViewById(R.id.synchronous_button);

        // Set initial position and button enabled status based on if alternatives exist
        if(mGame.getSynch() && mGame.getAsynch()) {
            mSyncButton.setEnabled(true);
            mSyncButton.setText(getString(R.string.synchronous_status));
        }
        else if(mGame.getSynch()) {
            mSyncButton.setText(getString(R.string.synchronous_status));
        }
        else if(mGame.getAsynch()) {
            mSyncButton.setText(getString(R.string.asynchronous_status));
        }

        //Add event listener to change the status if the button is enabled
        mSyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSyncButton.getText().equals(getString(R.string.synchronous_status))) {
                    mSyncButton.setText(getString(R.string.asynchronous_status));
                }
                else {
                    mSyncButton.setText(getString(R.string.synchronous_status));
                }
            }
        });

        mStartButton = view.findViewById(R.id.start_button);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Use mGame to send relevant information to the live page
                Intent intent = LiveGameActivity.newIntent(getActivity(), mRooms, mGame.getId());
                startActivity(intent);
            }
        });

        return view;
    }
}
