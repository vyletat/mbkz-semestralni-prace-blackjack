package cz.zcu.fav.kiv.mbkz.sp_blackjack;

import cz.zcu.fav.kiv.mbkz.sp_blackjack.enums.Rank;
import cz.zcu.fav.kiv.mbkz.sp_blackjack.enums.Suit;

public class Card {
    public Rank rank;
    public Suit suit;
    public int value;
    public String resource;

    public Card(Rank rank, Suit suit, int value, String resource) {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
        this.resource = resource;
    }

}
