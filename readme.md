# Leawood Text Game
This is a Java-based console application for creating text-based games. Unlike many text games, the gameplay is *NOT* hard-coded, instead, this engine inputs the map data from text files. As a result, you can create an unlimited amount of maps for use with this engine. 

## Input Files
This game requires two input files, one for the map and another for the key locations. The game data is stored in arrays (and not as objects!) for simplicity.  

### Map File
The Map file defines each node that the user can traverse. Each row in the text file defines a node. The node is identified by its row number. The syntax is:  
`nodeToNorth,nodeToSouth,nodeToEast,nodeToWest,nodeName,nodeDescription,`  

If there is a wall or barrier, use `00` to indicate that the user cannot move that direction.  

The first row in the file is row 0, which is a header that the system ignores.  

For example, the following file begins with a header at row 0 and describes four nodes (1, 2, 3, and 4):  
`00,00,00,00,nodeName,nodeDescription,`  
`00,03,02,00,NW,You are in the north-west corner.,`  
`00,04,00,01,NE,You are in the north-east corner.,`  
`01,00,04,00,SW,You are in the south-west corner.,`  
`02,00,00,03,SE,You are in the south-east corner.,`  


### KeyLocations file
The KeyLocations file identifies which nodes contain keys. The syntax is:  
`nodeKeyOpens, nodeKeyResides, isKeyAvailable (1/0),`  
For example:  
`21,19,1,`  
This key opens the door at node #21 and the player finds the key in node #19. The final parameter, `isKeyAvailable` indicates whether the user has found the key. `1` indicates that the key is still on the map, and `0` indicates that the user has collected the key and it is no longer found on the map.
