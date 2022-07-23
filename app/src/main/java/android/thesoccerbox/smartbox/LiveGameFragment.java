package android.thesoccerbox.smartbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class LiveGameFragment extends Fragment {

    private Game mGame;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID gameId = (UUID) getActivity().getIntent()
                .getSerializableExtra(GameSettingsActivity.GAME_ID);
        mGame = GameManager.get(getActivity()).getGame(gameId);

        // Start code
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.live_game, container, false);

        return view;
    }
}
