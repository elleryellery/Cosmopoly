package Structure;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;

import Elements.*;

public class Chance extends Tile{
    private TextInterpreter t = new TextInterpreter();
    public Chance(int x, int y){
        super(x, y, "Chance");
        Button[] continueButton = {
            new Button("B08", 680, 250, 250, 75, () -> {
                GameData.chanceDeck.peekLast().doAction();
                GameData.chanceDeck.pop();
                if(GameData.chanceDeck.size() == 0){
                    GameData.shuffleChanceCards();
                }
                Game.closePopup();
                DataCache.changeTurns();
            })
        };
        this.setImplication(() -> {
            Game.popup(() -> {
                Game.Graphics().drawImage(new ImageIcon("Cosmopoly-Assets\\M\\" + "Chance" + ".png").getImage(), 47, 20, 500, 500, null);
                Game.Graphics().setColor(Color.BLACK);
                Game.Graphics().setFont(new Font("Arial", Font.BOLD, 17));
                t.drawText(Game.Graphics(),GameData.chanceDeck.peekLast().description(), 231, 214, 30, 15);
            }, continueButton);
        });
    }
}
