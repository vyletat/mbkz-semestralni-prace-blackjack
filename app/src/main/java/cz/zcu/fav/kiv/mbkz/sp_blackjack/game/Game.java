package cz.zcu.fav.kiv.mbkz.sp_blackjack.game;

import android.util.Log;

import java.util.List;

public class Game {
    // Round WIN score
    private final int GOAL = 21;

    // Money
    private int bankSum;
    private int bet;

    // Cards
    private CardDeck dealerDeck, handDeck;
    List<Card> dealersFive, handsFive;

    // Counters
    private int handRound, dealerRound;
    private int win_count;

    // Score
    private int dealerScore = 0, handScore = 0;

    // Constructor
    public Game(int bankSum, int cardsInDeck) {
        this.bankSum = bankSum;
        this.dealerDeck = new CardDeck();
        this.handDeck = new CardDeck();
        this.win_count = 0;
    }

    /**
     * FOR HAND
     */
    public void next() {
        this.handRound++;
        Log.v("Hand", "Round index: " + this.handRound);
        this.handScore += this.handsFive.get(this.getHandRound()).value;
        Log.v("Hand", "Hand Score: " + this.handScore);

    }

    public void nextDealer() {
        this.dealerRound++;
        Log.v("Dealer", "Dealer index: " + this.dealerRound);
        this.dealerScore += this.dealersFive.get(this.getDealerRound()).value;
        Log.v("Dealer", "Dealer Score: " + this.dealerScore);
    }

    private void stop() {

    }

    public void nextRound() {
        // init zero
        this.dealerRound = -1;
        this.handRound = -1;
        this.dealerScore = 0;
        this.handScore = 0;

        // get next five card from deck
        this.dealersFive = this.dealerDeck.getNextFive();
        this.handsFive = this.handDeck.getNextFive();

        // add first card score do total score of each site
        // this.dealerScore += this.dealersFive.get(this.getDealerRound()).value;
        // this.handScore += this.handsFive.get(this.getHandRound()).value;
    }

    public void winRound() {
        bankSum += bet;
        win_count++;
    }

    public void loseRound() {
        bankSum -= bet;
    }

    public void drawRound() { }

    public int getScore() {
        // Vzorec: sazka/1000 * počet vítěžství
        return (bet / 1000) * win_count;
    }

    /*
    ------ GETTERS ------
     */

    public int getBankSum() {
        return bankSum;
    }

    public int getHandRound() {
        return handRound;
    }

    public int getDealerScore() {
        return dealerScore;
    }

    public int getHandScore() {
        return handScore;
    }

    public List<Card> getDealersFive() {
        return dealersFive;
    }

    public List<Card> getHandsFive() {
        return handsFive;
    }

    public int getGOAL() {
        return GOAL;
    }

    public int getBet() {
        return bet;
    }

    public int getDealerRound() {
        return dealerRound;
    }

    /*
    ------ SETTERS ------
     */

    public void setBet(int bet) {
        this.bet = bet;
    }


}
