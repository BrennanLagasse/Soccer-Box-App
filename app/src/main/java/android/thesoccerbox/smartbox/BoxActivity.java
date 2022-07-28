package android.thesoccerbox.smartbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.UUID;

public class BoxActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new BoxFragment();
    }

}