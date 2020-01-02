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


//TO DO: Continue working on key logic, which is stubbed out below
//TO DO: Research fight algorithms, integrate one into monster logic
//TO DO: Create a main menu for starting, saving, and loading games


public class TextGame {
	
	Scanner myScanner = new Scanner(System.in);	
	String userAction;
	
	ArrayList<String> keysFound = new ArrayList<String>();  //Stores the keys, which identify the nodes the user can pass through
		
	int monsterLocation;
	int monsterMovement; //The direction the monster wants to move, which may or may not be plausible
	Random random = new Random();
	boolean keepPlaying;	
	
	String[][] map = new String[99][6];
	int userLocation;   
	
	final int northNode = 0;       //The value in map[userLocation][northNode] identifies the node to the north
	final int southNode = 1;       //The value in map[userLocation][southNode] identifies the node to the south
	final int eastNode = 2;        //The value in map[userLocation][eastNode] identifies the node to the east
	final int westNode = 3;        //The value in map[userLocation][westNode] identifies the node to the west
	final int nameNode = 4;        //The text in map[userLocation][nameNode] contains the name of the node
	final int descriptionNode = 5; //The text in map[userLocation][descriptonNode] contains the text description of the node
	
		
	public static void main(String[] args) {

		TextGame leawood;
		leawood = new TextGame();	
												
		leawood.playerSetUp(); 			
		leawood.mapSetUp();
					
		leawood.printStatus();
		leawood.playGame();
	}
	
	public void playerSetUp(){
		
		userLocation = 1;    // Set the player's initial location
		keysFound.add("02");  //Give the player the key to the front door of the house
		
		monsterLocation = 9; //Set the monster's initial location
		
		keepPlaying = true; 
		
		System.out.println("Welcome to my text game.");
		System.out.println("Are you ready to begin? (y,n): ");
		myScanner.nextLine();
		
	}
	
	public void mapSetUp(){		
		
		try{
			//File mapFile = new File ("C:\\dandaWorkspace\\PracticeMyJavaSkills\\src\\myPracticePackage\\leawoodMap.txt");
			File mapFile = new File ("C:\\Users\\Matt\\eclipse-workspace\\Matts Text Game\\src\\mattsTextGame\\LeawoodHouseMap.txt");
			BufferedReader b = new BufferedReader(new FileReader(mapFile));
			String readline = "";
			int n=6; //number of parameters in each row, there's probably a way to use tokenizer to get it from readline 
			int nodeRow=0; //node number, also the row number
									
			while ((readline=b.readLine())!= null){
				//System.out.println(readline);  //For debugging purposes
				StringTokenizer tokenizer = new StringTokenizer(readline, ",");				
				
				for (int i=0;i<n;i++){					
					String token = tokenizer.nextToken();					
					map[nodeRow][i] = token;  //Populate array
					//System.out.println("(" + nodeRow + "," + i + ") = " + map[nodeRow][i]); //For debugging purposes
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
		
		// TO DO: Figure out how to store keys in nodes. Possibly create a new text file called LeawoodHouseKeyLocations.txt
		// Syntax of new file:
		// nodeKeyResides, nodeKeyOpens, isKeyAvailable (true, false)
		// Doors in Leawood map: 2, 21, 23, 24, 28, 30, 34 
		
	}
	
	public void printStatus(){		
		System.out.println(map[userLocation][descriptionNode] + " (" + userLocation + ", " + map[userLocation][4] +")");
		System.out.println("The dog is in the " + map[monsterLocation][nameNode] + " (" + monsterLocation + ")");
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
				System.out.println("Valid commands are: n,s,e,w,l,q");
			}	
			
			// Check if the new location has any keys -- possibly move to new method
			
			// TO DO: If the node contains a key, verify that the key does not already exist in keysFound list
			// TO DO: Add key to keysFound, notify player
			
		}
		
		// This code runs after keepPlaying is set to false
		System.out.println("--Quit!--");		
	}	
	
	public boolean isDoorUnlocked(int nodeNumber) {
		if (map[nodeNumber][nameNode].equals("doorway")){
			for (String i : keysFound) {
				if (nodeNumber == Integer.parseInt(i)) {
					System.out.println("The door is locked, but you have a key!");
					return true;  // User has the key
				}
			}
			System.out.println("The door is locked. You don't have a key.");
			return false; // The door is locked and the above loop did not find the key in the list
		}
		return true;  // The node isn't a door, so it's unlocked by default
	}
}
