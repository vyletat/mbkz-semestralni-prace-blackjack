package cz.zcu.fav.kiv.mbkz.sp_blackjack.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.zcu.fav.kiv.mbkz.sp_blackjack.enums.Rank;
import cz.zcu.fav.kiv.mbkz.sp_blackjack.enums.Suit;

public class CardDeck {
    public final int CARDS_IN_DECK = 52;
    public int index;
    public List<Card> deck;

    public CardDeck() {
        this.index = 0;
        this.deck = createDeck();
        this.shuffle();
    }

    /**
     * Vytvori vsechny karty a z nich balicek.
     *
     * @return  
     */
    private List<Card> createDeck() {
        List<Card> deck = new ArrayList<>(CARDS_IN_DECK);
        // ACE
        deck.add(new Card(Rank.ACE, Suit.CLUBS, 1, "ace_of_clubs"));
        deck.add(new Card(Rank.ACE, Suit.DIAMONDS, 1, "ace_of_diamonds"));
        deck.add(new Card(Rank.ACE, Suit.HEARTS, 1, "ace_of_hearts"));
        deck.add(new Card(Rank.ACE, Suit.SPADES, 1, "ace_of_spades2"));
        // TWO
        deck.add(new Card(Rank.TWO, Suit.CLUBS, 2, "two_of_clubs"));
        deck.add(new Card(Rank.TWO, Suit.DIAMONDS, 2, "two_of_diamonds"));
        deck.add(new Card(Rank.TWO, Suit.HEARTS, 2, "two_of_hearts"));
        deck.add(new Card(Rank.TWO, Suit.SPADES, 2, "two_of_spades"));
        // THREE
        deck.add(new Card(Rank.THREE, Suit.CLUBS, 3, "three_of_clubs"));
        deck.add(new Card(Rank.THREE, Suit.DIAMONDS, 3, "three_of_diamonds"));
        deck.add(new Card(Rank.THREE, Suit.HEARTS, 3, "three_of_hearts"));
        deck.add(new Card(Rank.THREE, Suit.SPADES, 3, "three_of_spades"));
        // FOUR
        deck.add(new Card(Rank.FOUR, Suit.CLUBS, 4, "four_of_clubs"));
        deck.add(new Card(Rank.FOUR, Suit.DIAMONDS, 4, "four_of_diamonds"));
        deck.add(new Card(Rank.FOUR, Suit.HEARTS, 4, "four_of_hearts"));
        deck.add(new Card(Rank.FOUR, Suit.SPADES, 4, "four_of_spades"));
        // FIVE
        deck.add(new Card(Rank.FIVE, Suit.CLUBS, 5, "five_of_clubs"));
        deck.add(new Card(Rank.FIVE, Suit.DIAMONDS, 5, "five_of_diamonds"));
        deck.add(new Card(Rank.FIVE, Suit.HEARTS, 5, "five_of_hearts"));
        deck.add(new Card(Rank.FIVE, Suit.SPADES, 5, "five_of_spades"));
        // SIX
        deck.add(new Card(Rank.SIX, Suit.CLUBS, 6, "six_of_clubs"));
        deck.add(new Card(Rank.SIX, Suit.DIAMONDS, 6, "six_of_diamonds"));
        deck.add(new Card(Rank.SIX, Suit.HEARTS, 6, "six_of_hearts"));
        deck.add(new Card(Rank.SIX, Suit.SPADES, 6, "six_of_spades"));
        // SEVEN
        deck.add(new Card(Rank.SEVEN, Suit.CLUBS, 7, "seven_of_clubs"));
        deck.add(new Card(Rank.SEVEN, Suit.DIAMONDS, 7, "seven_of_diamonds"));
        deck.add(new Card(Rank.SEVEN, Suit.HEARTS, 7, "seven_of_hearts"));
        deck.add(new Card(Rank.SEVEN, Suit.SPADES, 7, "seven_of_spades"));
        // EIGHT
        deck.add(new Card(Rank.EIGHT, Suit.CLUBS, 8, "eight_of_clubs"));
        deck.add(new Card(Rank.EIGHT, Suit.DIAMONDS, 8, "eight_of_diamonds"));
        deck.add(new Card(Rank.EIGHT, Suit.HEARTS, 8, "eight_of_hearts"));
        deck.add(new Card(Rank.EIGHT, Suit.SPADES, 8, "eight_of_spades"));
        // NINE
        deck.add(new Card(Rank.NINE, Suit.CLUBS, 9, "nine_of_clubs"));
        deck.add(new Card(Rank.NINE, Suit.DIAMONDS, 9, "nine_of_diamonds"));
        deck.add(new Card(Rank.NINE, Suit.HEARTS, 9, "nine_of_hearts"));
        deck.add(new Card(Rank.NINE, Suit.SPADES, 9, "nine_of_spades"));
        // TEN
        deck.add(new Card(Rank.TEN, Suit.CLUBS, 10, "ten_of_clubs"));
        deck.add(new Card(Rank.TEN, Suit.DIAMONDS, 10, "ten_of_diamonds"));
        deck.add(new Card(Rank.TEN, Suit.HEARTS, 10, "ten_of_hearts"));
        deck.add(new Card(Rank.TEN, Suit.SPADES, 10, "ten_of_spades"));
        // JACK
        deck.add(new Card(Rank.JACK, Suit.CLUBS, 10, "jack_of_clubs"));
        deck.add(new Card(Rank.JACK, Suit.DIAMONDS, 10, "jack_of_diamonds"));
        deck.add(new Card(Rank.JACK, Suit.HEARTS, 10, "jack_of_hearts"));
        deck.add(new Card(Rank.JACK, Suit.SPADES, 10, "jack_of_spades"));
        // QUEEN
        deck.add(new Card(Rank.QUEEN, Suit.CLUBS, 10, "queen_of_clubs"));
        deck.add(new Card(Rank.QUEEN, Suit.DIAMONDS, 10, "queen_of_diamonds"));
        deck.add(new Card(Rank.QUEEN, Suit.HEARTS, 10, "queen_of_hearts"));
        deck.add(new Card(Rank.QUEEN, Suit.SPADES, 10, "queen_of_spades"));
        // KING
        deck.add(new Card(Rank.KING, Suit.CLUBS, 10, "king_of_clubs"));
        deck.add(new Card(Rank.KING, Suit.DIAMONDS, 10, "king_of_diamonds"));
        deck.add(new Card(Rank.KING, Suit.HEARTS, 10, "king_of_hearts"));
        deck.add(new Card(Rank.KING, Suit.SPADES, 10, "king_of_spades"));

        return deck;
    }

    public void shuffle() {
        Collections.shuffle(this.deck);
        Log.v("Game", "--- CARD SHUFFLE ---");
    }

    public List<Card> getNextFive() {
        List<Card> fiveCards = new ArrayList<>(5);
        int oldIndex = this.index;
        this.index += 5;
        // uz nejsou volne karty
        if (CARDS_IN_DECK - this.index < 0) {
            this.shuffle();
            this.index = 0;
            oldIndex = this.index;
            this.index += 5;
            for (int i = oldIndex; i < this.index; i++) {
                fiveCards.add(this.deck.get(i));
            }
        } else {
            for (int i = oldIndex; i < this.index; i++) {
                fiveCards.add(this.deck.get(i));
            }
        }
        return fiveCards;
    }
}
