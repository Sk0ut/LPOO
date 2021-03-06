package maze.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import maze.logic.Game;

public class MainMenuPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static class Options{
		private int rows;
		private int cols;
		private int numberDragons;
		private boolean dragonMove;
		private boolean dragonSleep;
		private boolean dragonAttack;
		
		public Options(){
			this.setRows(15);
			this.setCols(19);
			this.setNumberDragons(3);
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
	private Options options;
	private JFrame parentFrame;
	private AssignedKeys assignedKeys;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null); // see javadoc for more info on the parameters            
	}

	public MainMenuPanel(JFrame parFrame) {
		this.parentFrame = parFrame;
		try{
			background = ImageIO.read(ClassLoader.getSystemResource("images/Main Menu Background.png"));
		}catch(IOException e){
			JOptionPane.showMessageDialog(null, "Erro a fazer load de ficheiros", "Oops!", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		this.options = new Options();
		this.assignedKeys = new AssignedKeys();
		repaint();
		setLayout(new BorderLayout(0, 0));
		this.setSize(background.getWidth()/4, background.getHeight());
		
		setOpaque(false);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setBounds(3*background.getWidth()/4, 0, background.getWidth()/4, background.getHeight());
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setBorder(new EmptyBorder(300,0,100,60));
		add(buttonPanel, BorderLayout.EAST);
		
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.setSize(100, 100);		
		buttonPanel.add(btnNewGame);
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game game = new Game(options.getRows(),options.getCols(),options.getNumberDragons(),options.isDragonMove(),options.isDragonSleep(),options.isDragonAttack());
				GameGui gameFrame = new GameGui(parentFrame, game, assignedKeys);
				gameFrame.setVisible(true);
				parentFrame.setVisible(false);
			}
		});
		
		
		JButton btnLoadGame = new JButton("Load Game");
		buttonPanel.add(btnLoadGame);
		btnLoadGame.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Illuminati MLG Files", "mlg");
				chooser.setFileFilter(filter);
				Game game = null;
				if(chooser.showOpenDialog(MainMenuPanel.this) == JFileChooser.APPROVE_OPTION){
					ObjectInputStream is = null;
					try{
						is = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile()));
						game = (Game) is.readObject();
					}catch(IOException | ClassNotFoundException o){
						o.printStackTrace();
						JOptionPane.showMessageDialog(MainMenuPanel.this, "Failed to load file!", "Error" ,JOptionPane.ERROR_MESSAGE);
					}
					finally{ if (is != null)
						try {
							is.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}}
				}else{
					return;
				}
				
				if(game == null)
					return;
				
				GameGui gameGui = new GameGui(parentFrame, game, assignedKeys);
				parentFrame.setVisible(false);
				gameGui.setVisible(true);
					
			}
			
		});
		
		JButton btnCreativeMode = new JButton("Creative Mode");
		buttonPanel.add(btnCreativeMode);
		btnCreativeMode.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CreativeModeFrame creative = new CreativeModeFrame(parentFrame, options, assignedKeys);
				parentFrame.setVisible(false);
				creative.setVisible(true);
 			}
			
		});
		
		JButton btnOptions = new JButton("Options");
		buttonPanel.add(btnOptions);
		btnOptions.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				OptionsMenu optMenu = new OptionsMenu(options, assignedKeys);
				JOptionPane.showMessageDialog(null, optMenu,"Options",JOptionPane.PLAIN_MESSAGE);
			}
			
		});
				
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int option = JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?", "Warning!" , JOptionPane.OK_CANCEL_OPTION);
				if(option == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		buttonPanel.add(btnQuit);

	
		
		
		
	}
}
