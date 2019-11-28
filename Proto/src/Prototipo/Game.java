package Prototipo;

import javax.swing.JFrame;

public class Game {

	public static void main(String[] args) {
		JFrame windows = new JFrame("Juego Plataforma");
		windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		windows.setContentPane(new GamePanel());
		windows.pack();
		windows.setVisible(true);
	}
}
