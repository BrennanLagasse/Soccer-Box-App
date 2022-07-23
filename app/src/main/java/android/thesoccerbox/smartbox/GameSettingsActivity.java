package android.thesoccerbox.smartbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class GameSettingsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "GameSettingsActivity creating fragment");
        return new GameSettingsFragment();
    }
}