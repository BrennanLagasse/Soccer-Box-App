package android.thesoccerbox.smartbox;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.UUID;

public class ResultsActivity extends SingleFragmentActivity {

    public static final String EXTRA_ROOMS = "android.smart_box.results.rooms";
    public static final String GAME_ID = "android.smart_box.results.game_id";
    public static final String SCORES = "android.smart_box.results.scores";

    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "ResultsActivity creating fragment");
        return new ResultsFragment();
    }

    public static Intent newIntent(Context packageContext, boolean[] rooms, UUID gameID, int[] scores) {
        /* Creates intent bound for this activity with rooms, game ID, and scores */
        Intent intent = new Intent(packageContext, ResultsActivity.class);
        intent.putExtra(EXTRA_ROOMS, rooms);
        intent.putExtra(GAME_ID, gameID);
        intent.putExtra(SCORES, scores);
        return intent;
    }
}