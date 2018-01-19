import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Card> deck = new ArrayList<>();

        boolean rightAmountOfPlayer = false;
        int nbOfPlayers = 2;

        System.out.println("Welcome to Blitz Go Fish\n\n" +
                "How many players will be playing? (Max of 5 players)");

        while(!rightAmountOfPlayer) {
            nbOfPlayers = scanner.nextInt();
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
            name = scanner.next();
            players[i] = new Player(name);
        }

        //Thomas creates the deck and deals out all the cards

        boolean gameOver = false;
        while(!gameOver){
            //main loop, good luck felix
        }


        int[] nbOfBooksForEachPlayer = new int[nbOfPlayers];            //get the amount of books each player has and put into an array of int
        for (int i=0; i<nbOfPlayers; i++) {
            nbOfBooksForEachPlayer[i] = players[i].getBook().size();
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
}

