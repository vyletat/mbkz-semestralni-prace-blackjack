package cz.zcu.fav.kiv.mbkz.sp_blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        TextView rules = (TextView) findViewById(R.id.rules_content);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            rules.setText(Html.fromHtml(getString(R.string.rules_content), Html.FROM_HTML_MODE_COMPACT));
        } else {
            rules.setText(Html.fromHtml(getString(R.string.rules_content)));
        }
    }
}