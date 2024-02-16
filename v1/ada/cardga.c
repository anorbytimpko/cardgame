

//Aaron Timpko, Professor Guha, Computer Science 2, 1-28-2024


static int numPlayers = 2;
static int maxDeck = 5;
static int totalCards = 1;

int main() {

		Player[] players = new Player[numPlayers];
		Card[] cardList = initializeCards();
		int hasPlayer = 0;

		System.out.println("C) Create new player");
        System.out.println("L) Add a card to a deck");
        System.out.println("U) Unload cargo from ship <srcStackIndex>");
        System.out.println("M) Move cargo between stacks <srcStackIndex> <dstStackIndex>");
        System.out.println("K) Clear dock");
        System.out.println("P) Print ship stacks");
        System.out.println("S) Search for cargo <name>");
        System.out.println("Q) Quit");

        String options = null;
        Scanner scan = new Scanner(System.in); // Capturing the input
        do {
            options = scan.nextLine();
            switch (options) {
                case "C":
					if(hasPlayer == 0){
						players = makePlayer(players, hasPlayer);
						hasPlayer = 1;
					}	
					if (hasPlayer == 1){
						players = makePlayer(players, hasPlayer);
						hasPlayer = 2;
					}
					if(hasPlayer == 2){
						System.out.println("You have the maximum number of players.");
					}

                    break;
                case "L":
					System.out.println("Please enter the player number you'd like to give cards:")
                    int playerNum = Integer.parseInt(scans.nextLine());
                    break;
                case "U":
                    // do what you want
                    break;
                case "M":
                    break;
                    // Add the rest of your cases
            }
        } while (!options.equals("Q")); // quitting the program


		scan.close();

		return 0;
	}
	
	Player[] makePlayer(Player[] players, int hasPlayer){
		Scanner scans = new Scanner(System.in); // Capturing the input
		players[hasPlayer] = new Player();
		
		System.out.println("Please input player name:");

		players[hasPlayer].setName(scans.nextLine());

		System.out.println("Please input player deck size:");

		players[hasPlayer].setDeckSize(Integer.parseInt(scans.nextLine()));

		players[hasPlayer].setCurDeckSize(0);

		players[hasPlayer].initializeDeck();

		scans.close();

		return players;
	}

	public static void addCard(Player[] players, String player, int cardNum){

	}

	public static Card[] initializeCards(){
		Card[] cardList = new Card[totalCards];
		Scanner input = new Scanner(new File("decklist.txt"));

		int i = 0;
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("employees.txt"))) {
			cardList[i] = (Card) in.readObject();

			System.out.println(cardList[i].getNum() + " " + cardList[i].getName());

			i++;
			
	   } catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
	   }

	return cardList;
}

	public static int isTrue(int[] array) {
        int count = 0;

        return count;
    }


	public static int goodCase(int[] array, int a, int b, int c, int d, int e, int f) {


        return 0;
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
		return rand.nextInt(damDice + 1);
	}
}

class Player{
	String name;
	Card[] deck;
	int deckSize;
	int curDeckSize;
	int health = 100;
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

}