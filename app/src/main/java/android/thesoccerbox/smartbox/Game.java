package android.thesoccerbox.smartbox;

import java.util.List;
import java.util.UUID;

public class Game {
    /**
     * Class that holds all the data for games which are each associated with a program on the pi
     */

    private final UUID mId;
    private final String mTitle;
    private final String mDescription;
    private final String mCodePath;
    private final boolean mSynchOption;
    private final boolean mAsynchOption;

    public Game(String title, String description, boolean synch, boolean asynch, String code_path) {
        mId = UUID.randomUUID();
        mTitle = title;
        mDescription = description;
        mCodePath = code_path;
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
    public String getCodePath() {
        return mCodePath;
    }
    public boolean getSynch() { return mSynchOption; }
    public boolean getAsynch() { return mAsynchOption; }
}
