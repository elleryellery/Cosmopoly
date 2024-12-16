package Structure;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Elements.DataCache;
import Elements.GameData;
import Elements.GraphicsDatabase;

public class Player {
    private int balance;
    private boolean inJail;
    private int numGetOutOfJailCards;
    private int tile = 0;
    private ArrayList<Tile> properties = new ArrayList<Tile>();
    private String model;

    public Player(String model){
        balance = 1500;
        this.model = model;
    }

    public void pay(int amount){
        balance -= amount;
    }

    public void collect(int amount){
        balance += amount;
    }

    public void goToJail(){
        if(numGetOutOfJailCards == 0){
            inJail = true;
            setTile("In Jail");
            Game.setScreen(GraphicsDatabase.S05);
        } else {
            numGetOutOfJailCards --;
        }
    }

    public int tile(){
        return tile;
    }

    public ArrayList<Tile> properties(){
        return properties;
    }

    public int balance(){
        return balance;
    }

    public void move(){
        tile ++;
    }

    public void setTile(int tile){
        this.tile = tile;
    }

    public void setTile(String tile){
        for(int i = 0; i < GameData.GameTiles.length; i++){
            if(GameData.GameTiles[i].name().equals(tile)){
                DataCache.currentPlayer.setTile(i);
            }
        }
    }

    public void advanceToNearestRailroad(){
        for(int i = DataCache.currentPlayer.tile(); i < GameData.GameTiles.length; i++){
            if(GameData.GameTiles[i] instanceof Railroad){
                setTile(i);
            }
            if(i >= GameData.GameTiles.length -1){
                i = 0;
            }
        }
    }

    public void addGetOutOfJailFreeCard(){
        numGetOutOfJailCards ++;
    }

    public void payTo(int amount, Player player){
        balance -= amount;
        player.collect(amount);
    }

    public void payEach(int amount){
        for(Player p: DataCache.players){
            payTo(amount, p);
        }
    }

    public void moveBack(int numSpaces){
        tile -= numSpaces;
        if(tile < 0){
            tile += GameData.GameTiles.length;
        }
    }

    public void releaseFromJail(){
        inJail = false;
        setTile("Just Visiting");
    }

    public Image getModel(){
        return new ImageIcon("Cosmopoly-Assets\\B\\" + model + ".png").getImage();
    }
}
