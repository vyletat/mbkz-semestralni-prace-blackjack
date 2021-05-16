package cz.zcu.fav.kiv.mbkz.sp_blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cz.zcu.fav.kiv.mbkz.sp_blackjack.database.ScoreboardContract;
import cz.zcu.fav.kiv.mbkz.sp_blackjack.database.ScoreboardDbHelper;

public class ScoreboardActivity extends AppCompatActivity {
    String scoreboard;
    TextView twScoreboard;
    ScoreboardDbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        twScoreboard = (TextView) findViewById(R.id.scoreboard);

        // Database init
        dbHelper = new ScoreboardDbHelper(this);
        db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ScoreboardContract.ScoreEntry.COLUMN_NAME_PLAYER,
                ScoreboardContract.ScoreEntry.COLUMN_NAME_SCORE
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = ScoreboardContract.ScoreEntry.COLUMN_NAME_SCORE + " DESC";
        Cursor cursor = db.query(
                ScoreboardContract.ScoreEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder,          // The sort order
                "10"        // limit
        );

        /*
        List scoreList = new ArrayList<>();
        List playerList = new ArrayList<>();
         */
        StringBuilder builder = new StringBuilder();
        while(cursor.moveToNext()) {
            String playerName = cursor.getString(cursor.getColumnIndexOrThrow(ScoreboardContract.ScoreEntry.COLUMN_NAME_PLAYER));
            int score = cursor.getInt(cursor.getColumnIndexOrThrow(ScoreboardContract.ScoreEntry.COLUMN_NAME_SCORE));

            builder.append(playerName + ": " + score);
            builder.append(System.getProperty("line.separator"));
            scoreboard = builder.toString();

            /*
            playerList.add(playerName);
            scoreList.add(score);
             */

            Log.v("Database", "SELECT: Player name = " + playerName + ", Score = " + score);
        }
        cursor.close();

        twScoreboard.setText(scoreboard);
    }

    /**
     * Zobrazi dialog a popr. smaze vsechny zaznamy z databaze
     *
     * @param view
     */
    public void truncateDatabase(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.truncate_database_text)
                .setTitle(R.string.truncate_database_header)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.execSQL("DELETE FROM " + ScoreboardContract.ScoreEntry.TABLE_NAME);
                        Log.v("Database", "DATABASE WAS TRUNCATE");
                        Context context = getApplicationContext();
                        String toastText = getResources().getString(R.string.truncate_database_toast);

                        Toast toast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT);
                        toast.show();

                        finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}