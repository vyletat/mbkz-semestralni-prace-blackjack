package cz.zcu.fav.kiv.mbkz.sp_blackjack;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cz.zcu.fav.kiv.mbkz.sp_blackjack.enums.Rank;
import cz.zcu.fav.kiv.mbkz.sp_blackjack.enums.Suit;

public class Game {
    private final int GOAL = 21;

    private int bankSum;
    private int counterRound;                   // current round 0-4
    private CardDeck dealerDeck;
    private CardDeck handDeck;
    private int dealerScore = 0;
    private int handScore = 0;
    List<Card> dealersFive, handsFive;

    public Game(int bankSum, int cardsInDeck) {
        this.bankSum = bankSum;
        this.counterRound = 0;
        this.dealerDeck = new CardDeck();
        this.handDeck = new CardDeck();
    }

    public void next() {
        Log.v("Next", "Round index: " + this.counterRound);

        this.dealerScore += this.dealersFive.get(this.getCounterRound()).value;
        this.handScore += this.handsFive.get(this.getCounterRound()).value;

        this.counterRound++;
    }

    private void stop() {

    }

    public void nextRound() {
        this.counterRound = 0;
        this.dealerScore = 0;
        this.handScore = 0;

        this.dealersFive = this.dealerDeck.getNextFive();
        this.handsFive = this.handDeck.getNextFive();

        this.dealerScore += this.dealersFive.get(this.getCounterRound()).value;
        this.handScore += this.handsFive.get(this.getCounterRound()).value;
    }

    public int getBankSum() {
        return bankSum;
    }

    public int getCounterRound() {
        return counterRound;
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
