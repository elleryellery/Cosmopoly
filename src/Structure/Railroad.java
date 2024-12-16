package Structure;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;

import Elements.DataCache;

public class Railroad extends Property{
    public Railroad(int x, int y, String name){
        super(x, y, name, 200, "Railroad", 25);

        Button[] purchaseButtons = {
            new Button("B06", 630, 250, 250, 75, () -> {
                DataCache.currentPlayer.pay(200);
                DataCache.currentPlayer.properties().add(this);
                Game.closePopup();
                DataCache.changeTurns();
            }),
            new Button("B07", 630, 350, 250, 75, () -> {
                Game.closePopup();
                DataCache.changeTurns();
            })
        };
        this.setImplication(() -> {
            Game.popup(() -> {
                Game.Graphics().drawImage(new ImageIcon("Cosmopoly-Assets\\Card\\" + name + ".png").getImage(), 206, 63, 208, 470, null);
                Game.Graphics().setColor(Color.WHITE);
                Game.Graphics().setFont(new Font("Times New Roman", Font.BOLD, 40));
                Game.Graphics().drawString("Buy " + name + " for $" + 200 + "?", 550, 150);
            }, purchaseButtons);
        });
    }
}
