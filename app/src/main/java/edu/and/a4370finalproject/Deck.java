package edu.and.a4370finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;
    private int currentCardIndex;
    public Deck(Context context) {
        cards = new ArrayList<>();
        Rank[] ranks = Rank.values();
        Suit[] suits = Suit.values();

        int spriteWidth = 71;
        int spriteHeight =94;
        int spacing = 1;

        for (Suit suit : suits) {
            for (Rank rank : ranks) {
                Bitmap cardSprite = extractCardSprite(context, suit, rank, spriteWidth, spriteHeight, spacing);
                Card card = new Card(suit, rank, cardSprite);
                cards.add(card);
            }
        }
        currentCardIndex = 0;
    }
    public List<Card> getCards() {
        return cards;
    }
    private Bitmap extractCardSprite(Context context, Suit suit, Rank rank, int spriteWidth, int spriteHeight, int spacing) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.deck,options);

        int x = rank.ordinal() * (spriteWidth + spacing);
        int y = suit.ordinal() * spriteHeight;

        Bitmap cardSprite = Bitmap.createBitmap(spriteSheet, x, y, spriteWidth, spriteHeight);

        spriteSheet.recycle();

        return cardSprite;
    }

    public void shuffle(){
        Collections.shuffle(cards);
        currentCardIndex = 0;
    }
    public Card drawCard() {
        if (currentCardIndex < cards.size()) {
            Card drawnCard = cards.get(currentCardIndex);
            currentCardIndex++;
            return drawnCard;
        } else {
            return null;
        }
    }
}