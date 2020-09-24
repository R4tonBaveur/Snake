package snake;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class game {

	public static void main(String[] args) {
		JFrame window = new JFrame();
		ImageIcon img = new ImageIcon("C:\\Users\\matth\\Pictures\\logo_snake.png");
		window.setIconImage(img.getImage());
		window.setTitle("snake");
		window.setSize(550,570);
		window.add(new gameplay());
		window.setResizable(true);
		window.setVisible(true);
		window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);

	}

}