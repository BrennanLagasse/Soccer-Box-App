package android.thesoccerbox.smartbox;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class ResultsFragment extends Fragment {

    private boolean[] mRooms;
    private Game mGame;
    private int[] mScores;

    private ByteArrayOutputStream baos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRooms = getActivity().getIntent().getBooleanArrayExtra(ResultsActivity.EXTRA_ROOMS);

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

        Button mPlayAgain;
        Button mNewGame;
        Button mShutDown;

        // If the user asks to play again, return to the settings page and send game choice
        mPlayAgain = view.findViewById(R.id.play_again_button);
        mPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = GameSettingsActivity.newIntent(getActivity(), mRooms, mGame.getId());
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
                Log.d(TAG, "************************* Shutdown Button ***********************");
                Toast.makeText(getActivity(), "Shutting Down", Toast.LENGTH_SHORT).show();

                // Send the shutdown command
                new ResultsFragment.AsyncGame().execute();
            }
        });

        return view;
    }

    private class AsyncGame
            extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "************************* Starting Shutdown ***********************");
            return executeShutdown();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // do something with the result
            Log.d(TAG, "************************* It finished!!! :) ***********************");
            Toast.makeText(getActivity(), "Shutdown Scheduled", Toast.LENGTH_SHORT).show();
        }
    }

    public String executeShutdown() {
        /*
         *  Connects to a raspberry pi and runs the given command
         */

        final String user = "pi";
        final String password = "raspberry";
        final String host = "10.1.10.18";
        final int port = 22;

        try {
            Log.d(TAG, "********************* Setting up Connection ************************");
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);

            // Avoid asking for key confirmation
            Properties prop = new Properties();
            prop.put("StrictHostKeyChecking", "no");
            session.setConfig(prop);

            Log.d(TAG, "********************* Connecting Session ************************");

            session.connect();

            Log.d(TAG, "********************* Session Connected ************************");

            // SSH Channel
            ChannelExec channelssh = (ChannelExec)
                    session.openChannel("exec");
            baos = new ByteArrayOutputStream();
            channelssh.setOutputStream(baos);

            Log.d(TAG, "********************* Running Command ************************");

            // Execute command
            channelssh.setCommand("sudo shutdown");
            channelssh.connect();
            channelssh.disconnect();

            Log.d(TAG, "********************* Command Run ************************");

            return baos.toString();
        }
        catch(Exception e) {
            Log.d(TAG, "********************* Connection Issues!! ************************");
            Log.d(TAG, e.toString());
            return "";
        }
    }

}
