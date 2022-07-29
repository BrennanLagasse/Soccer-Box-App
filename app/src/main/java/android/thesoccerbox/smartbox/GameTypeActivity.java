package android.thesoccerbox.smartbox;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.util.UUID;

public class GameTypeActivity extends SingleFragmentActivity {

    public static final String EXTRA_ROOMS = "android.smart_box.game_type.rooms";

    @Override
    protected Fragment createFragment() {
        return new PlayerFragment();
    }


    public static Intent newIntent(Context packageContext, boolean[] rooms) {
        /* This method can be used to create intents bound for this activity with the rooms in use */
        Log.d(TAG, "Creating intent for GameTypeActivity");
        Intent intent = new Intent(packageContext, GameTypeActivity.class);
        intent.putExtra(EXTRA_ROOMS, rooms);
        return intent;
    }
}