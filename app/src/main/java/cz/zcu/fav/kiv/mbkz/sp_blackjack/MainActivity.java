package cz.zcu.fav.kiv.mbkz.sp_blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Method for change activity to GameActivity.
     * @see GameActivity
     *
     * @param view
     */
    public void play(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void settings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void rules(View view) {
        Intent intent = new Intent(this, RulesActivity.class);
        startActivity(intent);
    }
}