package Structure;

import java.awt.Color;
import java.awt.Font;

import Elements.DataCache;

import javax.swing.ImageIcon;

public class Property extends Tile {
    private int price;
    private String set;
    private int numHouses = 0;
    private boolean hotel = false;
    private int baseRent;

    public Property(int x, int y, String name, int price, String set, int baseRent){
        super(x, y, name);
        this.price = price;
        this.set = set;
        this.baseRent = baseRent;

        Button[] purchaseButtons = {
            new Button("B06", 630, 250, 250, 75, () -> {
                DataCache.currentPlayer.pay(price);
                DataCache.currentPlayer.properties().add(this);
                Game.closePopup();
                DataCache.changeTurns();

            }),
            new Button("B07", 630, 350, 250, 75, () -> {
                Game.closePopup();
                DataCache.changeTurns();
            })
        };

        Button[] payRentButton = {
            new Button("B08", 630, 350, 250, 75, () ->{
                DataCache.currentPlayer.payTo(rent(), isOwned());
                DataCache.changeTurns();
                Game.closePopup();
            })
        };
        this.setImplication(() -> {
            double chanceForOpponentToBuy = 0.5;
            if(DataCache.currentPlayer == DataCache.players.getFirst() && isOwned() == null){
                Game.popup(() -> {
                    Game.Graphics().drawImage(new ImageIcon("Cosmopoly-Assets\\Card\\" + name + ".png").getImage(), 206, 63, 208, 470, null);
                    Game.Graphics().setColor(Color.WHITE);
                    Game.Graphics().setFont(new Font("Times New Roman", Font.BOLD, 40));
                    Game.Graphics().drawString("Buy " + name + " for $" + price + "?", 550, 150);
                }, purchaseButtons);
            } else if(DataCache.currentPlayer == DataCache.players.getFirst() && isOwned() != null && isOwned() != DataCache.players.getFirst()){
                Game.popup(() -> {
                    Game.Graphics().drawImage(new ImageIcon("Cosmopoly-Assets\\Card\\" + name + ".png").getImage(), 206, 63, 208, 470, null);
                    Game.Graphics().setColor(Color.WHITE);
                    Game.Graphics().setFont(new Font("Times New Roman", Font.BOLD, 40));
                    Game.Graphics().drawString("This property is owned by somebody else -- pay up! $" + rent(), 550, 150);
                }, payRentButton);
            } else if(isOwned() != null && isOwned() != DataCache.currentPlayer){
                DataCache.currentPlayer.payTo(rent(), isOwned());
                System.out.println("Darn, you got me! Paying $" + rent());
                DataCache.changeTurns();
            } else if(isOwned() == null){
                if(Math.random() < chanceForOpponentToBuy){
                    DataCache.currentPlayer.pay(price);
                    DataCache.currentPlayer.properties().add(this);
                } else {
                    System.out.println("No thanks!");
                }
                DataCache.changeTurns();
            } else {
                DataCache.changeTurns();
            }
        });
    }

    public void buyHouse(){
        if(!canBuyHotel()){
            numHouses ++;
        }
    }

    public boolean canBuyHotel(){
        return numHouses == 4;
    }

    public Player isOwned(){
        for(Player p: DataCache.players){
            if(p.properties().contains(this)){
                return p;
            }
        }
        return null;
    }

    public boolean colorSetOwned(){
        return true;
    }

    public double getMultiplier(double m){
        if(hotel){
            m *= 70;
        } else if(numHouses > 0){
            switch(numHouses){
                case 1:
                    m *=5;
                    break;
                case 2:
                    m *=14;
                    break;
                case 3:
                    m *= 38;
                    break;
                case 4:
                    m *= 50;
                    break;
            }
        } else if(colorSetOwned()){
            m *= 2;
        }

        return m;
    }

    public int rentWith(int houses){
        if(houses < 0){ //Color set not owned
            return baseRent;
        } else if (houses == 0){
            return 2*baseRent;
        } else if(houses == 1){
            return baseRent*5;
        } else if(houses == 2){
            return baseRent*14;
        } else if(houses == 3){
            return baseRent*38;
        } else if(houses == 4){
            return baseRent*50;
        } else if(houses ==5){
            return baseRent*70;
        }
        return 0;
    }

    public int rent(){
        return (int)(baseRent*getMultiplier(1));
    }

    public int rent(double multiplier){
        return (int)(baseRent*getMultiplier(multiplier));
    }

    public boolean hotel(){
        return hotel;
    }

    public int houses(){
        return numHouses;
    }

}
