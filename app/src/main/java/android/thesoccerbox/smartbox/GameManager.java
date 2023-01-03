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

    private final List<Game> mOnePlayerGames = new ArrayList<Game>();
    private final List<Game> mTwoPlayerGames = new ArrayList<Game>();
    private final List<Game> mTestGames = new ArrayList<Game>();

    public static GameManager get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new GameManager(context);
        }
        return sCrimeLab;
    }

    private GameManager(Context context) {
        // One Player Games
        mOnePlayerGames.add(new Game("Standard", "One target",
                1,  false, false,
                "1p_standard.py"));
        mOnePlayerGames.add(new Game("Standard Next", "One target with next target indicated",
                1, false, false,
                "1p_standard_next.py"));
        mOnePlayerGames.add(new Game("Standard Color", "All targets lit, hit the correct color",
                1, false, false,
                "1p_standard_color.py"));
        mOnePlayerGames.add(new Game("Double Tap Standard", "One target that must be hit twice",
                1, false, false,
                "1p_double_standard.py"));
        mOnePlayerGames.add(new Game("Two Targets Standard", "Two targets, either must be hit",
                1, false, false,
                "1p_two_targets.py"));
        mOnePlayerGames.add(new Game("Both Targets Standard", "Two targets, both must be hit",
                1, false, false,
                "1p_both_targets.py"));
        mOnePlayerGames.add(new Game("Rondo Scan", "One target, front only",
                1, false, false,
                "1p_rondo.py"));
        mOnePlayerGames.add(new Game("Shoulder Check Basic", "Hit front target matching back target",
                1, false, false,
                "1p_shoulder_check_basic.py"));
        mOnePlayerGames.add(new Game("Shoulder Check Advanced", "Hit front target matching back target",
                1, false, false,
                "1p_shoulder_check_advanced.py"));
        mOnePlayerGames.add(new Game("Color Find", "Hit target color announced by speaker",
                1, false, false,
                "1p_color_find.py"));

        // Two Player Games
        mTwoPlayerGames.add(new Game("Standard Simultaneous",
                "One target for each player, targets are unlinked",
                2, false, true,
                "2p_standard_sync"));
        mTwoPlayerGames.add(new Game("Standard Competitive",
                "One target for each player, targets are reset after either target is hit",
                2, true, false,
                "2p_standard_async"));
        mTwoPlayerGames.add(new Game("Standard Next",
                "One target for each player, next targets are indicated",
                2, true, true,
                "2p_standard_next_sync.py",
                "2p_standard_next_async.py"));
        // !! NOT FOUND !!
        mTwoPlayerGames.add(new Game("Standard Color",
                "All targets lit, each player must hit their color",
                2, true, false,
                "???"));
        // !! NOT FOUND !!
        mTwoPlayerGames.add(new Game("Double Tap Standard",
                "One target for each player that must be hit twice",
                2, true, true,
                "???"));
        mTwoPlayerGames.add(new Game("Two Targets Standard",
                "Two targets for each player, either must be hit",
                2, true, true,
                "2p_two_targets_sync.py",
                "2p_two_targets_async.py"));
        mTwoPlayerGames.add(new Game("Both Targets Standard",
                "Two targets for each player, both must be hit",
                2, true, true,
                "2p_both_targets_sync.py",
                "2p_both_targets_async.py"));
        mTwoPlayerGames.add(new Game("Three Goal Game First",
                "Each player must hit the opposing three targets once",
                2, false, false,
                "2p_three_goals_first.py"));
        mTwoPlayerGames.add(new Game("Three Goal Game Timed",
                "Each player must hit the opposing three targets",
                2, false, false,
                "2p_three_goals_max.py"));
        // !! NOT FOUND !!
        mTwoPlayerGames.add(new Game("1v1",
                "One target for each player that changes after a set interval",
                2, true, false,
                "???"));

        // Tests
        mTestGames.add(new Game("LED Test",
                "Lights up all LEDs one target at a time",
                0, false, false,
                "light_test.py"));
        // !! NOT FOUND !!
        mTestGames.add(new Game("Scoreboard Test",
                "Displays score on screen",
                0, false, false,
                "???"));
        // !! NOT FOUND !!
        mTestGames.add(new Game("Speaker Test",
                "Cycles through all game sounds",
                0, false, false,
                "1p_standard.py 1 0 2 60"));

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