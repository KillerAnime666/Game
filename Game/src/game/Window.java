package game;

import javax.swing.JFrame;

public class Window extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public Window() {
		setTitle("My Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setContentPane(new GamePanel(/*width*/1080,/*height*/720));
		
		setIgnoreRepaint(true);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
