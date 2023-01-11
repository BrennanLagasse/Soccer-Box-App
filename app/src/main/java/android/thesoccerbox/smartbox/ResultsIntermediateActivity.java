package android.thesoccerbox.smartbox;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class ResultsIntermediateActivity extends SingleFragmentActivity {
    public static final String EXTRA_ROOMS = "android.smart_box.results_alt.rooms";
    public static final String EXTRA_GAME_ID = "android.smart_box.results_alt.game_id";
    public static final String EXTRA_TARGET_TIME = "android.smart_box.results_alt.target_time";
    public static final String EXTRA_GAME_TIME = "android.smart_box.results_alt.game_time";
    public static final String EXTRA_PATH = "android.smart_box.results_alt.path";
    public static final String EXTRA_NUM_GAMES = "android.smart_box.results_alt.num_games";
    public static final String EXTRA_DELAY_BETWEEN_GAMES = "android.smart_box.results_alt.delay_between_games";
    public static final String SCORES = "android.smart_box.results_alt.scores";


    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "ResultsIntermediateActivity creating fragment");
        return new ResultsIntermediateFragment();
    }

    public static Intent newIntent(Context packageContext, boolean[] rooms, UUID gameID, double targetTime,
                                   double gameTime, boolean defaultPath, int numGames, int delayBetweenGames, int[] scores) {
        /* Creates intent bound for this activity with rooms, game ID, and scores. Also takes remaining games and delay*/
        Intent intent = new Intent(packageContext, ResultsIntermediateActivity.class);
        intent.putExtra(EXTRA_ROOMS, rooms);
        intent.putExtra(EXTRA_GAME_ID, gameID);
        intent.putExtra(EXTRA_TARGET_TIME, targetTime);
        intent.putExtra(EXTRA_GAME_TIME, gameTime);
        intent.putExtra(EXTRA_PATH, defaultPath);
        intent.putExtra(EXTRA_NUM_GAMES, numGames);
        intent.putExtra(EXTRA_DELAY_BETWEEN_GAMES, delayBetweenGames);
        intent.putExtra(SCORES, scores);
        return intent;
    }
}
