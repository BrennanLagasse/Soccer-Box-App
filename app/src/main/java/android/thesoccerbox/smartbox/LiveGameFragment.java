package android.thesoccerbox.smartbox;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Executor;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import static android.content.ContentValues.TAG;

public class LiveGameFragment extends Fragment {

    private Game mGame;
    private boolean[] mRooms;
    private int[] mScores = {0, 1, 0, 0, 0, 0, 0, 0};

    private TextView mConnectionStatus;

    private ByteArrayOutputStream baos;

    protected String results;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRooms = getActivity().getIntent().getBooleanArrayExtra(LiveGameActivity.EXTRA_ROOMS);
        Log.d(TAG, ("LGF Room 1: " + mRooms[0]));

        UUID gameId = (UUID) getActivity().getIntent()
                .getSerializableExtra(LiveGameActivity.EXTRA_GAME_ID);
        mGame = GameManager.get(getActivity()).getGame(gameId);

        new AsyncGame().execute(mGame.getCodePath());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.live_game, container, false);

        TextView mTitle;

        mTitle = view.findViewById(R.id.game_title);
        mTitle.setText(getString(R.string.game_title, mGame.getTitle()));

        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Use mGame to send relevant information to the results page
                Intent intent = ResultsActivity.newIntent(getActivity(), mRooms, mGame.getId(), mScores);
                startActivity(intent);
            }
        });

        mConnectionStatus = view.findViewById(R.id.connection_status);

        return view;
    }

    private class AsyncGame
            extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            return executeCommand(url);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // do something with the result
            Log.d(TAG, "************************* It finished!!! :) ***********************");
            Log.d(TAG, result);

            //Use mGame to send relevant information to the results page
            Intent intent = ResultsActivity.newIntent(getActivity(), mRooms, mGame.getId(), mScores);
            startActivity(intent);
        }
    }

    public String executeCommand(String command) {
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
            channelssh.setCommand(command);
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
