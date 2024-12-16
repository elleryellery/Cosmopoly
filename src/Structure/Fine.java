package Structure;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;

import Elements.DataCache;

public class Fine extends Tile{
    public Fine(int x, int y, String name){
        super(x, y, name);

        Button[] continueButton = {
            new Button("B08", 680, 250, 250, 75, () -> {
                DataCache.currentPlayer.pay(100);
                Game.closePopup();
                DataCache.changeTurns();
            })
        };
        
        this.setImplication(() -> {
            Game.popup(() -> {
                Game.Graphics().drawImage(new ImageIcon("Cosmopoly-Assets\\Card\\" + name + ".png").getImage(), 206, 63, 208, 470, null);
                Game.Graphics().setColor(Color.WHITE);
                Game.Graphics().setFont(new Font("Times New Roman", Font.BOLD, 40));
                Game.Graphics().drawString("Pay $100 for spaceship expenses.", 550, 150);
            }, continueButton);
        });
    }
}
