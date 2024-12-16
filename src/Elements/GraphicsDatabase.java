package Elements;

import Structure.*;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;

public class GraphicsDatabase {
    public static Button B01, B02, B03, B04, B05, B09, B10;
    public static ConditionalButton C01, C02;
    public static Screen S01, S02, S03, S04, S05, S06;
    public static JailRoom cell, yard, library, kitchen, control, cafeteria, bathrooms, exit;

    public static void init(){
        cell = new JailRoom("Jail Cell", 439, 436, 707, 572);
        yard = new JailRoom("Prison Yard", 863, 289, 1050, 571);
        kitchen = new JailRoom("Kitchen", 62, 289, 298, 442);
        cafeteria = new JailRoom("Cafeteria", 336, 213, 625, 348);
        control = new JailRoom("Control Room", 654, 136, 806, 269);
        library = new JailRoom("Library", 859, 113, 1082, 256);
        bathrooms = new JailRoom("Bathrooms", 96, 60, 304, 235);
        exit = new JailRoom("Exit", 496, 22, 663, 108);


        JailRoom[] c = {yard, kitchen};
        cell.setConnectedRooms(c);
        JailRoom[] py = {cell, control, library, exit};
        yard.setConnectedRooms(py);
        JailRoom[] k = {cell, cafeteria, bathrooms};
        kitchen.setConnectedRooms(k);
        JailRoom[] cf = {kitchen, control};
        cafeteria.setConnectedRooms(cf);
        JailRoom[] ctrl = {yard, cafeteria, library, exit};
        control.setConnectedRooms(ctrl);
        JailRoom[] l = {control, yard, exit};
        library.setConnectedRooms(l);
        JailRoom[] br = {kitchen, exit};
        bathrooms.setConnectedRooms(br);

        JailRoom[] randRooms = {yard, library, kitchen, control, cafeteria, bathrooms};

        B01 = new Button("B01", 440, 380, 350, 100, () -> {
            Game.setScreen(S03);
        });
        B02 = new Button("B02",31, 134, 339, 281, () -> {

        });
        B03 = new Button("B03",231, 134, 349, 344, () -> {

        });
        B04 = new Button("B04",531, 134, 216, 421, () -> {

        });
        B05 = new Button("B05",731, 134, 466, 329, () -> {

        });
        B09 = new Button("B09", 1105, 0, 74, 650, () -> {
            Game.setScreen(S03);
        });
        B10 = new Button("B08", 472, 335, 250, 75, () -> {
            Game.setScreen(S03);
            DataCache.currentPlayer.releaseFromJail();
        });
        C01 = new ConditionalButton("C01",681, 311, 70, 70, () -> (DataCache.rolling), () -> {}, true);
        C01.setAction(() -> {
            if(!DataCache.rolling && !DataCache.moving){
                DataCache.rolling = true;
                DataCache.timer = System.currentTimeMillis();
                C01.setW(140);
                C01.setH(140);
                C01.setX(C01.x()-35);
                C01.setY(C01.y()-35);
            }

        });
        C02 = new ConditionalButton("C02", 926, 15, 200, 200, () -> (DataCache.players.getFirst().properties().size() == 0), () -> {
            Game.setScreen(S04);
            ArrayList<Button> b = new ArrayList<Button> ();

            int x = 20;
            int y = 15;
            int w = 71;
            int h = 167;

            int spacing = 20;

            for(Tile p: DataCache.currentPlayer.properties()){
                System.out.println(p.name());
                Button button = new Button(p.name(), x, y, w, h, () -> {
                    System.out.println(p.name());
                });
                button.buttonInCard();
                b.add(button);
                x += w + 20;
                if(x + w + 25 > 1200){
                    y += h + spacing;
                    x = 20;
                }
            }
            b.add(B09);
            int n = b.size();
            Button[] button = new Button[n];
            for(int i = 0; i < n; i++){
                button[i] = b.get(i);
            }
            S04.addButtons(button);
        });

        S01 = new Screen("S01");
            Button[] BS01 = {B01};
            S01.addButtons(BS01);
            S01.overrideImage("S01.gif");
        S02 = new Screen("S02");
            Button[] BS02 = {B02, B03, B04, B05};
            S02.addButtons(BS02);
        S03 = new Screen("S03");
            Button[] BS03 = {C01, C02};
            S03.addButtons(BS03);
            S03.addScript(() -> {
                Scripts.drawBoard();
                if(DataCache.debug){
                    Scripts.drawNodes();
                }

                if(!DataCache.myTurn() && !DataCache.rolling && !DataCache.moving && !DataCache.activePopup){
                    DataCache.rolling = true;
                    DataCache.timer = System.currentTimeMillis();
                    C01.setW(140);
                    C01.setH(140);
                    C01.setX(C01.x()-35);
                    C01.setY(C01.y()-35);
                }

                if(DataCache.rolling && System.currentTimeMillis() -DataCache.timer > 1500){
                    Scripts.roll(0);
                    C01.setW(70);
                    C01.setH(70);
                    C01.setX(C01.x()+35);
                    C01.setY(C01.y()+35);
                }

                if(DataCache.moving){
                    Scripts.movePlayerToTarget();
                }
            });
        S04 = new Screen("S04");
            Button[] BS04 = {};
            S04.addButtons(BS04);
            S04.addScript(() -> {

            });

        S05 = new Screen("S05");
            S05.addScript(() -> {
                if(DataCache.guardedRoom == null){
                    int rand = (int)(Math.random() * (randRooms.length));
                    DataCache.guardedRoom = randRooms[rand];
                }

                if(DataCache.currentRoom == DataCache.guardedRoom){
                    if(DataCache.timer < 0) {
                        DataCache.timer = System.currentTimeMillis();
                    }
                    S05.overrideImage(DataCache.currentRoom.tag() + "_.png");
                    Game.Graphics().drawImage(new ImageIcon("Cosmopoly-Assets\\M\\Caught.png").getImage(), 300, 70, 500, 500, null);
                    double caughtTimer = 2;
                    if(System.currentTimeMillis() - DataCache.timer >= caughtTimer*1000){
                        Game.setScreen(S03);
                        DataCache.timer = -1;
                    }
                } else {
                    if(DataCache.currentRoom == exit){
                        Game.setScreen(S06);
                    } else {
                        DataCache.timer = -1;
                        S05.overrideImage(DataCache.currentRoom.tag() + ".png");
                    }
                }

                Scripts.drawHoverMouse();
            });
        
        S06 = new Screen("S06");
            Button[] BS06 = {B10};
            S06.addButtons(BS06);
    }
}
//1105