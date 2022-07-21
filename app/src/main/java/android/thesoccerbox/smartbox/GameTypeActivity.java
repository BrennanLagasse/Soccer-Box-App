package android.thesoccerbox.smartbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class GameTypeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PlayerFragment();
    }
}