package cz.zcu.fav.kiv.mbkz.sp_blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    ImageView dealer_card_first, dealer_card_second, dealer_card_third, dealer_card_fourth, dealer_card_fifth;
    ImageView hand_card_first, hand_card_second, hand_card_third, hand_card_fourth, hand_card_fifth;
    TextView bank, dealer_score, hand_score;
    Button hit, stand, place_bet, surrender;
    SeekBar bet;
    Game game;


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
        hit = (Button) findViewById(R.id.button_hit);
        stand = (Button) findViewById(R.id.button_stand);
        place_bet = (Button) findViewById(R.id.button_place_bet);
        surrender = (Button) findViewById(R.id.button_surrender);

        // Seekbar
        bet = (SeekBar) findViewById(R.id.seekBar_bet);

        initGame();
        //hit.setEnabled(false);
        //stand.setEnabled(false);
        this.reset();
    }

    private void initGame() {
        // Todo: Sebrat to z nastaveni
        int bankSum = 1000;

        this.game = new Game(bankSum, 52);
        bet.setMax(bankSum);

        double doubleProgress = (double) bankSum * 0.5;
        int progress = (int) doubleProgress;

        bet.setProgress(progress);
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
        this.nextDealer();
        this.hit.performClick();
    }

    public void bet() {

        hit.setEnabled(true);
        stand.setEnabled(true);
    }

    public void hit(View view) {
        nextHand();

        if (this.game.getHandScore() > this.game.getGOAL()) {
            loseDialog();
        }
        else if (this.game.getHandScore() == this.game.getGOAL()) {
            winDialog();
        }
    }

    public void stand(View view) {
        final int MAX_CARD_SCORE = 10;

        if (this.game.getDealerScore() + MAX_CARD_SCORE <= game.getGOAL()) {
            nextDealer();
            this.stand.performClick();

        } else {
            int nextCard = this.probabilityNextCard();

            if (this.game.getDealerScore() + nextCard <= game.getGOAL()) {
                // HRAJE
                nextDealer();

                this.stand.performClick();
            } else {
                // KONCI
                // Porovnat score
                if (this.game.getDealerScore() == this.game.getGOAL()) {
                    // LOSE
                    loseDialog();
                }
                else if (this.game.getDealerScore() > this.game.getGOAL()) {
                    // WIN
                    winDialog();
                } else {
                    int dealerDiff = this.game.getGOAL() - this.game.getDealerScore();
                    int handDiff = this.game.getGOAL() - this.game.getHandScore();

                    if (dealerDiff == handDiff) {
                        // Plichta
                    }
                    else if (dealerDiff < handDiff) {
                        // LOSE
                        loseDialog();
                    } else {
                        // WIN
                        winDialog();
                    }
                }
            }
        }
    }

    public void nextHand() {
        this.game.next();
        Log.v("Hit", "Hand round: " + this.game.getHandRound());
        Log.v("Hit", "Hand score: " + this.game.getHandScore());

        int index = this.game.getHandRound();
        Card handCard = this.game.getHandsFive().get(index);
        int handCardImage = getResources().getIdentifier(handCard.resource, "drawable", getPackageName());
        hand_score.setText("" + this.game.getHandScore());

        switch (index) {
            case 0:
                hand_card_fifth.setImageResource(handCardImage);
                break;
            case 1:
                hand_card_fourth.setImageResource(handCardImage);
                break;
            case 2:
                hand_card_third.setImageResource(handCardImage);
                break;
            case 3:
                hand_card_second.setImageResource(handCardImage);
                break;
            case 4:
                hand_card_first.setImageResource(handCardImage);
                break;
            default:
                hand_card_fifth.setImageResource(handCardImage);
                break;
        }
    }

    public void nextDealer() {
        game.nextDealer();
        int index = this.game.getDealerRound();
        Card dealerCard = this.game.getDealersFive().get(index);
        int dealerCardImage = getResources().getIdentifier(dealerCard.resource, "drawable", getPackageName());

        switch (index) {
            case 0:
                dealer_card_fifth.setImageResource(dealerCardImage);
                break;
            case 1:
                dealer_card_fourth.setImageResource(dealerCardImage);
                break;
            case 2:
                dealer_card_third.setImageResource(dealerCardImage);
                break;
            case 3:
                dealer_card_second.setImageResource(dealerCardImage);
                break;
            case 4:
                dealer_card_first.setImageResource(dealerCardImage);
                break;
            default:
                dealer_card_fifth.setImageResource(dealerCardImage);
                break;
        }

        dealer_score.setText("" + this.game.getDealerScore());
    }

    private void winDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.win_header)
                .setMessage(R.string.win_text)
                .setPositiveButton(R.string.next_round, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        reset();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { }
                })
                .setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void loseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.lose_text)
                .setTitle(R.string.lose_header).setPositiveButton(R.string.next_round, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            reset();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                })
                .setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private int probabilityNextCard() {
        double random = Math.random();
        final double CARDS = 52.0;      // cards total
        final double NUMBER = 4.0;      // cards from one color
        double oneNumber = NUMBER / CARDS;
        int nextCard = 0;

        // probabilities
        double one = oneNumber,
                two = 2 * oneNumber,
                three = 3 * oneNumber,
                four = 4 * oneNumber,
                five = 5 * oneNumber,
                six = 6 * oneNumber,
                seven = 7 * oneNumber,
                eight = 8 * oneNumber,
                nine = 9 * oneNumber,
                ten = (4 * NUMBER) / CARDS;

        if (random <= one) {
            nextCard = 1;
        } else {
            if (random <= two) {
                nextCard = 2;
            } else {
                if (random <= three) {
                    nextCard = 3;
                } else {
                    if (random <= four) {
                        nextCard = 4;
                    } else {
                        if (random <= five) {
                            nextCard = 5;
                        } else {
                            if (random <= six) {
                                nextCard = 6;
                            } else {
                                if (random <= seven) {
                                    nextCard = 7;
                                } else {
                                    if (random <= eight) {
                                        nextCard = 8;
                                    } else {
                                        if (random <= nine) {
                                            nextCard = 9;
                                        } else {
                                            nextCard = 10;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return nextCard;
    }
}