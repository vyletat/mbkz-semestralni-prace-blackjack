package cz.zcu.fav.kiv.mbkz.sp_blackjack;

import android.util.Log;

import java.util.List;

public class Game {
    private final int GOAL = 21;

    // Money
    private int bankSum, bet;

    // Cards
    private CardDeck dealerDeck, handDeck;
    List<Card> dealersFive, handsFive;

    public int getDealerRound() {
        return dealerRound;
    }

    // Counters
    private int handRound, dealerRound;                   // current round 0-4

    // Score
    private int dealerScore, handScore = 0;


    public Game(int bankSum, int cardsInDeck) {
        this.bankSum = bankSum;
        this.handRound = 0;
        this.dealerDeck = new CardDeck();
        this.handDeck = new CardDeck();
    }

    /**
     * FOR HAND
     */
    public void next() {
        Log.v("Next", "Round index: " + this.handRound);

        // this.dealerScore += this.dealersFive.get(this.getCounterRound()).value;
        this.handScore += this.handsFive.get(this.getHandRound()).value;
        this.handRound++;
    }

    public void nextDealer() {
        this.dealerRound++;
        this.dealerScore += this.dealersFive.get(this.getDealerRound()).value;
    }

    private void stop() {

    }

    public void nextRound() {
        // init zero
        this.dealerRound = 0;
        this.handRound = 0;
        this.dealerScore = 0;
        this.handScore = 0;

        // get next five card from deck
        this.dealersFive = this.dealerDeck.getNextFive();
        this.handsFive = this.handDeck.getNextFive();

        // add first card score do total score of each site
        this.dealerScore += this.dealersFive.get(this.getHandRound()).value;
        this.handScore += this.handsFive.get(this.getHandRound()).value;
    }

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

    public CardDeck getDealerDeck() {
        return dealerDeck;
    }

    public CardDeck getHandDeck() {
        return handDeck;
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
}
