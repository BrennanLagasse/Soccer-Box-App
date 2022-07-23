package android.thesoccerbox.smartbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.UUID;

import static android.content.ContentValues.TAG;

public class GameSettingsActivity extends SingleFragmentActivity {

    public static final String GAME_ID = "android.smart_box.game_id";

    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "GameSettingsActivity creating fragment");
        return new GameSettingsFragment();
    }

    public static Intent newIntent(Context packageContext, UUID gameID) {
        /*
         * This method can be used to create intents bound for this activity with the number of players
         */
        Intent intent = new Intent(packageContext, GameSettingsActivity.class);
        intent.putExtra(GAME_ID, gameID);
        return intent;
    }
}