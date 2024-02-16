import java.util.*;
import java.io.*;



//Aaron Timpko, Professor Guha, Computer Science 2, 1-28-2024
public class cards{

	public static Scanner sc = new Scanner(System.in);

	static int maxPlayers = 2;
	static int maxDeck = 5;
	static int totalCards = 10;

	public static void main(String[] args) throws FileNotFoundException {

		Player[] players = new Player[maxPlayers];
		Card[] cardList = new Card[totalCards];
		initializeCards(cardList);
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

					if(playerCount < maxPlayers){
						makePlayer(players, playerCount);
						playerCount++;
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

					doBattle(players[playerOne], players[playerTwo], cardList);
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
	}

	//This funtion adds any number of cards to a player's deck
	public static void addCard(Player[] players, int player, Card[] cardList){

		int i = 0;
		
		while(i == 0){
			System.out.println("Please enter the card ID you'd like to add:");
        	int cardId = Integer.parseInt(sc.nextLine());

			int j = players[player].addCard(cardId);

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
	public static void initializeCards(Card[] cardList) throws FileNotFoundException{
		Scanner input = new Scanner(new File("decklist.txt"));

		int i = 0;
		try {
			File in = new File("decklist.txt");
			Scanner sc = new Scanner(in);

			for(int l = 0; l < totalCards; l++){
				cardList[i] = new Card();

				cardList[i].setName(sc.next());
				cardList[i].setNum(sc.nextInt());
				cardList[i].setDiceOne(sc.nextInt());
				cardList[i].setDiceTwo(sc.nextInt());
				for(int x = 0; x < 3; x++){
					cardList[i].setCardType(sc.nextInt(), x);
				}
				

				System.out.println(cardList[i].getNum() + " " + cardList[i].getName());

				i++;
			}

			sc.close();

	   } catch (IOException e) {
			e.printStackTrace();
	   }

	   input.close();

}

	//Used for battles, gets random number between min and max
	public static int getRandom(int min, int max){
		int range = (max - min);

		return (int)((range * Math.random()) + min);
	}

	public static void doBattle(Player one, Player two, Card[] cardList){
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


			//Note- Blocks are first, then counters, then attacks
			//Declare what they're playing
			System.out.println(one.getName() + " plays " + cardList[one.deck[oneCard]].getName());
			System.out.println(two.getName() + " plays " + cardList[two.deck[twoCard]].getName());

			System.out.println();


			//Defense goes first
			if(cardList[one.deck[oneCard]].getCardType()[1] == 1){
				one.setAC(one.getAC() + cardList[one.deck[oneCard]].getDiceTwo());
				System.out.println(one.getName() + " has increased their AC to " + one.getAC() + " for the turn.");
			}

			if(cardList[two.deck[twoCard]].getCardType()[1] == 1){
				two.setAC(two.getAC() + cardList[two.deck[twoCard]].getDiceTwo());
				System.out.println(two.getName() + " has increased their AC to " + two.getAC() + " for the turn.");
			}
			
			//Counter goes second
			if(cardList[one.deck[oneCard]].getCardType()[2] == 1){
				one.setAC(one.getAC() - cardList[one.deck[oneCard]].getDiceOne());
				System.out.println(one.getName() + " has decreased their AC to " + one.getAC() + " for the turn, and is countering.");
				one.setStatus(0, 1);
			}

			if(cardList[two.deck[twoCard]].getCardType()[2] == 1){
				two.setAC(two.getAC() - cardList[two.deck[twoCard]].getDiceOne());
				System.out.println(two.getName() + " has decreased their AC to " + two.getAC() + " for the turn, and is countering.");
				two.setStatus(0, 1);
			}

			//Attacks go third
			if(cardList[one.deck[oneCard]].getCardType()[0] == 1){
				int damD;
				int hitD = cardList[one.deck[oneCard]].getHit();
				System.out.println(one.getName() + " attacks, rolling a " + hitD + ".");

				if(hitD > two.getAC()){
					damD = cardList[one.deck[oneCard]].getDam();
					two.setHealth(two.getHealth() - damD);

					System.out.println("That's a hit! Take " + damD + " damage!");
				}
				else{
					System.out.println("That's a miss!");
				}

				if(two.getStatus(0) == 1){
					damD = cardList[two.deck[twoCard]].getDam();
					System.out.println(two.getName() + " is countering! " + one.getName() + " takes " + damD + "!");
				}
			}

			if(cardList[two.deck[twoCard]].getCardType()[0] == 1){
				int damD;
				int hitD = cardList[two.deck[twoCard]].getHit();
				System.out.println(two.getName() + " attacks, rolling a " + hitD + ".");

				if(hitD > one.getAC()){
					damD = cardList[two.deck[twoCard]].getDam();
					one.setHealth(one.getHealth() - damD);

					System.out.println("That's a hit! Take " + damD + " damage!");
				}
				else{
					System.out.println("That's a miss!");
				}

				if(one.getStatus(0) == 1){
					damD = cardList[one.deck[oneCard]].getDam();
					System.out.println(one.getName() + " is countering! " + two.getName() + " takes " + damD + "!");
				}
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
			
			two.setStatus(0, 0);
			one.setStatus(0, 0);

			one.setAC(10);
			two.setAC(10);

			turnCount++;
		}

		one.setHealth(15);
		two.setHealth(15);
	}

}

//Describing each card
class Card implements Serializable {

	//Every card has a name, a num, a dice one and dice 2
	//For an attack, dice 1 is hit dice, and dice 2 is attack damage
	//for a defense, dice 1 is always 0, and dice 2 is the AC increase
	//For a counter, dice 1 is the new AC, and dice 2 is the attack
	String name;
	int num;
	int diceOne;
	int diceTwo;
	int[] cardType = new int[3];
	Random rand = new Random();

	public void setName(String name) {
		this.name = name;
	}

	public void setNum(int num){
		this.num = num;
	}

	public void setDiceOne(int diceOne){
		this.diceOne = diceOne;
	}

	public void setDiceTwo(int diceTwo){
		this.diceTwo = diceTwo;
	}

	public void setCardType(int cardType, int i){
		this.cardType[i] = cardType;
	}

	public String getName(){
		return name;
	}

	public int getDiceOne(){
		return diceOne;
	}

	public int getDiceTwo(){
		return diceTwo;
	}

	public int getNum(){
		return num;
	}

	public int[] getCardType(){
		return cardType;
	}

	//These functions roll hit (chance to hit) and damage
	public int getHit(){
		return rand.nextInt(diceOne + 1);
	}

	public int getDam(){
		return rand.nextInt(diceTwo + 2);
	}
}

//Describes each player
class Player{
	String name;
	int[] deck = new int[100];
	int deckSize;
	int curDeckSize;
	int health = 15;
	int AC = 10;

	//Status array is player status'. Only one exists- readying attacks,
	//which will allow afree attack if attacked first.
	int[] status = new int[1];


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
	
	public void setAC(int AC){
		this.AC = AC;
	}

	//Status is the number of what status it is. X is
	//whether it's on or off.
	public void setStatus(int status, int x){
		this.status[status] = x;
	}

	public String getName(){
		return name;
	}

	public int[] getDeck(){
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

	//returns the value of the status at stat
	public int getStatus(int stat){
		return status[stat];
	}

	public int addCard(int cardNum){
		if(curDeckSize == deckSize) return 1;

		deck[curDeckSize] = cardNum - 1;

		curDeckSize++;

		return 0;
	}

}