package android.thesoccerbox.smartbox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.content.ContentValues.TAG;

public class GameListFragment extends Fragment {

    private RecyclerView mGameRecyclerView;
    private GameAdapter mAdapter;

    private int mNumPlayers;
    private boolean[] mRooms;

    private List<Game> mGameList;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        GameListActivity a = (GameListActivity) getActivity();
        mNumPlayers = a.getNumPlayers();
        Log.d(TAG, ("Num Players: " + mNumPlayers));

        mRooms = getActivity().getIntent().getBooleanArrayExtra(GameListActivity.EXTRA_ROOMS);
        Log.d(TAG, ("GLF Room 1: " + mRooms[0]));

        GameManager gameManager = GameManager.get(getActivity());

        if(mNumPlayers == 1) {
            mGameList = gameManager.getOnePlayerGames();
        }
        else if(mNumPlayers == 2) {
            mGameList = gameManager.getTwoPlayerGames();
        }
        else {
            mGameList = gameManager.getTestGames();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_games, container, false);

        mGameRecyclerView = view.findViewById(R.id.games_recycler_view);
        mGameRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        mAdapter = new GameAdapter();
        mGameRecyclerView.setAdapter(mAdapter);
    }

    private class GameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Game mGame;
        private TextView mTitle;
        private TextView mDescription;

        public GameHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_game, parent, false));
            itemView.setOnClickListener(this);

            mTitle = itemView.findViewById(R.id.game_title);
            mDescription = itemView.findViewById(R.id.game_description);
        }

        @Override
        public void onClick(View view) {
            //Determine next step based on number of players
            if(mNumPlayers == 1 || mNumPlayers == 2) {
                // Send mGame to the settings page to determine list of games
                Intent intent = GameSettingsActivity.newIntent(getActivity(), mRooms, mGame.getId());
                startActivity(intent);
            }
            else {
                // Send mGame to the live page and start immediately
                Intent intent = LiveGameActivity.newIntent(getActivity(), mRooms, mGame.getId());
                startActivity(intent);
            }

        }

        private void bind(Game game) {
            mGame = game;
            mTitle.setText(mGame.getTitle());
            mDescription.setText(mGame.getDescription());
        }
    }

    private class GameAdapter extends RecyclerView.Adapter<GameHolder> {

        public GameAdapter() {

        }

        @Override
        public GameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new GameHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(GameHolder holder, int position) {
            Game game = mGameList.get(position);
            holder.bind(game);
        }

        @Override
        public int getItemCount() {
            return mGameList.size();
        }
    }
}
