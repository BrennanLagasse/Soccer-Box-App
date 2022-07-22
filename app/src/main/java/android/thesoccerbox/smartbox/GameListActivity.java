package android.thesoccerbox.smartbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.content.ContentValues.TAG;

public class GameListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "GameListActivity creating fragment");
        return new GameListFragment();
    }
}