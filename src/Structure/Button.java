package Structure;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import Elements.DataCache;

public class Button {
    
    private String tag;
    private int x,y,w,h;
    public ImageIcon icon = new ImageIcon("");
    private ImageIcon iconRegular;
    private ImageIcon iconHover;
    private Runnable action;

    public Button(){
        tag = "B00";
        x = 0;
        y = 0;
        w = 0;
        h = 0;
        icon = new ImageIcon("No Image");
    }

    public Button(String _tag, int _x, int _y, int _w, int _h, Runnable _action){
        tag = _tag;
        iconRegular = new ImageIcon("Cosmopoly-Assets\\B\\"+tag+".png");
        iconHover = new ImageIcon("Cosmopoly-Assets\\B\\"+tag+"_.png");
        icon = iconRegular;
        x = _x;
        y = _y;
        w = _w;
        h = _h;
        action = _action;
    }

    public void buttonInCard(){
        iconRegular = new ImageIcon("Cosmopoly-Assets\\Card\\"+tag+".png");
        iconHover = new ImageIcon("Cosmopoly-Assets\\Card\\"+tag+"_.png");
    }

    public void executeButtonAction(){
        action.run();
    }

    public void drawButton (){
        Game.Graphics().drawImage(icon.getImage(),x,y,w,h,null);
        if(DataCache.debug){
            Game.Graphics().drawRect(x, y, w, h);
        }
    }

    public boolean check(int mouseX, int mouseY){
        Rectangle mouse = new Rectangle(mouseX,mouseY,1,1);
        Rectangle me = new Rectangle(x,y,w,h);

        if(mouse.intersects(me)){
            executeButtonAction();
        }

        return mouse.intersects(me);
    }

    public void setRunnable(Runnable inputAction){
        action = inputAction;
    }
    public void setH(int inputH){
        h = inputH;
    }
    public void setW(int inputW){
        w = inputW;
    }
    public void addY(int inputY){
        y+=inputY;
    }
    public void checkHover(int mouseX, int mouseY){ //Changes the button's appearance if the user is hovering over the button
        Rectangle mouse = new Rectangle(mouseX,mouseY,1,1);
        Rectangle me = new Rectangle(x,y,w,h);

        if(mouse.intersects(me)){
            icon = iconHover;
        } else {
            icon = iconRegular;
        }
        
    }

    public int x(){
        return x;
    }
    public int y(){
        return y;
    }
    public int w(){
        return w;
    }
    public int h(){
        return h;
    }

    public String tag(){
        return tag;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void overrideImage(ImageIcon _iconRegular, ImageIcon _iconHover){
        iconRegular = _iconRegular;
        iconHover = _iconHover;
    }

    public void setAction(Runnable inputAction){
        action = inputAction;
    }
}
