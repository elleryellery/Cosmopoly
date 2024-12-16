import javax.swing.*;

import Structure.Game;

import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame{
	private static final int WIDTH =1200;
	private static final int HEIGHT=650;
	private static Game play;
	
	public Main () {
		super("Monopery");
		setSize(WIDTH, HEIGHT);

		play = new Game();
		((Component) play).setFocusable(true);
		
		setBackground(Color.BLACK);
		
		getContentPane().add(play);
		
		setVisible(true);
		
		addWindowListener( new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {

			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {

			}  
		});
	}

	public static void main(String[] args) {
		Main run = new Main();
	}

	public static Game play(){
		return play;
	}
}
