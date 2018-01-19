/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gofish;
import java.util.*;
/**
 *
 * @author f_lapi
 */
public class GoFish {

    final static int NUMBER_OF_SHUFFLES = 100;
    static boolean gameOver;
    static List<String> names; 
    
    static Player[] players;
    static List<Card> deck;
    static Scanner scanner;
    static long[] times;
    
    public static void main(String[] args) {
        
        //Stuff that other people should be doing that I can delete later
        scanner = new Scanner(System.in);
        deck = new ArrayList<>();
        //End of stuff that other people should be doing that I can delete later

        boolean rightAmountOfPlayer = false;
        int nbOfPlayers = 2;

        System.out.println("Welcome to Blitz Go Fish\n\n" +
                "How many players will be playing? (Max of 5 players)");

        while(!rightAmountOfPlayer) {
            nbOfPlayers = scanner.nextInt();
            scanner.nextLine();
            if(nbOfPlayers >5 || nbOfPlayers <2){
                rightAmountOfPlayer = false;
                System.out.println("Please enter a valid amount of players. (Max Amount of player is 5)");
            } else {
                rightAmountOfPlayer = true;
            }
        }

        Player[] players = new Player[nbOfPlayers];

        System.out.println("\nEnter the name of each player.");
        String name;

        for(int i=0; i<nbOfPlayers; i++){
            System.out.print("Player" + (i+1) + ": ");
            name = scanner.nextLine();
            players[i] = new Player(name);
        }

        //create deck
        for (int i = 0; i < 4; i++){
            for (int j = 1; j<=13; j++){
                Card newOne = new Card();
                if (i == 0){
                    newOne.setSuit("Spades");
                }
                else if (i == 1){
                    newOne.setSuit("Hearts");
                }
                else if (i == 2){
                    newOne.setSuit("Clovers");
                }
                else{
                    newOne.setSuit("Diamonds");
                }
                newOne.setValue(j);
                deck.add(newOne);
            }
        }
        //shuffle deck
        Collections.shuffle(deck);


        //deals cards (NOT WORKING)
        for (int i = 0; i < nbOfPlayers; i++){
            if (nbOfPlayers == 2 || nbOfPlayers == 3){
                for(int j = 0; j < 7; j++){
                    Card newOne = deck.get(0).copy();
                    deck.remove(0);
                    players[i].getHand().add(newOne);
                }
            }
            else{
                for(int j = 0; j < 5; j++){
                    Card newOne = deck.get(0).copy();
                    deck.remove(0);
                    players[i].getHand().add(newOne);
                }
            }
        }
        boolean inputValid;
        
        //Timers
        //Create an array for the time of each player
        times = new long[players.length];
        int minutes = 5;
        //Recieve the time as an input from the user
        do
        {
            inputValid = true;
            System.out.print("\nHow much time, in minutes, should each player have?: ");
            //Keep asking until the user enters an integer
            try
            {
                minutes = scanner.nextInt();
                scanner.nextLine();
                //Make sure the time is at least 1 minute
                if(minutes <= 0)
                    throw new Exception("Number inputted less than one.");                    
            }
            catch (Exception ex)
            {
                System.out.println("Please enter an integer number greater than 0.");
                scanner.nextLine();
                inputValid = false;
            }
        } while(!inputValid);
        
        for(int i = 0; i < times.length; i++)
        {
            times[i] = 500000000; //minutes * 60 * 1000;++++++++++++++++++++++++++FIX ME
        }
        
        //To hold whether the game has ended or not
        gameOver = false;
        
        //For easier name recognition
        names = new ArrayList<>();
        for(Player player : players)
            names.add(player.getName().toLowerCase());
        
        //To calculate how much time a turn has taken
        long turnStartTime;
        long turnTotalTime;
        long timeLeft;

        int[] playersWithNoTime = new int[nbOfPlayers];
        int count = 0;
        for(int i=0; i<nbOfPlayers; i++){
            playersWithNoTime[i] = 1;
        }


        //Main game loop
        while(!gameOver)
        {
            for(int i = 0; i < players.length; i++)
            {
                Player player = players[i];                
                if (player.getHand().size() <= 0 && deck.size() <= 0)
                {
                    System.out.println(player.getName() + " has no cards, and the deck is empty, so they must skip their turn.");
                }
                else if(times[i] <= 0)
                {
                    System.out.println(player.getName() + " has no time left, so they must skip their turn.");
                    playersWithNoTime[i] = 0;

                    boolean csgo = false;
                    count=0;
                    for(int k=0; k<playersWithNoTime.length; k++){
                        if(playersWithNoTime[k]==0){
                            count +=1;
                        }
                    }

                    if(count == nbOfPlayers)
                        csgo = true;

                    gameOver = csgo;
                }
                else
                {
                    //Get the time at which the player started their turn
                    turnStartTime = System.currentTimeMillis();
                    
                    //Print the books already gotten by any player, without saying who got them
                    String books = "Books completed: ";
                    //Add all the values of all the books to the pile
                    for(int j = 0; j < players.length; j++)
                        for(int value : players[j].getBooks())
                            books += toCardType(value) + ", ";
                    
                    //If no books have been obtained, put "none".
                    if (books.equals("Books completed: "))
                        books += "none.";
                    
                    //Display the books.
                    System.out.println(books);

                    //Print the player's hand
                    System.out.println("Your hand:");
                    for(Card card : player.getHand())
                        System.out.println(card.toString());

                    //Print the time they have left
                    timeLeft = (long)(times[i] / 1000.0);
                    System.out.println("You have " + timeLeft + " seconds left this game.");
                    
                    //Create variables to hold the name of the player stealing from, and the value of the cards asked for
                    String playerStolen;
                    int valueStolen = 0;
                    do
                    {
                        inputValid = true;
                        System.out.print("Which player would you like to steal a card from?: ");
                        playerStolen = scanner.nextLine();
                        
                        //Make sure the name they entered (ignoring caps) is the name of one of the players
                        //Note you cannot steal from yourself.
                        if (names.contains(playerStolen.toLowerCase()) && !playerStolen.equals(player.getName()))
                        {
                            do
                            {
                                try
                                {
                                    inputValid = true;
                                    System.out.println("What card would you like to ask for? (#/jack/queen/king/ace)");
                                    
                                    //Take the string they input and turn it into an int representing that card's value
                                    valueStolen = toValue(scanner.nextLine());
                                    
                                    //Make sure the number entered actually corresponds to a playing card
                                    if (valueStolen < 1 || valueStolen > 13)
                                    {
                                        System.out.println("Please enter a number from 1 to 13, or the name of the card.");
                                        //ask again
                                        inputValid = false;
                                    }
                                }
                                catch (Exception ex)
                                {
                                    //Possible exceptions: if they did not enter a playing card's name, the toValue method
                                    //will parse to a number. If that throws a format exception, let the user know that they entered
                                    //the wrong format.
                                    System.out.println("That word does not correspond to a card.");
                                    //ask again
                                    inputValid = false;
                                }
                            } while(!inputValid);
                        }
                        else //If the name entered is not good, explain why.
                        {
                            if (playerStolen.equals(player.getName()))
                                System.out.println("You cannot steal from yourself.");
                            else
                                System.out.println(playerStolen + " is not a player.");
                            //ask again
                            inputValid = false;
                        }

                    } while (!inputValid);
                                        
                    //Now playerStolen is a valid player name, and valueStolen is a valid value type
                    //The following will be True if the player being stolen from did have at least one of that card.
                    boolean cardWasStolen = false;
                    
                    //Go through each player to find the one with that name
                    for(int j = 0; j < players.length; j++)
                    {
                        Player robbedPlayer = players[j];
                        //The following if only executes if we got the player that we were to steal from.
                        //Note this will never be true for player = robbedPlayer since the user was not allowed to enter their own name.
                        if (robbedPlayer.getName().equals(playerStolen))
                        {
                            //Create a separate list to hold the cards that will be removed from their hand
                            List<Card> cardsToBeRemoved = new ArrayList<>();
                            
                            //For each card, if it has the value asked, add it to the list of cards to be removed
                            for(Card card : robbedPlayer.getHand())
                            {
                                if (card.getValue() == valueStolen)
                                {
                                    //Set this to true to say that a card was stolen and the player does not need to go fish
                                    cardWasStolen = true;
                                    cardsToBeRemoved.add(card);
                                }
                            }
                            //Then, go through each card that was flagged for removal
                            for(Card card : cardsToBeRemoved)
                            {
                                //Remove it from the robbed player's hand
                                robbedPlayer.getHand().remove(card);
                                
                                //Add it to the robbing player's hand
                                player.getHand().add(card);
                                
                                //Let the player know what they stole
                                System.out.println("You stole the " + card.toString() + " from " + robbedPlayer.getName() + "!");
                            }
                        }
                    }
                    //If no card was stolen, this block executes
                    if (!cardWasStolen)
                    {
                        System.out.println("Go Fish!");
                        
                        //If there are cards in the deck, give one to the player.
                        if (deck.size() > 0) {
                            player.getHand().add(deck.get(0));
                            deck.remove(0);
                        }
                        else
                            System.out.println("But there are no more cards in the deck. :(");
                    }

                    //Check if there are any new books in the player's hand
                    int[] values = new int[13];
                    for(Card card : player.getHand())
                    {
                        values[card.getValue() - 1]++; //because array of values goes from 0 to 12 but card values go from 1 to 13
                        //without the -1 you get an index out of bounds error when checking kings (tries to access index 13)
                    }
                    //If the player has at least 4 of a type of card, that's a book
                    for(int j = 0; j < values.length; j++)
                    {
                        if (values[j] >= 4)
                        {
                            //Give that book to the player
                            player.getBooks().add(j + 1);
                            //Remove the booked cards from the player's hand
                            for(int k = 0; k < player.getHand().size();)
                            {
                                if(player.getHand().get(k).getValue() == j + 1)
                                    player.getHand().remove(k);
                                else
                                    k++;
                            }
                        }
                    }
                    turnTotalTime = System.currentTimeMillis() - turnStartTime;
                    System.out.println("Your turn took " + (long)(turnTotalTime / 1000) + " seconds.");
                    times[i] -= turnTotalTime;
                    if (times[i] < 0)
                        times[i] = 0;
                    System.out.println(player.getName() + " now has " + (long)(times[i] / 1000) + " seconds left.");

                    if (times[i] <= 0)
                    {
                        if (player.getHand().size() > 0)
                        {
                            //Put all of the player's cards into the deck
                            for(Card card : player.getHand())
                                deck.add(card);
                            //Empty the player's hand
                            player.getHand().clear();
                            //Shuffle the deck
                            shuffle(deck);
                        }
                    }

                    //Test for game over 
                    boolean testForGameOver = true;
                    
                    //The game is still going on as long as players have cards
                    for(int j = 0; j < players.length; j++)
                        if (players[j].getHand().size() > 0)
                            testForGameOver = false;
                    
                    //The game is still going on as long as there are cards in the deck
                    if (deck.size() > 0)
                        testForGameOver = false;
                    
                    //The game is still going on if any player has time left
                    for(long time : times)
                    {
                        if (time > 0)
                            testForGameOver = false;
                    }

                    //If any of the above conditions was false, then the gameOver is false.
                    gameOver = testForGameOver;
                
                }

                //Regardless of whether the player has taken a turn or not,
                String nextPlayerName;
                if (i != players.length - 1)
                    nextPlayerName = players[i + 1].getName();
                else
                    nextPlayerName = players[0].getName();

                System.out.println("\n" + "It is now the turn of " + nextPlayerName + ".\nPress Enter to begin your turn.");
                scanner.nextLine();    
                System.out.println();
                System.out.println();
            }
        }


        int[] nbOfBooksForEachPlayer = new int[nbOfPlayers];            //get the amount of books each player has and put into an array of int
        for (int i=0; i<nbOfPlayers; i++) {
            nbOfBooksForEachPlayer[i] = players[i].getBooks().size();
        }

        int indexOfValue = 0;
        boolean allZeros = false;
        int placement = 1;

        while (!allZeros) {         //while loop finds max of int arr and replaces it by zero until array is all zeros
            int max = 0;
            for (int i = 0; i < nbOfBooksForEachPlayer.length; i++) {
                if (nbOfBooksForEachPlayer[i] > max) {
                    max = nbOfBooksForEachPlayer[i];
                    indexOfValue = i;
                }
            }

            if(max == 0){
                break;
            }

            nbOfBooksForEachPlayer[indexOfValue] = 0; //replace max with zero

            System.out.println(players[indexOfValue].getName() + " placed number " + placement + "."); //print name of player and his placement
            placement +=1;
        }

        System.out.println("\nHope you enjoyed playing :D");
        
    }
    public static String toCardType(int value)
    {
        switch(value)
        {
            case 11:
                return "Jacks";
            case 12:
                return "Queens";
            case 13:
                return "Kings";
            case 1:
                return "Aces";
            default:
                return Integer.toString(value);
        }
    }
    public static int toValue(String cardName)
    {
        switch(cardName.toLowerCase())
        {
            case "jack":
                return 11;
            case "queen":
                return 12;
            case "king":
                return 13;
            case "ace":
                return 1;
            default:
                return Integer.parseInt(cardName);
        }
    }
    
    public static void shuffle(List<Card> deck)
    {
        int i1, i2;
        Card temp;
        for(int i = 0; i < NUMBER_OF_SHUFFLES; i++)
        {
            i1 = (int)(Math.random() * deck.size());
            i2 = (int)(Math.random() * deck.size());
            temp = deck.get(i1).copy();
            deck.set(i1, deck.get(i2).copy());
            deck.set(i2, temp);
        }
    }
    
}
