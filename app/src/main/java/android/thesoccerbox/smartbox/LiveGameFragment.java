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
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Scanner;
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
    private int[] mRoomNums;
    private int mTargetTime;
    private int mGameTime;
    private boolean mDefaultPath;
    private int mNumGames;
    private int mDelayBetweenGames;
    private int[] mScores;

    private String CONNECTION_ERROR = "CONNECTION ERROR";

    private TextView mConnectionStatus;

    protected String results;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Convert the inputted boolean array to integer room numbers
        mRooms = getActivity().getIntent().getBooleanArrayExtra(LiveGameActivity.EXTRA_ROOMS);
        Log.d(TAG, ("LGF Room 1: " + mRooms[0]));

        int numRooms = 0;

        for(boolean r : mRooms) {
            if (r) {
                numRooms++;
            }
        }

        mRoomNums = new int[numRooms];

        int i = 0;

        for(int j = 0; j < mRooms.length; j++) {
            if (mRooms[j]) {
                mRoomNums[i] = j;
                i++;
            }
        }

        // Get the id of the game
        UUID gameId = (UUID) getActivity().getIntent()
                .getSerializableExtra(LiveGameActivity.EXTRA_GAME_ID);
        mGame = GameManager.get(getActivity()).getGame(gameId);

        // Get the game statistics
        mTargetTime = (int) getActivity().getIntent().getDoubleExtra(LiveGameActivity.EXTRA_TARGET_TIME, 5);
        mGameTime = (int) getActivity().getIntent().getDoubleExtra(LiveGameActivity.EXTRA_GAME_TIME, 60);

        // Get which path the code uses
        mDefaultPath = getActivity().getIntent().getBooleanExtra(LiveGameActivity.EXTRA_PATH, true);

        // Get the information about the number of games
        mNumGames = getActivity().getIntent().getIntExtra(LiveGameActivity.EXTRA_NUM_GAMES, 1);
        mDelayBetweenGames = getActivity().getIntent().getIntExtra(LiveGameActivity.EXTRA_DELAY_BETWEEN_GAMES, 90);

        // Create new variable to store the scores
        mScores = new int[mRooms.length * mGame.getNumPlayers()];

        // Create a function call to the selected game with command line arguments
        // Arguments: Num Rooms, array of room numbers, target time, game time
        String command;

        if (mDefaultPath) {
            command = mGame.getCodePath();
        }
        else {
            command = mGame.getAsyncCodePath();
        }

        command = command.concat(" " + mRoomNums.length);
        for(int room : mRoomNums) {
            command = command.concat(" " + room);
        }
        command = command.concat(" " + mTargetTime);
        command = command.concat(" " + mGameTime);

        // Print out the command sent to the system
        Log.d(TAG, "*************************** Sent Command " + command);

        new AsyncGame().execute(command);
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

            // Decide which page to go to based on results
            if (result.equals(CONNECTION_ERROR)) {
                // PI disconnected, alert user and go to disconnect page
                Intent intent = ConnectionErrorActivity.newIntent(
                        getActivity(),
                        mRooms,
                        mGame.getId(),
                        mTargetTime,
                        mGameTime,
                        mDefaultPath,
                        mNumGames,
                        mDelayBetweenGames);
                startActivity(intent);
            }
            else {
                // Pull Scores
                Scanner scan = new Scanner(result);

                // Sift through other input
                while (scan.hasNextLine()) {

                    String line = scan.nextLine();
                    Log.d(TAG, line);

                    if(line.charAt(0) == 's') {
                        // Update score
                        int room = Integer.parseInt(line.substring(2,3));
                        int player = Integer.parseInt(line.substring(4,5));
                        mScores[room*2+player] = Integer.parseInt(line.substring(6));
                    }
                }

                // Log scores
                Log.d(TAG, "***** Recorded Scores:");
                for(int s : mScores) {
                    Log.d(TAG, Integer.toString(s));
                }
                Log.d(TAG, "***** End of Scores:");

                // The game ends as expected, use mGame to send relevant information to the results page
                if(mNumGames <= 1) {
                    // Last game, go to final results page
                    Intent intent = ResultsActivity.newIntent(getActivity(), mRooms, mGame.getId(), mScores);
                    startActivity(intent);
                }
                else {
                    // At least one more game, go to intermediate results page
                    Intent intent = ResultsIntermediateActivity.newIntent(
                            getActivity(),
                            mRooms,
                            mGame.getId(),
                            mTargetTime,
                            mGameTime,
                            mDefaultPath,
                            mNumGames,
                            mDelayBetweenGames,
                            mScores);
                    startActivity(intent);
                }
            }

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
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            channelssh.setOutputStream(baos);

            Log.d(TAG, "********************* Running Command ************************");

            // Execute command
            channelssh.setCommand(command);
            OutputStream out = channelssh.getOutputStream();
            channelssh.connect();

            Log.d(TAG, command);

            // Listen for score updates until end command is send
            while(!baos.toString().contains("END")) {

                // Experimental code for searching through baos for information
                Scanner scan = new Scanner(baos.toString());

                // Sift through other input
                while (scan.hasNextLine()) {
                    Log.d(TAG, "CHECKING A LINE");
                    String line = scan.nextLine();
                    Log.d(TAG, line);


                    if(line.charAt(0) == 's') {
                        // Update score
                        Log.d(TAG, "************* Update score **************");
                        int room = Integer.parseInt(line.substring(2,3));
                        int player = Integer.parseInt(line.substring(4,5));
                        mScores[room*2+player] = Integer.parseInt(line.substring(6));
                    }

                }
                scan.close();

                baos.reset();

                // Take a quick break
                Thread.sleep(100);
            }

            channelssh.disconnect();

            Log.d(TAG, "********************* Command Ran ************************");

            String output = baos.toString();

            Log.d(TAG, ("Output: " + output));
            return output;
        }
        catch(Exception e) {
            Log.d(TAG, "********************* Connection Issues!! ************************");
            Log.d(TAG, e.toString());
            return CONNECTION_ERROR;
        }
    }
}