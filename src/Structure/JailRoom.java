package Structure;

import javax.swing.ImageIcon;
import java.awt.Rectangle;

public class JailRoom {
    private String tag;
    private Rectangle area;
    private JailRoom[] connectedRooms;

    public JailRoom(String tag, int x1, int y1, int x2, int y2){
        this.tag = tag;
        area = new Rectangle(x1, y1, x2-x1, y2-y1);
    }

    public Rectangle area(){
        return area;
    }

    public String tag(){
        return tag;
    }

    public boolean hover(int mouseX, int mouseY){ 
        Rectangle mouse = new Rectangle(mouseX,mouseY,1,1);
        return mouse.intersects(area);
    }

    public JailRoom[] connectedRooms(){
        return connectedRooms;
    }

    public void setConnectedRooms(JailRoom[] rooms){
        connectedRooms = rooms;
    }
}
