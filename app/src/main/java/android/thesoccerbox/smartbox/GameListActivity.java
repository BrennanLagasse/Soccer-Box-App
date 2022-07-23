package android.thesoccerbox.smartbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static java.lang.String.valueOf;

public class GameListActivity extends SingleFragmentActivity {

    private static final String EXTRA_NUM_PLAYERS = "android.smartbox.num_players";

    private int mNumPlayers;

    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "GameListActivity creating fragment");
        return new GameListFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNumPlayers = getIntent().getIntExtra(EXTRA_NUM_PLAYERS, 0);
        Log.d(TAG, valueOf(mNumPlayers));
    }

    public static Intent newIntent(Context packageContext, int numPlayers) {
        /**
         * This method can be used to create intents bound for this activity with a list of games
         */
        Intent intent = new Intent(packageContext, GameListActivity.class);
        intent.putExtra(EXTRA_NUM_PLAYERS, numPlayers);
        return intent;
    }

    public int getNumPlayers() {
        return mNumPlayers;
    }
}