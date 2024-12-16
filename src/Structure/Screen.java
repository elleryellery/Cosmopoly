package Structure;
import java.awt.Graphics;
import javax.swing.ImageIcon;

import Elements.DataCache;

public class Screen {

	private ImageIcon back;
    private String tag;
    //private SoundPlayer sfx;
    private boolean startPlayer = true;
    private boolean includeInHistory = true;
    Sound[] backgroundSounds;
    Button[] buttons = {};
    //DragZone[] dragZones;
    Runnable script = () -> {};

    public Screen() {
        back = new ImageIcon("IMG-Screens/SCREEN-ImageNotFound.png");
        tag = "S00";
        script = () -> {
            System.out.println("Empty constructor.");
        };
	}

	public Screen(String _tag) {
        tag = _tag;
        back = new ImageIcon("Cosmopoly-Assets\\S\\" + tag + ".png");
	}

    public void drawScreen(Graphics g2d, int screenWidth, int screenHeight){ //Draws the screen's background image, features, and buttons and, if necessary, starts the background music
        g2d.drawImage(back.getImage(),0,0,screenWidth,screenHeight,null);
        script.run();
        if(buttons.length > 0){
            for(Button b: buttons){
                b.drawButton();
            }
        }
    }

    public void overrideImage(String fileName){
        back = new ImageIcon("Cosmopoly-Assets\\S\\" + fileName);
    }

    public void excludeFromHistory(){
        includeInHistory = false;
    }

    public boolean includeInHistory(){
        return includeInHistory;
    }
    
    public String tag() {
        return tag;
    }

    public ImageIcon back() {
        return back;
    }

    public void addButtons(Button[] _buttons){
        buttons = _buttons;
    }

    /*public void addDragZones(DragZone[] _dragZones){
        dragZones = _dragZones;
    }*/

    public void addScript(Runnable _script){
        script = _script;
    }

    public void setBack(ImageIcon _back){
        back = _back;
    }

    public void addBackgroundSounds(Sound[] _backgroundSounds){
        backgroundSounds = _backgroundSounds;
    }

    public Button[] buttons(){
        return buttons;
    }

    public Sound[] backgroundSounds(){
        return backgroundSounds;
    }

    public boolean startPlayer(){
        return startPlayer;
    }
    public void setStartPlayer(boolean _startPlayer){
        startPlayer = _startPlayer;
    }

    public String toString(){
        return tag;
    }

    public void rearrangeToLast(Button button){
        Button[] temp = new Button[buttons.length];
        int index = 0;
        for(int i = 0; i < buttons.length; i++){
            if(!(buttons[i] == button)){
                temp[index] = buttons[i];
                index ++;
            }
        }
        temp[buttons.length - 1] = button;
        buttons = temp;
    }
}


