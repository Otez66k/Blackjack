package BlackJack;

import java.util.HashMap;

public class Computer {

    private int handValue;
    private HashMap<String, Integer> computerHand;

    public int getHandValue () {
        return handValue;
    }

    public int getCard (String card, Cards cards) {
        return cards.cardDeck.get(card);
    }

    public Computer() {
        handValue = 0;
        computerHand = new HashMap<String, Integer>();
    }

    public HashMap<String, Integer> getComputerHand() {
        return computerHand;
    }

    public void setComputerHand(String card, int value) {
        String s = card;
        while (computerHand.put(card, value) != null) {
            card += "!";
        }
        
        handValue += value;
    }
    
}
