package BlackJack;

import java.util.HashMap;

public class Cards {
    HashMap<String, Integer> cardDeck = new HashMap<String, Integer>();
    private int aceValue;

    public Cards () {
        for (int i = 1; i <= 10; i++) {
            cardDeck.put(Integer.toString(i), i);
        }
        cardDeck.put("King", 10);
        cardDeck.put("Queen", 10);
        cardDeck.put("Jack", 10);
        cardDeck.put("Ace", getAceValue());
    }

    public void showDeck() {
        for (String i : cardDeck.keySet()) {
            System.out.println(i);
        }
    }

    public void setAceValue (int aceValue) {
        this.aceValue = aceValue;
    }
    
    public int getAceValue() {
        return aceValue;
    }
}
