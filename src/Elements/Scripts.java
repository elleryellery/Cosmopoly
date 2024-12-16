package Elements;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import Structure.Game;
import Structure.Player;
import Structure.Property;
import Structure.Tile;

public class Scripts {
    public static void roll(int rollNum){
        System.out.println(DataCache.target);
        DataCache.rolling = false;

        int roll;
        if(rollNum == 0){
            roll = (int)(Math.random()*6 + 1);
        } else {
            roll = rollNum;
        }

        DataCache.target = (DataCache.currentPlayer.tile() + roll)%GameData.GameTiles.length;

        for(int i = DataCache.currentPlayer.tile(); i < DataCache.target; i ++){
            if(GameData.GameTiles[i].exclude()){
                DataCache.target ++;
            }
        }

        GraphicsDatabase.C01.startAppearance(false);
        DataCache.moving = true;
        DataCache.timer = System.currentTimeMillis();

        DataCache.roll = roll;
        System.out.println("Roll: " + roll + "; Target: " + DataCache.target);
    }

    public static void movePlayerToTarget(){
        if(DataCache.moving){
            GraphicsDatabase.C01.setIcon(new ImageIcon("Cosmopoly-Assets\\M\\" + DataCache.roll + ".png"));
            if(System.currentTimeMillis()-DataCache.timer >= 500){
                if(DataCache.debug){
                    System.out.println("Target: " + DataCache.target + ", Tile: " + DataCache.currentPlayer.tile());
                }

                if((DataCache.target <= DataCache.currentPlayer.tile())&&(Math.abs(DataCache.target-DataCache.currentPlayer.tile())<=6)){
                    DataCache.moving = false;
                    GameData.GameTiles[DataCache.currentPlayer.tile()].runImplication();
                    GraphicsDatabase.C01.unsetIcon();
                    return;
                } else {
                    if(DataCache.currentPlayer.tile() < GameData.GameTiles.length -1){
                        DataCache.currentPlayer.move();
                    } else {
                        DataCache.currentPlayer.setTile(0);
                        DataCache.currentPlayer.collect(200);
                    }
                }

                if(GameData.GameTiles[DataCache.currentPlayer.tile()].exclude()){
                    DataCache.currentPlayer.move();
                    System.out.println("1");
                }

                DataCache.timer = System.currentTimeMillis();
            }
        }
    }

    public static void drawPlayers(){
        int offsetX = 0;
        int i = 0;
        Color[] colors ={
            Color.MAGENTA,
            Color.GREEN,
            Color.RED
        };
        for(Player p: DataCache.players){
            Tile t = GameData.GameTiles[p.tile()];
            //int w = 75;
            //int h = 75;
            Game.Graphics().setColor(colors[i]);
            Game.Graphics().drawOval(t.x()+offsetX - 1, t.y() - 1, 2, 2);
            offsetX +=5;
            i ++;
            //Game.Graphics().drawImage(p.getModel(), t.x() - w/2, t.y() - h/2, w, h, null);
        }
    }

    public static void drawBalance(){
        Game.Graphics().setColor(Color.WHITE);
        Game.Graphics().setFont(new Font("Times New Roman", Font.BOLD, 20));
        for(int i = 0; i < DataCache.players.size(); i++){
            Game.Graphics().drawString("Player " + (i+1) + " Balance: $" + DataCache.players.get(i).balance(), 25, 25*(i+2));
        }
        Game.Graphics().drawString("Player 2 Properties: " + DataCache.players.getLast().properties(), 25, 25);
    }

    public static void drawNodes(){
        for(Tile g: GameData.GameTiles){
            if(DataCache.currentPlayer.properties().contains(g)){
                Game.Graphics().setColor(Color.RED);
            } else if(!(g instanceof Property)){
                Game.Graphics().setColor(Color.BLUE);
            } else {
                Game.Graphics().setColor(Color.GREEN);
            }
            Game.Graphics().drawString(g.name(), g.x(), g.y());
            Game.Graphics().drawRect(g.x()-1, g.y()-1, 2,2);
        }
    }

    public static void drawBoard(){
        Game.Graphics().drawImage(new ImageIcon("Cosmopoly-Assets\\M\\Board.png").getImage(),0, 0, 1082, 600, null);
        drawBalance();
        drawPlayers();
    }

    public static void payLotTaxes(){
        int numHouses = 0;
        int numBases = 0;
        Player player = DataCache.currentPlayer;

        for(Tile pr: player.properties()){
            Property p = (Property)pr;
            if(p.hotel()){
                numBases ++;
            } else {
                numHouses += p.houses();
            }
        }

        player.collect(25*numHouses + 100*numBases);
    }

    public static void payEachPlayer(int amountPerPlayer){
        for(Player p: DataCache.players){
            DataCache.currentPlayer.payTo(amountPerPlayer, p);
        }
    }

    public static void collectFromEachPlayer(int amountPerPlayer){
        for(Player p: DataCache.players){
            DataCache.currentPlayer.collect(amountPerPlayer);
            p.pay(amountPerPlayer);
        }
    }

    public static void drawHoverMouse(){
        if(DataCache.mouseX > 0){
            int width = 100;
            int height = 100;
            Game.Graphics().drawImage(new ImageIcon("Cosmopoly-Assets\\M\\HoverMouse.png").getImage(), (int)(DataCache.mouseX - width/2), (int)(DataCache.mouseY - height/2), width, height, null);
        }
    }
}
