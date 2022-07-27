package android.thesoccerbox.smartbox;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.UUID;

public class ResultsActivity extends SingleFragmentActivity {

    public static final String GAME_ID = "android.smart_box.results.game_id";

    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "ResultsActivity creating fragment");
        return new ResultsFragment();
    }

    public static Intent newIntent(Context packageContext, UUID gameID) {
        /*
         * This method can be used to create intents bound for this activity with the game ID
         */
        Intent intent = new Intent(packageContext, ResultsActivity.class);
        intent.putExtra(GAME_ID, gameID);
        return intent;
    }
}