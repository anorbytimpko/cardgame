import java.util.*;
import java.io.*;



//Aaron Timpko, Professor Guha, Computer Science 2, 1-28-2024
public class cards{

	public static Scanner sc = new Scanner(System.in);

	static int numPlayers = 2;
	static int maxDeck = 5;
	static int totalCards = 1;

	public static void main(String[] args) throws FileNotFoundException {

		Player[] players = new Player[numPlayers];
		Card[] cardList = initializeCards();
		int playerCount = 0;


        String options = null;

        do {

			//Prints menu
			System.out.println();
			System.out.println("C) Create new player");
			System.out.println("L) Add a card to a deck");
			System.out.println("U) Play Cards");
			System.out.println("M) Move cargo between stacks <srcStackIndex> <dstStackIndex>");
			System.out.println("K) Clear dock");
			System.out.println("P) Print ship stacks");
			System.out.println("S) Search for cargo <name>");
			System.out.println("Q) Quit");
            options = sc.nextLine();

			//Reads through cases to determine what we're doing
            switch (options) {
                case "C":

					//Right now, we've two players, so we only need to check for two. 
					//Need to change to account for X max players
					if(playerCount == 0){
						makePlayer(players, playerCount);
						playerCount = 1;
					}	
					else if (playerCount == 1){
						makePlayer(players, playerCount);
						playerCount = 2;
					}
					else{
						System.out.println("You have the maximum number of players.");
					}

                    break;

					//Adds card id to player's deck- need to change to take in
					//name of card or ID.
                case "L":
					System.out.println("Please enter the player number you'd like to give cards:");
                    int playerNum = Integer.parseInt(sc.nextLine());

					if(playerNum > playerCount || playerNum < 0){
						System.out.println("Player number invalid.");
					}
					if(players[playerNum].getCurDeckSize() == players[playerNum].getDeckSize()){
						System.out.println("Deck is full!");
					}
					else{
						addCard(players, playerNum, cardList);
					}
                    break;

					//Takes in two player numbers- need to change to account
					//for name or ID in future. Sends to fight.
                case "U":
                    System.out.println("Enter the first player to do battle:");
					int playerOne = Integer.parseInt(sc.nextLine());
					System.out.println("Enter the second player to do battle:");
					int playerTwo = Integer.parseInt(sc.nextLine());

					doBattle(players[playerOne], players[playerTwo]);
                    break;
                case "M":
                    break;
                    // Add the rest of your cases
            }
        } while (!options.equals("Q")); // quitting the program


		sc.close();
	}
	
	//This function initializes a player, and sets their deck to empty.
	public static void makePlayer(Player[] players, int hasPlayer){

		players[hasPlayer] = new Player();
		
		System.out.println("Please input player name:");

		players[hasPlayer].setName(sc.nextLine());

		System.out.println("Please input player deck size:");

		players[hasPlayer].setDeckSize(Integer.parseInt(sc.nextLine()));

		players[hasPlayer].setCurDeckSize(0);

		players[hasPlayer].initializeDeck();

	}

	//This funtion adds any number of cards to a player's deck
	public static void addCard(Player[] players, int player, Card[] cardList){

		int i = 0;
		
		while(i == 0){
			System.out.println("Please enter the card ID you'd like to add:");
        	int cardId = Integer.parseInt(sc.nextLine());

			int j = players[player].addCard(cardList, cardId);

			if(j == 1){
				System.out.println("Card deck filled!");
				i = 1;
			}
			else{
				System.out.println("Continue? (0 for yes, 1 for no)");
        		i = Integer.parseInt(sc.nextLine());
			}
		}

	}

	//This initializes our cardList, and fills it with the cards given.
	public static Card[] initializeCards() throws FileNotFoundException{
		Card[] cardList = new Card[totalCards];
		Scanner input = new Scanner(new File("decklist.txt"));

		int i = 0;
		try {
			File in = new File("decklist.txt");
			Scanner sc = new Scanner(in);

			while(sc.hasNextLine()){
				cardList[i] = new Card();

				cardList[i].setName(sc.next());
				cardList[i].setNum(sc.nextInt());
				cardList[i].setHitDice(sc.nextInt());
				cardList[i].setDamDice(sc.nextInt());
				

				System.out.println(cardList[i].getNum() + " " + cardList[i].getName());

				i++;
			}

			sc.close();

	   } catch (IOException e) {
			e.printStackTrace();
	   }

	   input.close();

	   


	return cardList;
}

	//Used for battles, gets random number between min and max
	public static int getRandom(int min, int max){
		int range = (max - min);

		return (int)((range * Math.random()) + min);
	}

	public static void doBattle(Player one, Player two){
		int turnCount = 1;
		int gameOver = 0;

		System.out.println();
		System.out.println(one.getName() + " is battling " + two.getName());
		System.out.println("--------------------");
		System.out.println();

		while(gameOver == 0){
			System.out.println("Turn " + turnCount);
			System.out.println();

			//Randomly picks a card for each of them to play
			int oneCard = getRandom(0, one.getCurDeckSize());
			int twoCard = getRandom(0, two.getCurDeckSize());

			//Rolls hits now
			int oneHit = one.deck[oneCard].getHit();
			int twoHit = two.deck[twoCard].getHit();

			//Declare what they're playing
			System.out.println(one.getName() + " plays " + one.deck[oneCard].getName());
			System.out.println(two.getName() + " plays " + two.deck[twoCard].getName());

			System.out.println();

			System.out.println(one.getName() + " rolls " + oneHit + "- that's a ");

			if(oneHit > two.getAC()){
				int damage = one.deck[oneCard].getDam();

				System.out.println("hit! Take " + damage + " damage!");

				two.setHealth(two.getHealth() - damage);
			}
			else{
				System.out.println("miss!");
			}

			System.out.println();

			System.out.println(two.getName() + " rolls " + twoHit + "- that's a ");

			if(twoHit > one.getAC()){
				int damage = two.deck[twoCard].getDam();

				System.out.println("Hit! Take " + damage + " damage!");

				one.setHealth(one.getHealth() - damage);
			}
			else{
				System.out.println("Miss!");
			}

			if(two.getHealth() < 0 && one.getHealth() < 0){
				System.out.println();

				System.out.println("Tie! Both players lose.");

				gameOver = 1;
			}
			else if(one.getHealth() < 0){
				System.out.println();

				System.out.println("Game over! " + two.getName() + " wins!");
				gameOver = 1;
			}
			else if(two.getHealth() < 0){
				System.out.println();

				System.out.println("Game over! " + one.getName() + " wins!");

				gameOver = 1;
			}

			turnCount++;
		}


	}
    
}

//Describing each card
class Card implements Serializable {

	//Every card has a name, a num, a hit and dam dice
	String name;
	int num;
	int hitDice;
	int damDice;
	Random rand = new Random();

	public void setName(String name) {
		this.name = name;
	}

	public void setNum(int num){
		this.num = num;
	}

	public void setHitDice(int hitDice){
		this.hitDice = hitDice;
	}

	public void setDamDice(int damDice){
		this.damDice = damDice;
	}

	public String getName(){
		return name;
	}

	public int getNum(){
		return num;
	}

	//These functions roll hit (chance to hit) and damage
	public int getHit(){
		return rand.nextInt(hitDice + 1);
	}

	public int getDam(){
		return rand.nextInt(damDice + 2);
	}
}

//Describes each player
class Player{
	String name;
	Card[] deck;
	int deckSize;
	int curDeckSize;
	int health = 15;
	int AC = 10;


	public void setName(String name) {
		this.name = name;
	}
	
	public void setDeckSize(int deckSize){
		this.deckSize = deckSize;
	}

	public void setCurDeckSize(int curDeckSize){
		this.curDeckSize = curDeckSize;
	}

	public void setHealth(int health){
		this.health = health;
	}

	public String getName(){
		return name;
	}

	public Card[] getDeck(){
		return deck;
	}

	public int getDeckSize(){
		return deckSize;
	}

	public int getCurDeckSize(){
		return curDeckSize;
	}

	public int getHealth(){
		return health;
	}

	public int getAC(){
		return AC;
	}

	public void initializeDeck(){
		deck = new Card[deckSize];
	}

	public int addCard(Card[] cardList, int cardNum){
		if(curDeckSize == deckSize) return 1;

		deck[curDeckSize] = cardList[cardNum - 1];

		curDeckSize++;

		return 0;
	}

}
