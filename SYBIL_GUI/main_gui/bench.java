package main_gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class bench {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(600, 600));
		frame.add(new Load_Panel());
		frame.setVisible(true);
	}

}
