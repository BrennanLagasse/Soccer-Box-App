package android.thesoccerbox.smartbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class ResultsFragment extends Fragment {

    private Game mGame;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Receive intent with relevant information
        UUID gameId = (UUID) getActivity().getIntent()
                .getSerializableExtra(LiveGameActivity.GAME_ID);
        mGame = GameManager.get(getActivity()).getGame(gameId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.live_game, container, false);

        return view;
    }

}
