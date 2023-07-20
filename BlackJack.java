package BlackJack;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.naming.directory.InvalidAttributesException;

public class BlackJack {

    public void start (Player player, int bet, Computer computer) {
        if (bet > player.getBalance()) {
            System.out.println("You cannot bet more money than you actually have.");
        }
        else {
            boolean replay = false;
            do {
                Scanner sc = new Scanner(System.in);
                Cards cards = new Cards();

                
                try {
                    System.out.println("\nWELCOME TO BLACKJACK!");
                    System.out.println("----------------------------\n");
                    Thread.sleep(2000);
                    System.out.println("Dealing cards...");
                    System.out.println("----------------------------");
                    Thread.sleep(2000);
                } 
                catch (InterruptedException e) {
                    e.printStackTrace();
                }   

                String computerStarterCard =  generateRandomCard(cards.cardDeck.keySet());
                List<String> computerCards = new ArrayList<>();
                boolean gameEnd = false;
                boolean error = false; 
                replay = false;
                innerLoop:
                    while (gameEnd == false) {
                        String randomCardPlayer =  generateRandomCard(cards.cardDeck.keySet());
                        
                        // Deal the first 2 starter cards
                        if (player.getPlayerHand().size() < 2) {
                            if (randomCardPlayer.equals("Ace")) {
                                do {
                                    error = false;

                                    try {
                                        System.out.println("You have an Ace. Select it's value (1 or 11): ");
                                        int aceValue = sc.nextInt();
                                        if (aceValue != 1 && aceValue != 11) { throw new InvalidAttributesException();}
                                        cards.setAceValue(aceValue);
                                    }
                                    catch (InvalidAttributesException e) {
                                        System.out.println("Please input either 1 or 11");
                                        error = true;
                                        sc.nextLine();   
                                    }
                                }
                                while (error);
                            }
                                if (randomCardPlayer.equals("Ace")) {
                                    player.setPlayerHand(randomCardPlayer, cards.getAceValue());
                                }
                                else {
                                    player.setPlayerHand(randomCardPlayer, cards.cardDeck.get(randomCardPlayer));
                                }
                                
                        }
                        else {

                            if (computerCards.contains(computerStarterCard) != true) {
                                computerCards.add(computerStarterCard);
                                computer.setComputerHand(computerStarterCard, cards.cardDeck.get(computerStarterCard));
                            }
                            
                            
                            System.out.println("\nOPPONENT HAND: ");
                            System.out.println(computerCards.get(0) + " , [Cards Hidden]");


                            System.out.println("\nYOUR HAND:\n");
                            int count = 0;
                            for (String s : player.getPlayerHand().keySet()) {
                                s = s.replace("!", "");
                                System.out.print(s);
                                count++;
                                if (count < player.getPlayerHand().size()) {
                                    System.out.print(" , ");
                                }
                            }
                            System.out.println();
                            System.out.println("\n" + "TOTAL HAND VALUE: " + player.getHandValue() + "\n");
                            System.out.println("----------------------------");
                            


                            if (playerWinBlackjack(player, bet)) { break; }
                            if (playerLoseBusted(player, bet)) { break; }
                            do {
                                error = false;

                                try {
                                    System.out.println("Hit or Stand (H/S): ");
                                    String hos = sc.next();
                                    if (!hos.equals("H") && !hos.equals("S") &&
                                    !hos.equals("h") && !hos.equals("s")) {
                                        throw new InputMismatchException();
                                    }
                                    else if (hos.equals("H") || hos.equals("h")) {
                                        randomCardPlayer =  generateRandomCard(cards.cardDeck.keySet());
                                        dealCards(player, cards, randomCardPlayer, sc);
                                        System.out.println("\nYOU PICKED UP A " + randomCardPlayer);
                                    }
                                    else if (hos.equals("S") || hos.equals("s")) {
                                        System.out.println("\nOPPONENT'S TURN\n");
                                        try {
                                            Thread.sleep(2000);
                                            System.out.println("OPPONENT DRAWS CARDS..");
                                            Thread.sleep(1500);
                                            computerPlay(computer, cards, computerCards, player);
                                            break innerLoop;
                                        }
                                        catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                catch (InputMismatchException e) {
                                    System.out.println("\nPlease input either H or S for Hit or Stand respectively");
                                    error = true;
                                    sc.nextLine();
                                }
                            }
                            while (error);   
                        }
                } 

                if (player.getHandValue() > computer.getHandValue() && player.getHandValue() <= 21 ) {
                    System.out.println("----------------------------");
                    System.out.println("\nCONGRATULATIONS YOU WIN! YOU HAVE WON $" + bet + "\n");
                    player.increaseBalance(bet);
                }
                else if (player.getHandValue() == computer.getHandValue()) {
                    System.out.println("PUSH");
                }
                else if (player.getHandValue() < computer.getHandValue() && computer.getHandValue() <= 21) {
                    System.out.println("----------------------------");
                    System.out.println("YOUR OPPONENT WINS! YOU HAVE LOST $" + bet + "\n");
                    player.decreaseBalance(bet);
                }
                else if (player.getHandValue() < computer.getHandValue() && computer.getHandValue() > 21) {
                    System.out.println("----------------------------");
                    System.out.println("\nCONGRATULATIONS YOU WIN! YOU HAVE WON $" + bet + "\n");
                    player.increaseBalance(bet);
                }
                System.out.println("YOUR HAND VALUE: " + player.getHandValue());
                System.out.println("OPPONENT HAND VALUE: " + computer.getHandValue());
                System.out.println("\nYOUR BALANCE: $" + player.getBalance() + "\n");

                System.out.println("\nOPPONENT'S HAND:\n");

                int count = 0;
                for (String s : computerCards) {
                    System.out.print(s);
                    count++;
                    if (count < computerCards.size()) {
                        System.out.print(" , ");
                    } 
                }
                System.out.println("\n\nYOUR HAND:\n");
                count = 0;
                for (String s : player.getPlayerHand().keySet()) {
                    s = s.replace("!", "");
                    System.out.print(s);
                    count++;
                    if (count < player.getPlayerHand().size()) {
                        System.out.print(" , ");
                    }
                }
                System.out.println();

                if (player.getBalance() == 0) {
                    System.out.println("\nGAME OVER! YOU HAVE RUN OUT OF MONEY TO PLAY!");
                }
                else {
                    do {
                        error = false;
                        try {
                            System.out.print("\nDO YOU WISH TO PLAY AGAIN (Y/N)? : ");
                            String replayMsg = sc.next();

                            if (replayMsg.equalsIgnoreCase("y")) {
                                player = new Player(player.getBalance());
                                computer = new Computer();
                                replay = true;
                            }
                            else if (replayMsg.equalsIgnoreCase("n")) {
                                replay = false;
                            }
                            else {
                                throw new Exception();
                            }
                        }
                        catch (Exception e) {
                            System.out.println("\n<--- PLEASE ONLY INPUT Y OR N --->\n"); 
                            sc.nextLine();
                            error = true;
                        }
                    }
                    while (error);
                }
            }
            while (replay);
            
            System.out.println("\nTHANK YOU FOR PLAYING BLACKJACK!");
        }
    }

    private String generateRandomCard (Set<String> keySet) {
        List<String> keyList = new ArrayList<>(keySet);
        int randIdx = new Random().nextInt(keyList.size());
        String randomKey = keyList.get(randIdx);
        
        return randomKey;
    }

    private  boolean playerWinBlackjack (Player player, int bet) {
        if (player.getHandValue() == 21) {
        System.out.println("BLACKJACK! YOU HAVE WON $" + (bet * 2));
        return true;
        }
        else {
            return false;
        }
    }

    private void computerPlay (Computer computer, Cards cards,  List<String> computerCards, Player player) {
        try {
            Thread.sleep(2000);
            
            boolean playOver = false;

            while (playOver != true) {
                String randomCardComputer =  generateRandomCard(cards.cardDeck.keySet());
                Random rand = new Random();
                int randomChance = rand.nextInt(100) + 1;
              
                if (computer.getHandValue() == 21) {
                    System.out.println("YOUR OPPONENT HITS JACKPOT!");
                    playOver = true;
                }

                else if (randomCardComputer.equals("Ace") && computer.getHandValue() + 11 <= 21) {
                    computer.setComputerHand(randomCardComputer, 11);
                    computerCards.add(randomCardComputer);
                }

                else if ( randomCardComputer.equals("Ace") && computer.getHandValue() + 11 > 21) {
                    computer.setComputerHand(randomCardComputer, 1);
                    computerCards.add(randomCardComputer);
                }

                else if (computer.getHandValue() <= 11 ) {
                    computer.setComputerHand(randomCardComputer, cards.cardDeck.get(randomCardComputer));
                    computerCards.add(randomCardComputer);
                }

                else if (computer.getHandValue() >= 11 && computer.getHandValue() <= 15) {
                    if (randomChance <= 75) {
                        computer.setComputerHand(randomCardComputer,  cards.cardDeck.get(randomCardComputer));
                        computerCards.add(randomCardComputer);
                    }
                }

                else if (computer.getHandValue() >= 15 && computer.getHandValue() <= 17) {
                    if (randomChance <= 20 ) {
                        computer.setComputerHand(randomCardComputer,  cards.cardDeck.get(randomCardComputer));
                        computerCards.add(randomCardComputer);
                    }
                }

                else if (computer.getHandValue() >= 18 && computer.getHandValue() <= 19) {
                    if (randomChance <= 5 ) {
                        computer.setComputerHand(randomCardComputer,  cards.cardDeck.get(randomCardComputer));
                        computerCards.add(randomCardComputer);
                    }
                }
                else {
                    playOver = true;
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private boolean playerLoseBusted (Player player, int bet) {
        if (player.getHandValue() > 21) {
            System.out.println("BUSTED! YOU HAVE LOST $" + bet);
            player.decreaseBalance(bet);
            return true;
        }
        else {
            return false;
        }
    }
    private void dealCards (Player player, Cards cards, String randomCard, Scanner sc) {
        boolean error = false;
        

        if (randomCard.equals("Ace")) {
            do {
                error = false;

                try {
                    System.out.println("You have an Ace. Select it's value (1 or 11): ");
                    int aceValue = sc.nextInt();
                    if (aceValue != 1 && aceValue != 11) { throw new Exception();}
                    cards.setAceValue(aceValue);
                }
                catch (Exception e) {
                    System.out.println("Please input either 1 or 11");
                    error = true;
                    sc.nextLine();   
                }
            }
            while (error);
        }
            if (randomCard.equals("Ace")) {
                player.setPlayerHand(randomCard, cards.getAceValue());
            }
            else {
                player.setPlayerHand(randomCard, cards.cardDeck.get(randomCard));
            }
    }

}
