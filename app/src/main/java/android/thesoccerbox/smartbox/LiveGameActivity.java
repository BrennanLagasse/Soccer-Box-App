package android.thesoccerbox.smartbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import static android.content.ContentValues.TAG;
import static java.lang.String.valueOf;

public class LiveGameActivity extends SingleFragmentActivity {

    public static final String EXTRA_ROOMS = "android.smart_box.live.rooms";
    public static final String EXTRA_GAME_ID = "android.smart_box.live.game_id";
    public static final String EXTRA_TARGET_TIME = "android.smart_box.live.target_time";
    public static final String EXTRA_GAME_TIME = "android.smart_box.live.game_time";
    public static final String EXTRA_PATH = "android.smart_box.live.path";

    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "LiveGameActivity creating fragment");
        return new LiveGameFragment();
    }

    public static Intent newIntent(Context packageContext, boolean[] rooms, UUID gameID, double targetTime, double gameTime, boolean defaultPath) {
        /* This method can be used to create intents bound for this activity with the game ID */
        Intent intent = new Intent(packageContext, LiveGameActivity.class);
        intent.putExtra(EXTRA_ROOMS, rooms);
        intent.putExtra(EXTRA_GAME_ID, gameID);
        intent.putExtra(EXTRA_TARGET_TIME, targetTime);
        intent.putExtra(EXTRA_GAME_TIME, gameTime);
        intent.putExtra(EXTRA_PATH, defaultPath);
        return intent;
    }


}