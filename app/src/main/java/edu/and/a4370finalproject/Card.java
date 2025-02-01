package edu.and.a4370finalproject;

import android.graphics.Bitmap;

public class Card implements Comparable<Card> {
    private Suit suit;
    private Rank rank;
    private Bitmap sprite;
    public Card(Suit suit, Rank rank, Bitmap sprite) {
        this.suit = suit;
        this.rank = rank;
        this.sprite = sprite;
    }
    public Suit getSuit() {
        return suit;
    }
    public Rank getRank() {
        return rank;
    }
    public Bitmap getSprite() {
        return sprite;
    }
    @Override
    public int compareTo(Card other) {

        return this.rank.compareTo(other.rank);
    }
}