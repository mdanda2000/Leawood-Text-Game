package mattsTextGame;

import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Random;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;

//TO DO: Clean up output text. Possibly hide the dog until its near?
//TO DO: Research fight algorithms, integrate one into monster logic
//TO DO: Create a main menu for starting, saving, and loading games


public class TextGame {
	
	Scanner myScanner = new Scanner(System.in);	
	String userAction;
	
	int userLocation;   		
	int monsterLocation;
	int monsterMovement; //The direction the monster wants to move, which may or may not be plausible
	Random random = new Random();  //Determines which direction the monster will move
	boolean keepPlaying;	
	
	String[][] map = new String[99][6];
	int[][] keyMap = new int[99][3];
	
	ArrayList<Integer> keysFound = new ArrayList<Integer>();  //Stores the keys, which identify the nodes the user can pass through
		
	final int northNode = 0;       //The value in map[userLocation][northNode] identifies the node to the north
	final int southNode = 1;       //The value in map[userLocation][southNode] identifies the node to the south
	final int eastNode = 2;        //The value in map[userLocation][eastNode] identifies the node to the east
	final int westNode = 3;        //The value in map[userLocation][westNode] identifies the node to the west
	final int nameNode = 4;        //The text in map[userLocation][nameNode] contains the name of the node
	final int descriptionNode = 5; //The text in map[userLocation][descriptonNode] contains the text description of the node
	
	final int nodeKeyOpens = 0;   //The value in keyMap[i][nodeKeyOpens] identifies which door the key opens
	final int nodeKeyResides = 1; //The value in keyMap[i][nodeKeyResides] identifies where the player can find the key
	final int isKeyAvailable = 2; //Identifies whether the key is still on the map. If (keyMap[i][isKeyAvailable] == 1), the player has not found it yet
	
	final int mapFileTokensPerRow = 6;  //The expected number of parameters per row in the input map file. Works better than countTokens() 
	
	
	public static void main(String[] args) {

		TextGame leawood;
		leawood = new TextGame();	
												
		leawood.playerSetUp(); 			
		leawood.mapSetUp();
		
		leawood.keySetUp();
					
		leawood.printStatus();
		leawood.playGame();
	}
	
	public void playerSetUp(){
		
		userLocation = 1;    // Set the player's initial location
		keysFound.add(2);  //Give the player the key to the front door of the house
		
		monsterLocation = 9; //Set the monster's initial location
		
		keepPlaying = true; 
		
		System.out.println("Welcome to my text game.");
		printCommands();
		System.out.println("Press ENTER to begin.");
		myScanner.nextLine();
		
	}
	
	public void mapSetUp(){		
		
		try{
			//File mapFile = new File ("C:\\dandaWorkspace\\PracticeMyJavaSkills\\src\\myPracticePackage\\leawoodMap.txt");
			File mapFile = new File ("C:\\Users\\Matt\\eclipse-workspace\\Matts Text Game\\src\\mattsTextGame\\LeawoodHouseMap.txt");
			BufferedReader b = new BufferedReader(new FileReader(mapFile));
			String readline = "";
			
			int nodeRow=0; //node number, also the row number
									
			while ((readline=b.readLine())!= null){
				//System.out.println(readline);  //For debugging purposes
				StringTokenizer tokenizer = new StringTokenizer(readline, ",");				
				
				// Using "mapFileTokensPerRow" instead of "tokenizer.countTokens()" reduces potential bugs from reading in map file
				
				for (int i=0; i<mapFileTokensPerRow ;i++){					
					String token = tokenizer.nextToken();					
					map[nodeRow][i] = token;  //Populate array
					System.out.println("(" + nodeRow + "," + i + ") = " + map[nodeRow][i]); //For debugging purposes
				}
				nodeRow++;						
			}		
			b.close();
		}catch (IOException e){
			e.printStackTrace();
		}		
	}
	
	public void keySetUp() {
		
		// Same as mapSetUp() except it identifies which nodes contain keys. 
		
		// Syntax of KeyLocations file:
		// nodeKeyOpens, nodeKeyResides, isKeyAvailable (1/0),
		
		try{
			File keyFile = new File ("C:\\Users\\Matt\\eclipse-workspace\\Matts Text Game\\src\\mattsTextGame\\LeawoodHouseKeyLocations.txt");
			BufferedReader k = new BufferedReader(new FileReader(keyFile));
			String readline = "";
			int n=3; //number of parameters in each row, there's probably a way to use tokenizer to get it from readline 
			int nodeRow=0; //node number, also the row number
									
			while ((readline=k.readLine())!= null){
				//System.out.println(readline);  //For debugging purposes
				StringTokenizer tokenizer = new StringTokenizer(readline, ",");				
				
				for (int i=0;i<n;i++){					
					String token = tokenizer.nextToken();					
					keyMap[nodeRow][i] = Integer.parseInt(token);  //Populate array					
				}
				System.out.println("Key for node " + keyMap[nodeRow][nodeKeyOpens] + " is in node " + keyMap[nodeRow][1]); //For debugging purposes
				nodeRow++;						
			}		
			k.close();
		}catch (IOException e){
			e.printStackTrace();
		}		
		
		
	}
	
	public void printStatus(){		
		System.out.println(map[userLocation][descriptionNode] + " (" + userLocation + ", " + map[userLocation][4] +")");
		System.out.println("The dog is in the " + map[monsterLocation][nameNode] + " (" + monsterLocation + ")");
	}
	
	public void printCommands() {
		System.out.println();
		System.out.println("Valid commands are:");
		System.out.println("  n = North");
		System.out.println("  s = South");
		System.out.println("  e = East");
		System.out.println("  w = West");
		System.out.println("  l = Look Around");
		System.out.println("  q = Quit Game");
		System.out.println();
	}
	
	public void playGame(){
				
		while (keepPlaying) 
		{
			//Process monster -- Possibly move this to a separate method
			
			//Monster chooses a random direction
			monsterMovement = random.nextInt(4);
			
			//Validate that the random direction is possible, if so, move the monster there. Otherwise, don't move. 
			if (map[monsterLocation][monsterMovement].compareTo("00")!=0) {              //If the proposed node is not "00"
				monsterLocation = Integer.parseInt(map[monsterLocation][monsterMovement]);  //Then move the monster to that node
			}
			
			//Check if monster is near the player
			if (userLocation==monsterLocation) {
				System.out.println("The dog is in your location!!");
			}			
			
			
			//System.out.println(mapDescription[userLocation][1]);
			System.out.println("What do you want to do?:");
			userAction = myScanner.nextLine();
			
			switch(userAction){
			case "l": //Look around, see what's available
				this.printStatus();				
				break;
			case "n":				
				if (map[userLocation][northNode].compareTo("00")!=0) {        //If the node to the north is not "00"
					if(isDoorUnlocked(Integer.parseInt(map[userLocation][northNode]))){  //If user has the key
						userLocation = Integer.parseInt(map[userLocation][northNode]);  //Then move the user to the node to the north
						System.out.println("You moved north.");
						this.printStatus();	
					}						
				}
				else System.out.println("Sorry, but you cannot go north.");
				break;
			case "s":
				if (map[userLocation][southNode].compareTo("00")!=0) {              //If the node to the south is not "00"
					if(isDoorUnlocked(Integer.parseInt(map[userLocation][southNode]))){  //If user has the key
						userLocation = Integer.parseInt(map[userLocation][southNode]);  //Then move the user to the node to the south
						System.out.println("You moved south.");
						this.printStatus();	
					}
				}
				else System.out.println("Sorry, but you cannot go south.");
				break;
			case "e":
				if (map[userLocation][eastNode].compareTo("00")!=0) {              //If the node to the east is not "00"
					if(isDoorUnlocked(Integer.parseInt(map[userLocation][eastNode]))){  //If user has the key
						userLocation = Integer.parseInt(map[userLocation][eastNode]);  //Then move the user to the node to the east
						System.out.println("You moved east.");
						this.printStatus();	
					}
				}
				else System.out.println("Sorry, but you cannot go east.");
				break;
			case "w":
				if (map[userLocation][westNode].compareTo("00")!=0) {              //If the node to the west is not "00"
					if(isDoorUnlocked(Integer.parseInt(map[userLocation][westNode]))){  //If user has the key
						userLocation = Integer.parseInt(map[userLocation][westNode]);  //Then move the user to the node to the west
						System.out.println("You moved west.");
						this.printStatus();	
					}
				}
				else System.out.println("Sorry, but you cannot go west.");
				break;
			case "q":
				keepPlaying=false;
				break;
			default:
				printCommands();
				//System.out.println("Valid commands are: n,s,e,w,l,q");
			}	
			
			checkForKey(userLocation); // Check if the new node location has any keys			
			
		}
		
		// This code runs after keepPlaying is set to false
		System.out.println("--You quit!--");		
	}	
	
	public boolean isDoorUnlocked(int nodeNumber) {
		if (map[nodeNumber][nameNode].equals("doorway")){
			for (int i : keysFound) {
				if (nodeNumber == i) {
					System.out.println("The door is locked, but you have a key!");
					return true;  // User has the key
				}
			}
			System.out.println("The door is locked. You don't have a key.");
			return false; // The door is locked and the above loop did not find the key in the list
		}
		return true;  // The node isn't a door, so it's unlocked by default
	}
	
	public void checkForKey(int nodeNumber) {
		// Check whether the node contains a key. Compare the nodeNumber with the values in keyMap[i][nodeKeyResides]
					
		for (int i=0; i < keyMap.length; i++) {  //Check every row in the keyMap[][] array
			if (nodeNumber == keyMap[i][nodeKeyResides]) {
				if (keyMap[i][isKeyAvailable]==1) {
					//Add the key to the keysFound array
					keysFound.add(keyMap[i][0]);   
					
					//Set the value in keyMap[i][2] to "0" to indicate that the key is no longer in the node
					keyMap[i][isKeyAvailable] = 0;
					
					System.out.println("You found a key! " + keyMap[i][nodeKeyOpens]);
				}
								
			}
		}
		
		
	}
}
