package edu.and.a4370finalproject;

import android.content.Context;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameController {
    private Deck deck;
    private List<Card> baseCards;
    private List<Card> playerCards;
    private List<Card> cpuCards;

    public GameController(Context context) {
        deck = new Deck(context);
        deck.shuffle();
        baseCards = new ArrayList<>();
        playerCards = new ArrayList<>();
        cpuCards = new ArrayList<>();
        dealInitialCards();
    }

    private void dealInitialCards() {
        for (int i = 0; i < 3; i++) {
            baseCards.add(deck.drawCard());
            playerCards.add(deck.drawCard());
            cpuCards.add(deck.drawCard());
        }
    }

    public List<Card> getFlopCards() {
        return baseCards;
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public List<Card> getCpuCards() {
        return cpuCards;
    }

    public void swapPlayerCards(List<Integer> selectedIndices) {
        if (selectedIndices.isEmpty() || selectedIndices.size() > 3) {
            return;
        }

        for (int index : selectedIndices) {
            if (index >= 0 && index < playerCards.size()) {
                playerCards.set(index, deck.drawCard());
            }
        }
    }

    public void startNewRound() {

        baseCards.clear();
        playerCards.clear();
        cpuCards.clear();

        deck.shuffle();

        dealInitialCards();
    }

    public String judgeRound(List<Card> playerCards, List<Card> cpuCards, List<Card> flopCards) {
        List<Card> playerHand = new ArrayList<>(playerCards);
        playerHand.addAll(flopCards);

        List<Card> cpuHand = new ArrayList<>(cpuCards);
        cpuHand.addAll(flopCards);

        String playerHandType = evaluateHand(playerHand);
        String cpuHandType = evaluateHand(cpuHand);


        int result = compareHands(playerHandType, cpuHandType);

        if (result > 0) {
            return "You win  "+ playerHandType;
        } else if (result < 0) {
            return "You lose " + cpuHandType;
        } else {
            return "It's a tie!";
        }
    }

    public List<Card> getRandomCpuCards() {
        List<Card> cpuSelectedCards = new ArrayList<>();
        Random random = new Random();

        int index1 = random.nextInt(cpuCards.size());
        int index2;
        do {
            index2 = random.nextInt(cpuCards.size());
        } while (index2 == index1);

        cpuSelectedCards.add(cpuCards.get(index1));
        cpuSelectedCards.add(cpuCards.get(index2));

        return cpuSelectedCards;
    }
    private String evaluateHand(List<Card> hand) {
        if (isRoyalFlush(hand)) {
            return "Royal Flush";
        }
        if (isStraightFlush(hand)) {
            return "Straight Flush";
        }
        if (isFourOfAKind(hand)) {
            return "Four of a Kind";
        }
        if (isFullHouse(hand)) {
            return "Full House";
        }
        if (isFlush(hand)) {
            return "Flush";
        }
        if (isStraight(hand)) {
            return "Straight";
        }
        if (isThreeOfAKind(hand)) {
            return "Three of a Kind";
        }
        if (isTwoPair(hand)) {
            return "Two Pair";
        }
        if (isOnePair(hand)) {
            return "One Pair";
        }
        return "High Card"; }
    private boolean isRoyalFlush(List<Card> cards) {
        if (!isFlush(cards)) {
            return false;
        }

        Set<Rank> ranks = new HashSet<>();
        for (Card card : cards) {
            ranks.add(card.getRank());
        }

        Set<Rank> royalRanks = Set.of(Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING, Rank.ACE);
        return ranks.containsAll(royalRanks);
    }
    private boolean isOnePair(List<Card> cards) {

        Collections.sort(cards);

        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).getRank() == cards.get(i + 1).getRank()) {
                return true;
            }
        }
        return false;
    }
    private boolean isTwoPair(List<Card> cards) {

        Collections.sort(cards);

        int pairsFound = 0;
        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).getRank() == cards.get(i + 1).getRank()) {
                pairsFound++;
                i++;
            }
        }
        return pairsFound == 2;
    }
    private boolean isThreeOfAKind(List<Card> cards) {

        Collections.sort(cards);

        for (int i = 0; i < cards.size() - 2; i++) {
            if (cards.get(i).getRank() == cards.get(i + 1).getRank() &&
                    cards.get(i + 1).getRank() == cards.get(i + 2).getRank()) {
                return true;
            }
        }
        return false;
    }
    private boolean isStraight(List<Card> cards) {

        Collections.sort(cards);

        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i + 1).getRank().ordinal() != cards.get(i).getRank().ordinal() + 1) {
                return false;
            }
        }
        return true;
    }
    private boolean isFlush(List<Card> cards) {

        Suit suit = cards.get(0).getSuit();
        for (Card card : cards) {
            if (card.getSuit() != suit) {
                return false;
            }
        }
        return true;
    }
    private boolean isFullHouse(List<Card> cards) {
        Collections.sort(cards);

        if ((cards.get(0).getRank() == cards.get(1).getRank() &&
                cards.get(1).getRank() == cards.get(2).getRank() &&
                cards.get(3).getRank() == cards.get(4).getRank()) ||
                (cards.get(0).getRank() == cards.get(1).getRank() &&
                        cards.get(2).getRank() == cards.get(3).getRank() &&
                        cards.get(3).getRank() == cards.get(4).getRank())) {
            return true;
        }
        return false;
    }
    private boolean isFourOfAKind(List<Card> cards) {

        Collections.sort(cards);

        for (int i = 0; i < cards.size() - 3; i++) {
            if (cards.get(i).getRank() == cards.get(i + 1).getRank() &&
                    cards.get(i + 1).getRank() == cards.get(i + 2).getRank() &&
                    cards.get(i + 2).getRank() == cards.get(i + 3).getRank()) {
                return true;
            }
        }
        return false;
    }
    private boolean isStraightFlush(List<Card> cards) {
        return isStraight(cards) && isFlush(cards);
    }

    private int compareHands(String hand1, String hand2) {
        List<String> handOrder = List.of(
                "Royal Flush", "Straight Flush", "Four of a Kind", "Full House",
                "Flush", "Straight", "Three of a Kind", "Two Pair", "One Pair", "High Card"
        );

        int index1 = handOrder.indexOf(hand1);
        int index2 = handOrder.indexOf(hand2);

        return Integer.compare(index1, index2);
    }
}