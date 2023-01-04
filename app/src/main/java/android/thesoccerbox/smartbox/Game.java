package android.thesoccerbox.smartbox;

import java.util.List;
import java.util.UUID;

public class Game {
    /**
     * Class that holds all the data for games which are each associated with a program on the pi
     */

    private static final String FILE_PREFIX = "sudo python3 rpi_ws281x/python/examples/soccer_box_final/";

    private final UUID mId;
    private final String mTitle;
    private final String mDescription;
    private final String mCodePath;
    private final String mAsyncCodePath;
    private final int mNumPlayers;
    private final boolean mSynchOption;
    private final boolean mAsynchOption;

    // Default Constructor
    public Game(String title, String description, int numPlayers, boolean synch, boolean asynch, String file_name) {
        mId = UUID.randomUUID();
        mTitle = title;
        mDescription = description;
        mNumPlayers = numPlayers;
        mCodePath = FILE_PREFIX + file_name;
        mAsyncCodePath = "";
        mSynchOption = synch;
        mAsynchOption = asynch;
    }

    // Alternate Constructor
    public Game(String title, String description, int numPlayers, boolean synch, boolean asynch, String file_name, String async_file_name) {
        mId = UUID.randomUUID();
        mTitle = title;
        mDescription = description;
        mNumPlayers = numPlayers;
        mCodePath = FILE_PREFIX + file_name;
        mAsyncCodePath = FILE_PREFIX + async_file_name;
        mSynchOption = synch;
        mAsynchOption = asynch;
    }

    public UUID getId() {
        return mId;
    }
    public String getTitle() {
        return mTitle;
    }
    public String getDescription() {
        return mDescription;
    }
    public int getNumPlayers() { return mNumPlayers; }
    public String getCodePath() {
        return mCodePath;
    }
    public String getAsyncCodePath() { return mAsyncCodePath; }
    public boolean getSynch() { return mSynchOption; }
    public boolean getAsynch() { return mAsynchOption; }
}
