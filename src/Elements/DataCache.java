package Elements;

import java.awt.Color;
import java.util.ArrayList;

import Structure.*;

public class DataCache {
    public static Screen myScreen;
    public static boolean debug = false;
    public static boolean inFrame = true;

    public static ArrayList<Player> players = new ArrayList<Player>();
    public static Player currentPlayer;
    private static int playerIndex;

    public static boolean moving = false;
    public static int target;
    public static int roll;
    public static boolean activePopup = false;
    public static boolean rolling = false;

    public static long timer;


    public static JailRoom currentRoom = GraphicsDatabase.cell;
    public static JailRoom guardedRoom;
    public static int mouseX;
    public static int mouseY;

    public static Property selectedProperty = (Property)GameData.GameTiles[1];
    
    public static void init(){
        players.add(new Player("B02"));
        players.add(new Player("B03"));

        playerIndex = 0;
        currentPlayer = players.get(playerIndex);
    }

    public static void changeTurns(){
        if(currentPlayer == players.getLast()){
            currentPlayer = players.getFirst();
            playerIndex = 0;
        } else {
            playerIndex ++;
            currentPlayer = players.get(playerIndex);
        }
    }

    public static boolean myTurn(){
        return currentPlayer == players.getFirst();
    }

    public static void resetJail(){
        DataCache.currentRoom = GraphicsDatabase.cell;
    }
}
