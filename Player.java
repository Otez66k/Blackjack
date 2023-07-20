package BlackJack;

import java.util.HashMap;

public class Player {
    
    private int handValue;
    private HashMap<String, Integer> playerHand;
    private int balance;

    public int getHandValue () {
        return handValue;
    }

    public int getCard (String card, Cards cards) {
        return cards.cardDeck.get(card);
    }
    
    public int getBalance() {
        return balance;
    }
    
    public void decreaseBalance (int amount) {
        balance -= amount;
    }
    
    public void increaseBalance (int amount) {
        balance += amount;
    }

    public Player(int balance) {
        this.handValue = 0;
        this.balance = balance;
        this.playerHand = new HashMap<String, Integer>();
        
    }

    public HashMap<String, Integer> getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(String card, int value) {
        String s = card;
        while (playerHand.put(card, value) != null) {
            card += "!";
        }
        
        handValue += value;
    }
    
}
