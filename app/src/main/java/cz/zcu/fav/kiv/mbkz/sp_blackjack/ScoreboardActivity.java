package cz.zcu.fav.kiv.mbkz.sp_blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.zcu.fav.kiv.mbkz.sp_blackjack.database.FeedReaderContract;
import cz.zcu.fav.kiv.mbkz.sp_blackjack.database.FeedReaderDbHelper;

public class ScoreboardActivity extends AppCompatActivity {
    String scoreboard;
    TextView twScoreboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        twScoreboard = (TextView) findViewById(R.id.scoreboard);

        // Database
        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedReaderContract.FeedEntry.COLUMN_NAME_PLAYER,
                FeedReaderContract.FeedEntry.COLUMN_NAME_SCORE
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedReaderContract.FeedEntry.COLUMN_NAME_SCORE + " DESC";

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder,          // The sort order
                "10"        // limit
        );

        List scoreList = new ArrayList<>();
        List playerList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        while(cursor.moveToNext()) {
            String playerName = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_PLAYER));
            int score = cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_SCORE));

            builder.append(playerName + ": " + score);
            builder.append(System.getProperty("line.separator"));
            scoreboard = builder.toString();

            playerList.add(playerName);
            scoreList.add(score);

            Log.v("Database", "SELECT: Player name = " + playerName + ", Score = " + score);
        }
        cursor.close();

        twScoreboard.setText(scoreboard);
    }
}