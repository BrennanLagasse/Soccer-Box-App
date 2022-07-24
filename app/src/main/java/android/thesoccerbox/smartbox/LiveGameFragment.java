package android.thesoccerbox.smartbox;

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

    private TextView mTitle;
    private TextView mConnectionStatus;
    private TextView mScoreTitle1;
    private TextView mScore1;
    private TextView mScoreTitle2;
    private TextView mScore2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID gameId = (UUID) getActivity().getIntent()
                .getSerializableExtra(LiveGameActivity.GAME_ID);
        mGame = GameManager.get(getActivity()).getGame(gameId);

        // Start game here (potentially in a new thread?)

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.live_game, container, false);

        mTitle = view.findViewById(R.id.game_title);
        mTitle.setText((mGame.getTitle() + " Game"));

        mConnectionStatus = view.findViewById(R.id.connection_status);

        mScoreTitle1 = view.findViewById(R.id.score_title_1);
        mScore1 = view.findViewById(R.id.score_value_1);
        mScoreTitle2 = view.findViewById(R.id.score_title_2);
        mScore2 = view.findViewById(R.id.score_value_2);

        // Default text is for players = 2
        if(mGame.getNumPlayers() == 1) {
            // Collapse second score and rewrite player 1 score to just score
            mScoreTitle1.setText(R.string.one_player_score_title);
            mScoreTitle2.setText("");
            mScore2.setText("");
        }
        else if(mGame.getNumPlayers() == 0) {
            // Set default scores for testing
            mScore1.setText("20");
            mScore2.setText("22");
        }

        return view;
    }

    public String executeCommand(String command) throws Exception {

        final String user = "ssh";
        final String password = "raspberry";
        final String host = "10.1.10.18";
        final int port = 22;

        try {
            Log.d(TAG, "Attempting to connect to Pi");

            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);

            // Avoid asking for key confirmation
            Properties prop = new Properties();
            prop.put("StrictHostKeyChecking", "no");
            session.setConfig(prop);

            session.connect();

            mConnectionStatus.setText(R.string.connected);

            // SSH Channel
            ChannelExec channelssh = (ChannelExec)
                    session.openChannel("exec");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            channelssh.setOutputStream(baos);

            // Execute command
            channelssh.setCommand(command);
            channelssh.connect();
            channelssh.disconnect();

            return baos.toString();
        }
        catch(Exception e) {
            Log.d(TAG, "Connection Issues 1");
            return "";
        }
    }
}
