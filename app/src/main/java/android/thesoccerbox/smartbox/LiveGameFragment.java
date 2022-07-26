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

    private ByteArrayOutputStream baos;

    protected String results;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID gameId = (UUID) getActivity().getIntent()
                .getSerializableExtra(LiveGameActivity.GAME_ID);
        mGame = GameManager.get(getActivity()).getGame(gameId);

        // Start game here (potentially in a new thread?)
        Executor executor = new Executor() {
            @Override
            public void execute(Runnable runnable) {
                runnable.run();
            }
        };

        executor.execute(new Runnable() {
            @Override
            public void run() {
                results = executeCommand(mGame.getCodePath());
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.live_game, container, false);

        mTitle = view.findViewById(R.id.game_title);
        mTitle.setText(getString(R.string.game_title, mGame.getTitle()));

        mConnectionStatus = view.findViewById(R.id.connection_status);

        return view;
    }

    public String executeCommand(String command) {
        /*
        *  Connects to a raspberry pi and runs the given command
        */

        final String user = "ssh";
        final String password = "raspberry";
        final String host = "10.1.10.18";
        final int port = 22;

        try {
            Log.d(TAG, "*****Attempting to connect to Pi*****");

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
            baos = new ByteArrayOutputStream();
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
