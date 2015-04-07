package maze.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenuPanel extends JPanel {

	public static class Options{
		private int rows;
		private int cols;
		private int numberDragons;
		private boolean dragonMove;
		private boolean dragonSleep;
		private boolean dragonAttack;
		
		public Options(){
			this.setRows(10);
			this.setCols(10);
			this.setNumberDragons(2);
			this.setDragonMove(true);
			this.setDragonSleep(true);
			this.setDragonAttack(true);
		}

		public int getCols() {
			return cols;
		}

		public void setCols(int cols) {
			this.cols = cols;
		}

		public int getRows() {
			return rows;
		}

		public void setRows(int rows) {
			this.rows = rows;
		}

		public int getNumberDragons() {
			return numberDragons;
		}

		public void setNumberDragons(int numberDragons) {
			this.numberDragons = numberDragons;
		}

		public boolean isDragonMove() {
			return dragonMove;
		}

		public void setDragonMove(boolean dragonMove) {
			this.dragonMove = dragonMove;
		}

		public boolean isDragonSleep() {
			return dragonSleep;
		}

		public void setDragonSleep(boolean dragonSleep) {
			this.dragonSleep = dragonSleep;
		}

		public boolean isDragonAttack() {
			return dragonAttack;
		}

		public void setDragonAttack(boolean dragonAttack) {
			this.dragonAttack = dragonAttack;
		}
		
		public Options clone(){
			Options opt = new Options();
			opt.rows = this.rows;
			opt.cols = this.cols;
			opt.numberDragons = this.numberDragons;
			opt.dragonMove = this.dragonMove;
			opt.dragonSleep = this.dragonSleep;
			opt.dragonAttack = this.dragonAttack;
			return opt;
		}
	}
	
	BufferedImage background;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null); // see javadoc for more info on the parameters            
	}

	public MainMenuPanel() {
		
		try{
			background = ImageIO.read(new File("src1/images/Main Menu Background.png"));
		}catch(IOException e){
			JOptionPane.showMessageDialog(null, "Erro a fazer load de ficheiros", "Oops!", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		repaint();
		setLayout(new BorderLayout(0, 0));
		this.setSize(background.getWidth()/4, background.getHeight());
		
		setOpaque(false);
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setBounds(3*background.getWidth()/4, 0, background.getWidth()/4, background.getHeight());
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		panel_1.setBorder(new EmptyBorder(300,0,100,60));
		add(panel_1, BorderLayout.EAST);
		
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.setSize(100, 100);
		
		panel_1.add(btnNewGame);
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Game game = new Game();
			}
		});
		
		
		JButton btnLoadGame = new JButton("Load Game");
		panel_1.add(btnLoadGame);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int option = JOptionPane.showConfirmDialog(null,"Warning!","Are you sure you want to exit?" , JOptionPane.OK_CANCEL_OPTION);
				if(option == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		panel_1.add(btnQuit);

		JButton btnOptions = new JButton("Options");
		//TODO adicionar menu opções
		
		
		
	}
}