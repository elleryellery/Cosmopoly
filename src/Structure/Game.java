package Structure;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.*; 

import Elements.*;

public class Game  extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{
	
	private BufferedImage back; 
	private static Graphics g2d;
	private static Runnable popupScript;
	private static Button[] popupButtons = {};

	public Game() {
		
		//Create a new thread that handles input from the keyboard and mouse
		new Thread(this).start();	
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		DataCache.init();
		GraphicsDatabase.init();
		DataCache.currentRoom = GraphicsDatabase.cell;
		GameData.shuffleChanceCards();
		GameData.shuffleCommunityChestCards();

		DataCache.myScreen = GraphicsDatabase.S05;			
	}

	//Run the thread
	public void run(){
	   	try{
	   		while(true){
	   		   Thread.currentThread().sleep(5);
	        	repaint();
	        }
	    } catch(Exception e) {
	   			
	    }
	}
	
	public void paint(Graphics g){
		
		//Graphics setup
		Graphics2D twoDgraph = (Graphics2D) g; 
		if(back ==null)
			back=(BufferedImage)( (createImage(getWidth(), getHeight()))); 
				
		g2d = back.createGraphics();
		g2d.clearRect(0,0,getSize().width, getSize().height);
		g2d.setFont(new Font("Times New Roman", Font.BOLD, 20));
		g2d.setColor(Color.GREEN);
		((Graphics2D) g2d).setStroke(new BasicStroke(10));
		
		DataCache.myScreen.drawScreen(g2d, getWidth(), getHeight());
		if(popupScript != null){
			popupScript.run();
		}

		twoDgraph.drawImage(back, null, 0, 0);
	}

	public static Graphics Graphics(){
		return g2d;
	}

	public static void setScreen(Screen _screen){
		DataCache.myScreen = _screen;
	}

	public static void popup(Runnable script, Button[] buttons){
		DataCache.activePopup = true;
		popupButtons = buttons;
		popupScript = () -> {
			g2d.drawImage(new ImageIcon("Cosmopoly-Assets\\M\\Dim.png").getImage(), 0, 0, null);
			script.run();
			for(Button b: popupButtons){
				b.drawButton();
			}
		};
	}

	public static void closePopup(){
		popupButtons = new Button[] {};
		popupScript = null;
		DataCache.activePopup = false;
	}


	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key= e.getKeyCode();
		char keyChar = e.getKeyChar();	

		if(keyChar == '$'){
			for(Tile g: GameData.GameTiles){
				if(g instanceof Property){
					DataCache.currentPlayer.properties().add(g);
				}
			}
		}

		if(keyChar == '~'){
			DataCache.debug = !DataCache.debug;
		}

		if(DataCache.debug){
			int num = 0;
			switch(keyChar){
				case '1':
					num = 1;
					break;
				case '2':
					num = 2;
					break;
				case '3':
					num = 3;
					break;
				case '4':
					num = 4;
					break;
				case '5':
					num = 5;
					break;
				case '6':
					num = 6;
					break;
			}
			if(num > 0){
				Scripts.roll(num);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
	
	@Override
	public void mouseDragged(MouseEvent e) {

	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(!DataCache.activePopup){
			for(Button b: DataCache.myScreen.buttons()){
				b.checkHover(e.getX(), e.getY());
			}
		}
		for(Button b: popupButtons){
			b.checkHover(e.getX(), e.getY());
		}
		if(DataCache.myScreen == GraphicsDatabase.S05){
			DataCache.mouseX = -1;
			DataCache.mouseY = -1;
			for(JailRoom r: DataCache.currentRoom.connectedRooms()){
				if(r.hover(e.getX(), e.getY())){
					DataCache.mouseX = e.getX();
					DataCache.mouseY = e.getY();
					break;
				}
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		DataCache.inFrame = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		DataCache.inFrame = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!DataCache.activePopup){
			for(Button b: DataCache.myScreen.buttons()){
				if(b.check(e.getX(), e.getY())){
					break;
				}
			}
		}
		for(Button b: popupButtons){
			if(b.check(e.getX(), e.getY())){
				break;
			}
		}
		for(int i = DataCache.myScreen.buttons().length - 1; i >= 0; i--){
			Button b = DataCache.myScreen.buttons()[i];
			b.checkHover(e.getX(), e.getY());
		}
		if(DataCache.debug){
			System.out.println("DEBUG: Mouse coordinates: (" + e.getX() + ", " + e.getY() + ")");
		}

		if(DataCache.myScreen == GraphicsDatabase.S05 && DataCache.timer < 0){
			for(JailRoom r: DataCache.currentRoom.connectedRooms()){
				if(r.hover(e.getX(), e.getY())){
					DataCache.currentRoom = r;
					break;
				}
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}
}
