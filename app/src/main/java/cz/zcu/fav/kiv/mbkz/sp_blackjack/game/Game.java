package cz.zcu.fav.kiv.mbkz.sp_blackjack.game;

import android.util.Log;

import java.util.List;

/**
 * Trida pro chod jedne instandce hry.
 */
public class Game {
    // Round WIN score
    private final int GOAL = 21;                //Goal hry
    // Money
    private int bankSum;
    private int bet;
    // Cards
    private CardDeck dealerDeck, handDeck;
    List<Card> dealersFive, handsFive;
    // Counters
    private int handRound, dealerRound;
    private int winCount, gameRounds;
    // Score
    private int dealerScore = 0, handScore = 0;

    // Constructor
    public Game(int bankSum, int cardsInDeck) {
        this.bankSum = bankSum;
        this.dealerDeck = new CardDeck();
        this.handDeck = new CardDeck();
        this.winCount = 0;
        this.gameRounds = 0;
        Log.v("Game", "GAME INIT: Bank = " + this.bankSum);
    }

    /**
     * Dalsi tah pro HRACE.
     */
    public void next() {
        this.handRound++;
        this.handScore += this.handsFive.get(this.getHandRound()).value;
        Log.v("Game", "HAND " + this.gameRounds + "-" + this.handRound + ": Hand Score = " + this.handScore);
    }

    /**
     * Dalsi tah pro DEALERA.
     */
    public void nextDealer() {
        this.dealerRound++;
        this.dealerScore += this.dealersFive.get(this.getDealerRound()).value;
        Log.v("Game", "DEALER " + this.gameRounds + "-" + this.dealerRound + ": Hand Score = " + this.dealerScore);
    }

    /**
     * Zacatek noveho kola.
     */
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

        this.gameRounds++;
    }

    /**
     * Hrac vyhral kolo.
     */
    public void winRound() {
        bankSum += bet;
        winCount++;
        Log.v("Game", "PLAYER WIN ROUND: Total win count = " + this.winCount);
    }

    /**
     * Hrac prohral kolo.
     */
    public void loseRound() {
        bankSum -= bet;
        Log.v("Game", "DEALER WIN ROUND");
    }

    /**
     * Remiza
     */
    public void drawRound() {
        Log.v("Game", "IT IS A DRAW");
    }

    /**
     * Vypocet vysledneho score do databaze.
     *
     * @return      Vypoctene score.
     */
    public int getScore() {
        // Vzorec: sazka/1000 * počet vítěžství
        return (bet / 100) * winCount;
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

    public int getWinCount() {
        return winCount;
    }

    /*
    ------ SETTERS ------
     */

    public void setBet(int bet) {
        this.bet = bet;
        Log.v("Game", "GAME BET: Bet = " + this.bet);
    }


}
