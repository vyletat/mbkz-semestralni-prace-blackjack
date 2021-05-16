package cz.zcu.fav.kiv.mbkz.sp_blackjack.database;

import android.provider.BaseColumns;

public class ScoreboardContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ScoreboardContract() {}

    /* Inner class that defines the table contents */
    public static class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "score_entry";
        public static final String COLUMN_NAME_PLAYER = "player";
        public static final String COLUMN_NAME_SCORE = "score";
    }

    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ScoreEntry.TABLE_NAME + " (" +
                    ScoreEntry._ID + " INTEGER PRIMARY KEY," +
                    ScoreEntry.COLUMN_NAME_PLAYER + " TEXT," +
                    ScoreEntry.COLUMN_NAME_SCORE + " INTEGER)";

    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ScoreEntry.TABLE_NAME;

    static final String SQL_TRUNCATE_ENTRIES =
            "DELETE FROM " + ScoreEntry.TABLE_NAME;
}
