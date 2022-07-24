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

    public static final String GAME_ID = "android.smart_box.live.game_id";

    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "LiveGameActivity creating fragment");
        return new LiveGameFragment();
    }

    public static Intent newIntent(Context packageContext, UUID gameID) {
        /*
         * This method can be used to create intents bound for this activity with the number of players
         */
        Intent intent = new Intent(packageContext, LiveGameActivity.class);
        intent.putExtra(GAME_ID, gameID);
        return intent;
    }


}