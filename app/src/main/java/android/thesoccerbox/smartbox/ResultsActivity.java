package android.thesoccerbox.smartbox;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

public class ResultsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "ResultsActivity creating fragment");
        return new ResultsFragment();
    }
}