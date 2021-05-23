package cz.zcu.fav.kiv.mbkz.sp_blackjack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import cz.zcu.fav.kiv.mbkz.sp_blackjack.database.ScoreboardContract;
import cz.zcu.fav.kiv.mbkz.sp_blackjack.database.ScoreboardDbHelper;
import cz.zcu.fav.kiv.mbkz.sp_blackjack.game.Card;
import cz.zcu.fav.kiv.mbkz.sp_blackjack.game.Game;

public class GameActivity extends AppCompatActivity {
    ImageView dealer_card_first, dealer_card_second, dealer_card_third, dealer_card_fourth, dealer_card_fifth;
    ImageView hand_card_first, hand_card_second, hand_card_third, hand_card_fourth, hand_card_fifth;
    TextView bank, bet_amount, dealer_score, hand_score;
    Button hit, stand, place_bet, surrender;
    SeekBar bet;
    Game game;
    MediaPlayer mpBet, mpNewRound, mpWin, mpLose, mpDraw, mpHit, mpGameOver;
    SQLiteDatabase db;
    String player_name;
    List<ImageView> dealerCards, handCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Hide Action Appbar
        getSupportActionBar().hide();

        // Orientation settings
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean orientation = prefs.getBoolean("switch_preference_game_landscape", false);
        Log.v("Settings", "Game landscape = " + orientation.toString());
        if (orientation) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        // Player name
        this.player_name = prefs.getString("edit_text_preference_player_name", "Unknown_player");
        Log.v("Settings", "Player name = " + this.player_name);

        //Database
        ScoreboardDbHelper dbHelper = new ScoreboardDbHelper(this);
        // Gets the data repository in write mode
        this.db = dbHelper.getWritableDatabase();

        // Dealer cards
        dealer_card_first = (ImageView) findViewById(R.id.imageView);
        dealer_card_second = (ImageView) findViewById(R.id.imageView2);
        dealer_card_third = (ImageView) findViewById(R.id.imageView3);
        dealer_card_fourth = (ImageView) findViewById(R.id.imageView4);
        dealer_card_fifth = (ImageView) findViewById(R.id.imageView5);
        ImageView[] arrayDealerCards = {dealer_card_first, dealer_card_second, dealer_card_third, dealer_card_fourth, dealer_card_fifth};
        dealerCards = Arrays.asList(arrayDealerCards);

        // Hand cards
        hand_card_first = (ImageView) findViewById(R.id.imageView6);
        hand_card_second = (ImageView) findViewById(R.id.imageView7);
        hand_card_third = (ImageView) findViewById(R.id.imageView8);
        hand_card_fourth = (ImageView) findViewById(R.id.imageView9);
        hand_card_fifth = (ImageView) findViewById(R.id.imageView10);
        ImageView[] arrayHandCards = {hand_card_first, hand_card_second, hand_card_third, hand_card_fourth, hand_card_fifth};
        handCards = Arrays.asList(arrayHandCards);

        // Score and money
        bank = (TextView) findViewById(R.id.textView_bank);
        bet_amount = (TextView) findViewById(R.id.textView_bet_amount);
        dealer_score = (TextView) findViewById(R.id.textView_dealer_score);
        hand_score = (TextView) findViewById(R.id.textView_hand_score);

        // Buttons
        hit = (Button) findViewById(R.id.button_hit);
        stand = (Button) findViewById(R.id.button_stand);
        place_bet = (Button) findViewById(R.id.button_place_bet);
        surrender = (Button) findViewById(R.id.button_surrender);

        // Seekbar
        bet = (SeekBar) findViewById(R.id.seekBar_bet);
        bet.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // set step
                progress = recalculateBetValue(progress);
                bet_amount.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Media players - sounds
        mpBet = MediaPlayer.create(this, R.raw.bet);
        mpNewRound = MediaPlayer.create(this, R.raw.new_round);
        mpWin = MediaPlayer.create(this, R.raw.win);
        mpLose = MediaPlayer.create(this, R.raw.lose);
        mpGameOver = MediaPlayer.create(this, R.raw.game_over);
        mpHit = MediaPlayer.create(this, R.raw.hit);

        initGame();
        hit.setEnabled(false);
        stand.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        surrender.performClick();
    }

    /**
     * Metoda pro spravne prepocitani hodnot v seek baru.
     *
     * @param progress Zvolena hodnota
     * @return Prepocitana zvolena hodnota.
     */
    private int recalculateBetValue(int progress) {
        progress = progress / 100;
        progress = progress * 100;
        return progress;
    }

    /**
     * Inicializace hry.
     */
    private void initGame() {
        int bankSum = 10000;                        // NASTAVENI SUMY
        bank.setText("" + bankSum);

        game = new Game(bankSum, 52);
        bet.setMax(bankSum);
        double doubleProgress = (double) bankSum * 0.5;
        int progress = (int) doubleProgress;
        bet.setProgress(progress);

        resetCardBack();
    }

    /**
     * Nastaveni sazky pro hru.
     *
     * @param view
     */
    public void bet(View view) {
        int betAmount = recalculateBetValue(bet.getProgress());
        game.setBet(betAmount);

        hit.setEnabled(true);
        stand.setEnabled(true);

        reset();
        mpBet.start();

        bet.setEnabled(false);
        place_bet.setEnabled(false);
    }

    /**
     * Nastaveni rubu karet.
     */
    private void resetCardBack() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String cardBackColor = prefs.getString("list_preference_card_back_color", "blue");          // Defaultni barva karet
        String cardBackName = "card_back_" + cardBackColor;
        Log.v("Settings", "Card back color = " + this.player_name);

        int cardBack = getResources().getIdentifier(cardBackName, "drawable", getPackageName());

        dealerCards.forEach(cardImageView -> {
            cardImageView.setImageResource(cardBack);
        });
        handCards.forEach(cardImageView -> {
            cardImageView.setImageResource(cardBack);
        });
    }

    /**
     * Metoda pro resetovani herniho planu a overeni, jestli hrac ma na dalsi sazku.
     */
    private void reset() {
        resetCardBack();

        // Overovani jestli ma na dalsi sazku
        if (game.getBankSum() >= game.getBet()) {
            // OK
            this.game.nextRound();
            bank.setText("" + game.getBankSum());
            this.nextDealer();
            this.hit.performClick();
            this.hit.performClick();
        } else {
            // GAME END - Prilis velka sazka
            loseGameDialog();
            mpGameOver.start();

            // Pridani score do databaze
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(ScoreboardContract.ScoreEntry.COLUMN_NAME_PLAYER, player_name);
            values.put(ScoreboardContract.ScoreEntry.COLUMN_NAME_SCORE, game.getScore());
            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(ScoreboardContract.ScoreEntry.TABLE_NAME, null, values);
            Log.v("Game", "PLAYER SCORE: Bet = " + game.getBet() + ", Player win counter = " + game.getWinCount() + ", [(" + game.getBet() + " / 1000) * " + game.getWinCount() + " = " + game.getScore() + "]");
            Log.v("Database", "INSERT: Player name = " + player_name + ", Score = " + game.getScore());
        }
    }

    /**
     * Dalsi tah hrace.
     *
     * @param view
     */
    public void hit(View view) {
        Log.v("Game", "PLAYER HIT");

        int index = this.game.getHandRound();
        if (index < 4) {
            nextHand();
            mpHit.start();

            if (this.game.getHandScore() > this.game.getGOAL()) {
                loseDialog();
                game.loseRound();
                mpLose.start();
            } else if (this.game.getHandScore() == this.game.getGOAL()) {
                winDialog();
                game.winRound();
                mpWin.start();
            } else {
                if (game.getHandRound() == 4) {
                    stand.performClick();
                }
            }
        } else {
            stand.performClick();
        }
    }

    /**
     * Stani hrace, tah dealera a vyhodnoceni kola.
     *
     * @param view
     */
    public void stand(View view) {
        Log.v("Game", "PLAYER STANDS");

        final int MAX_CARD_SCORE = 10;

        int index = this.game.getDealerRound();
        if (index < 4) {
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
                    scoreEvaluated();
                }
            }
        } else {
            scoreEvaluated();
        }
    }

    /**
     *
     */
    public void scoreEvaluated() {
        if (this.game.getDealerScore() == this.game.getGOAL()) {
            // LOSE
            loseDialog();
            game.loseRound();
            mpLose.start();
        } else if (this.game.getDealerScore() > this.game.getGOAL()) {
            // WIN
            winDialog();
            game.winRound();
            mpWin.start();
        } else {
            int dealerDiff = this.game.getGOAL() - this.game.getDealerScore();
            int handDiff = this.game.getGOAL() - this.game.getHandScore();

            if (dealerDiff == handDiff) {
                // DRAW
                drawDialog();
                game.drawRound();
            } else if (dealerDiff < handDiff) {
                // LOSE
                loseDialog();
                game.loseRound();
                mpLose.start();
            } else {
                // WIN
                winDialog();
                game.winRound();
                mpWin.start();
            }
        }
    }

    /**
     * Dalsi tah hrace.
     */
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
                // Ukoncit kolo
                stand.performClick();
                break;
        }
    }

    /**
     * Dalsi tah dealera.
     */
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
                // Vyhodnot
                scoreEvaluated();
                break;
        }

        dealer_score.setText("" + this.game.getDealerScore());
    }

    /**
     * Dialog pro vyhru.
     */
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
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Dialog pro prohru.
     */
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
                        finish();
                    }
                })
                .setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Dialog pro remizu.
     */
    private void drawDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.draw_text)
                .setTitle(R.string.draw_header).setPositiveButton(R.string.next_round, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                reset();
            }
        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Dialog pro konec hry.
     */
    private void loseGameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.lose_game_text)
                .setTitle(R.string.lose_game_header)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Metoda, kterou vyuziva dealer, která rozhoduje na zaklade pravdepodobnosti, jestli ma tahnout dal nebo ne.
     *
     * @return Hodnota karty.
     */
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

    /**
     * Pokud se hrac rozhodne ukoncit hru.
     *
     * @param view
     */
    public void surrender(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.surrender_text)
                .setTitle(R.string.surrender_header)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Pridani score do databaze
                        // Create a new map of values, where column names are the keys
                        ContentValues values = new ContentValues();
                        values.put(ScoreboardContract.ScoreEntry.COLUMN_NAME_PLAYER, player_name);
                        values.put(ScoreboardContract.ScoreEntry.COLUMN_NAME_SCORE, game.getScore());
                        // Insert the new row, returning the primary key value of the new row
                        long newRowId = db.insert(ScoreboardContract.ScoreEntry.TABLE_NAME, null, values);

                        Log.v("Game", "PLAYER SURRENDER");
                        Log.v("Game", "PLAYER SCORE: Bet = " + game.getBet() + ", Player win counter = " + game.getWinCount() + ", [(" + game.getBet() + " / 1000) * " + game.getWinCount() + " = " + game.getScore() + "]");
                        Log.v("Database", "INSERT: Player name = " + player_name + ", Score = " + game.getScore());

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