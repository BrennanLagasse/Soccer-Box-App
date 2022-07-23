package android.thesoccerbox.smartbox;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameManager {
    /**
     * Class that holds and organizes all of the games for general use
     */

    private static GameManager sCrimeLab;

    private List<Game> mOnePlayerGames = new ArrayList<Game>();
    private List<Game> mTwoPlayerGames = new ArrayList<Game>();
    private List<Game> mTestGames = new ArrayList<Game>();

    public static GameManager get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new GameManager(context);
        }
        return sCrimeLab;
    }

    private GameManager(Context context) {
        mOnePlayerGames.add(new Game("Standard", "One target", "???"));
        mOnePlayerGames.add(new Game("Standard Next", "One target with next target indicated", "???"));
        mOnePlayerGames.add(new Game("Standard Color", "All targets lit, hit the correct color", "???"));
        mOnePlayerGames.add(new Game("Double Tap Standard", "One target that must be hit twice", "???"));
        mOnePlayerGames.add(new Game("Two Targets Standard", "Two targets, either must be hit", "???"));
        mOnePlayerGames.add(new Game("Both Targets Standard", "Two targets, both must be hit", "???"));
        mOnePlayerGames.add(new Game("Rondo Scan", "One target, front only", "???"));
        mOnePlayerGames.add(new Game("Shoulder Check 1", "Hit front target matching back target", "???"));
        mOnePlayerGames.add(new Game("Shoulder Check 2", "Hit front target matching back target", "???"));
        mOnePlayerGames.add(new Game("Color Find", "Hit target color announced by speaker", "???"));

        mTwoPlayerGames.add(new Game("Standard Simultaneous", "One target for each player, targets are unlinked", "???"));
        mTwoPlayerGames.add(new Game("Standard Competitive", "One target for each player, targets are reset after either target is hit", "???"));
        mTwoPlayerGames.add(new Game("Standard Next", "One target for each player, next targets are indicated", "???"));
        mTwoPlayerGames.add(new Game("Standard Color", "All targets lit, each player must hit their color", "???"));
        mTwoPlayerGames.add(new Game("Double Tap Standard", "One target for each player that must be hit twice", "???"));
        mTwoPlayerGames.add(new Game("Two Targets Standard", "Two targets for each player, either must be hit", "???"));
        mTwoPlayerGames.add(new Game("Both Targets Standard", "Two targets for each player, both must be hit", "???"));
        mTwoPlayerGames.add(new Game("Three Goal Game First", "Each player must hit the opposing three targets once", "???"));
        mTwoPlayerGames.add(new Game("Three Goal Game Timed", "Each player must hit the opposing three targets", "???"));

        mTestGames.add(new Game("LED Test", "Lights up all LEDs one target at a time", "???"));
        mTestGames.add(new Game("Scoreboard Test", "Displays score on screen", "???"));
        mTestGames.add(new Game("Speaker Test", "Cycles through all game sounds", "???"));

    }



    public List<Game> getOnePlayerGames() {
        return mOnePlayerGames;
    }

    public List<Game> getTwoPlayerGames() {
        return mTwoPlayerGames;
    }

    public List<Game> getTestGames() {
        return mTestGames;
    }

    public Game getGame(UUID id) {
        for (Game game : mOnePlayerGames) {
            if (game.getId().equals(id)) {
                return game;
            }
        }
        for (Game game : mTwoPlayerGames) {
            if (game.getId().equals(id)) {
                return game;
            }
        }
        for (Game game : mTestGames) {
            if (game.getId().equals(id)) {
                return game;
            }
        }
        return null;
    }
}