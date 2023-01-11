package android.thesoccerbox.smartbox;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class ConnectionErrorActivity extends SingleFragmentActivity {

    public static final String EXTRA_ROOMS = "android.smart_box.error.rooms";
    public static final String EXTRA_GAME_ID = "android.smart_box.error.game_id";
    public static final String EXTRA_TARGET_TIME = "android.smart_box.error.target_time";
    public static final String EXTRA_GAME_TIME = "android.smart_box.error.game_time";
    public static final String EXTRA_PATH = "android.smart_box.error.path";
    public static final String EXTRA_NUM_GAMES = "android.smart_box.error.num_games";
    public static final String EXTRA_DELAY_BETWEEN_GAMES = "android.smart_box.error.delay_between_games";

    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "ConnectionErrorActivity creating fragment");
        return new ConnectionErrorFragment();
    }

    public static Intent newIntent(Context packageContext, boolean[] rooms, UUID gameID, double targetTime,
                                   double gameTime, boolean defaultPath, int numGames, int delayBetweenGames) {
        /* This method can be used to create intents bound for this activity with the game ID */
        // This state should only be reached when a game is attempted, so all variables are saved
        Intent intent = new Intent(packageContext, ConnectionErrorActivity.class);
        intent.putExtra(EXTRA_ROOMS, rooms);
        intent.putExtra(EXTRA_GAME_ID, gameID);
        intent.putExtra(EXTRA_TARGET_TIME, targetTime);
        intent.putExtra(EXTRA_GAME_TIME, gameTime);
        intent.putExtra(EXTRA_PATH, defaultPath);
        intent.putExtra(EXTRA_NUM_GAMES, numGames);
        intent.putExtra(EXTRA_DELAY_BETWEEN_GAMES, delayBetweenGames);
        return intent;
    }
}
