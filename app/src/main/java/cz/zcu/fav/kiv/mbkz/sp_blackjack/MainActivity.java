package cz.zcu.fav.kiv.mbkz.sp_blackjack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


/**
 * @author Bc. Tomas Vyleta
 * @version 1.0
 */

/**
 * Hlavni aktivita pro menu aplikace.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean nightMode = prefs.getBoolean("switch_preference_dark_mode", false);
        Log.v("Settings", "Night mode = " + nightMode.toString());

        // Nigh mode
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_main);
    }

    /**
     * Metoda pro zmenu aktivity na GameActivity.
     * @see GameActivity
     *
     * @param view
     */
    public void play(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    /**
     * Metoda pro zmenu aktivity na SettingsActivity.
     * @see SettingsActivity
     *
     * @param view
     */
    public void settings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    /**
     * Metoda pro zmenu aktivity na RulesActivity.
     * @see RulesActivity
     *
     * @param view
     */
    public void rules(View view) {
        Intent intent = new Intent(this, RulesActivity.class);
        startActivity(intent);
    }

    /**
     * Metoda pro zmenu aktivity na ScoreboardActivity.
     * @see ScoreboardActivity
     *
     * @param view
     */
    public void scoreboard(View view) {
        Intent intent = new Intent(this, ScoreboardActivity.class);
        startActivity(intent);
    }
}