package com.game;

import javax.swing.JFrame;

public class Start {
	
	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame("2048");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(game);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
	}

}
