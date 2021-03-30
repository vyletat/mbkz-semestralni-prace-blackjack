package cz.zcu.fav.kiv.mbkz.sp_blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GameActivity extends AppCompatActivity {
    ImageView dealer_card_first, dealer_card_second, dealer_card_third, dealer_card_fourth, dealer_card_fifth;
    ImageView hand_card_first, hand_card_second, hand_card_third, hand_card_fourth, hand_card_fifth;
    TextView bank, dealer_score, hand_score;
    Game game;
    Button hit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Dealer cards
        dealer_card_first = (ImageView) findViewById(R.id.imageView);
        dealer_card_second = (ImageView) findViewById(R.id.imageView2);
        dealer_card_third = (ImageView) findViewById(R.id.imageView3);
        dealer_card_fourth = (ImageView) findViewById(R.id.imageView4);
        dealer_card_fifth = (ImageView) findViewById(R.id.imageView5);

        // Hand cards
        hand_card_first = (ImageView) findViewById(R.id.imageView6);
        hand_card_second = (ImageView) findViewById(R.id.imageView7);
        hand_card_third = (ImageView) findViewById(R.id.imageView8);
        hand_card_fourth = (ImageView) findViewById(R.id.imageView9);
        hand_card_fifth = (ImageView) findViewById(R.id.imageView10);

        // Score and money
        bank = (TextView) findViewById(R.id.textView_bank);
        dealer_score = (TextView) findViewById(R.id.textView_dealer_score);
        hand_score = (TextView) findViewById(R.id.textView_hand_score);

        // Buttons
        this.hit = (Button) findViewById(R.id.button_hit);

        this.game = new Game(1000, 52);
        this.reset();
    }

    public void hit(View view) {
        int index = this.game.getCounterRound();

        Card dealerCard = this.game.getDealersFive().get(index);
        Card handCard = this.game.getHandsFive().get(index);

        int dealerCardImage = getResources().getIdentifier(dealerCard.resource, "drawable", getPackageName());
        int handCardImage = getResources().getIdentifier(handCard.resource, "drawable", getPackageName());

        switch (index) {
            case 0:
                dealer_card_fifth.setImageResource(dealerCardImage);
                hand_card_fifth.setImageResource(handCardImage);
                Log.v("Next", "fifth");
                break;
            case 1:
                dealer_card_fourth.setImageResource(dealerCardImage);
                hand_card_fourth.setImageResource(handCardImage);
                Log.v("Next", "fourth");
                break;
            case 2:
                dealer_card_third.setImageResource(dealerCardImage);
                hand_card_third.setImageResource(handCardImage);
                Log.v("Next", "third");
                break;
            case 3:
                dealer_card_second.setImageResource(dealerCardImage);
                hand_card_second.setImageResource(handCardImage);
                Log.v("Next", "second");
                break;
            case 4:
                dealer_card_first.setImageResource(dealerCardImage);
                hand_card_first.setImageResource(handCardImage);
                Log.v("Next", "first");
                break;
            default:
                dealer_card_fifth.setImageResource(dealerCardImage);
                hand_card_fifth.setImageResource(handCardImage);
                Log.v("Next", "fifth");
                break;
        }

        dealer_score.setText("" + this.game.getDealerScore());
        hand_score.setText("" + this.game.getHandScore());

        if (this.game.getHandScore() < this.game.getGOAL()) {
            Toast.makeText(this, "Malo!", Toast.LENGTH_SHORT).show();
            this.game.next();
        }
        else if (this.game.getHandScore() == this.game.getGOAL()) {
            // Toast.makeText(this, "Noice!", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.win_header)
                    .setTitle(R.string.win_text);
            builder.setPositiveButton(R.string.next_round, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    reset();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            // Toast.makeText(this, "Goofy!", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.lose_header)
                    .setTitle(R.string.lose_text);
            builder.setPositiveButton(R.string.next_round, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    reset();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }



    }

    private void initGame() {
        // Todo: Sebrat to z nastaveni
    }

    private void reset() {

        int cardBack = getResources().getIdentifier("card_back_blue", "drawable", getPackageName());

        dealer_card_first.setImageResource(cardBack);
        dealer_card_second.setImageResource(cardBack);
        dealer_card_third.setImageResource(cardBack);
        dealer_card_fourth.setImageResource(cardBack);
        dealer_card_fifth.setImageResource(cardBack);

        hand_card_first.setImageResource(cardBack);
        hand_card_second.setImageResource(cardBack);
        hand_card_third.setImageResource(cardBack);
        hand_card_fourth.setImageResource(cardBack);
        hand_card_fifth.setImageResource(cardBack);

        this.game.nextRound();
        this.hit.performClick();
    }
}