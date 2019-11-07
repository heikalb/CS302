import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/** 
 * Implements the main game GUI
 * 
 * Students should not edit or submit this file.
 * 
 * @author schulzca, matthewb
 * 
 */
@SuppressWarnings("serial")
public class Gui extends JFrame
{
	/**
	 * The CantStop game that this GUI displays.
	 */
	private final CantStop cantStop;

	/**
	 * Dimensions for graphics
	 */
	private final static int SLOT_WIDTH = 34;
	private final static int DOT_DIAMETER = 10;
	private final static int DISK_DIAMETER = 24;
	private final static int BORDER = 10;

	/**
	 * Game state info
	 */
	private static boolean gameOver;
	private static boolean didBust;
	private static int[] rolls = {0,0,0,0};
	private static boolean[] selectedDice = {false, false, false, false};
	
	/**
	 * Random
	 */
	@SuppressWarnings("unused")
	private static Random rnd = ( Util.RANDOM_SEED == -1 ? new Random() : new Random(Util.RANDOM_SEED));
	
	
	/**
	 * GUI components
	 */
	private JLabel pTurnLabel;
	private JLabel statusLabel;
	private JButton roll;
	private JButton submit;
	private JButton again;
	private JButton noMore;
	private BoardCanvas boardCanvas;
	private DiceCanvas diceCanvas;

	/**
	 * Initializes the GUI
	 * @param cs The CantStop object that this GUI will represent.
	 */
	@SuppressWarnings("static-access")
	public Gui(CantStop cs)
	{
		
		this.cantStop = cs;
		GameBoard board = cantStop.getGameBoard();
		if ( board == null ) {
			System.out.println("getGameBoard in CantStop returned null. "+
					"It must return a properly initialized GameBoard to continue. ");
		} 

		String title = "Can't Stop!";

		// Get players
		Player[] players = board.getPlayers();
		if(players == null){
			System.out.println("getPlayers in GameBoard returned null. "+
					"It must return an array of Player objects to continue.");
		}

		// CREATE COMPONENTS 
		
		// GAME BOARD
		JPanel mainPanel = new JPanel();

		int cHeight = 12 * SLOT_WIDTH + BORDER;
		int	cWidth = 11 * SLOT_WIDTH + BORDER;

		boardCanvas = new BoardCanvas();
		boardCanvas.setPreferredSize(new Dimension(cWidth, cHeight + 50));
		boardCanvas.setBorder(BorderFactory.createRaisedBevelBorder());

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(boardCanvas, BorderLayout.NORTH);
		
		// TEXT INFORMATION
		pTurnLabel = new JLabel();
		statusLabel = new JLabel();
		JPanel south = new JPanel();
		
		GridLayout gLayout = new GridLayout(2,1);
		gLayout.setVgap(5);
		south.setLayout(gLayout);
		south.add(pTurnLabel);
		south.add(statusLabel);
		south.setPreferredSize(new Dimension(cWidth, 30));

		mainPanel.add(south, BorderLayout.SOUTH);
		mainPanel.setBorder(new TitledBorder(title));

		// PLAYER INTERACTION PARTS
		JPanel center = new JPanel();
		JPanel northOfCenter = new JPanel();
		center.setLayout(new BorderLayout());
		
		//ROLL AND SUBMIT BUTTONS
		roll = new JButton("ROLL!");
		roll.setMaximumSize(new Dimension(50, 20));
		roll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < rolls.length; i++){
					rolls[i] = rnd.nextInt(6) + 1;
				}
	
				didBust = cantStop.getGameBoard().didPlayerBust(rolls);
				reactToRoll();
			}
		});
		roll.setEnabled(false);
		northOfCenter.add(roll);
		
		submit = new JButton("Lock in choice!");
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!didBust){
					int numSelected = 0;
					int sum = 0;
					for(int i = 0; i < selectedDice.length; i++){
						if(selectedDice[i]) {
							numSelected++;
							sum += rolls[i];
						}
					}
					
					if(numSelected != 2){
						statusLabel.setText("Must select two dice to use before locking in!\n ");
						update();
					} else {
						String report = cantStop.getGameBoard().isValidChoice(sum);
						if(report == null || report.equals("")){
							cantStop.getGameBoard().handleUserChoice(rolls, sum);
							allowContinueOrStop();
						} else {
							statusLabel.setText(report);
						}
					}
				} else {

					submit.setText("Lock in choice!");
					endTurn();
				}
				
				
			}
		});
		submit.setEnabled(false);
		northOfCenter.add(submit);
		center.add(northOfCenter, BorderLayout.NORTH);
		
		//DICE DISPLAY
		diceCanvas = new DiceCanvas();
		diceCanvas.setPreferredSize(new Dimension((SLOT_WIDTH)*8+6, (SLOT_WIDTH+3)*2));
		diceCanvas.setBorder(BorderFactory.createRaisedBevelBorder());
		center.add(diceCanvas, BorderLayout.CENTER);
		this.addMouseListener(diceCanvas);
		
		//CANT STOP BUTTONS
		JPanel southOfCenter = new JPanel();
		again = new JButton("Can't Stop!");
		again.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(gameOver){
					finishGame(true);
				} else {
					allowNewRoll();
				}
			}
		});
		again.setEnabled(false);
		southOfCenter.add(again);
		
		noMore = new JButton("I'm done.");
		noMore.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(gameOver){
					finishGame(false);
				} else {
					endTurn();
				}
			}
		});
		noMore.setEnabled(false);
		southOfCenter.add(noMore);
		center.add(southOfCenter, BorderLayout.SOUTH);
		
		mainPanel.add(center, BorderLayout.CENTER);

		this.add(mainPanel);
		this.setLocation(300, 300);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

		if (cWidth < 300)
		{
			int currW = (int) mainPanel.getPreferredSize().getWidth();
			int currH = (int) mainPanel.getPreferredSize().getHeight();
			mainPanel.setPreferredSize(new Dimension(currW + 150, currH));
		}
		this.setResizable(false);
		this.pack();
		this.setVisible(true);

	}
	
	/**
	 * Start the game. Only call once.
	 * New games should create new GUIs through Util.showNewGameDialog()
	 */
	public void start(){
		cantStop.getGameBoard().startNewTurn();
		allowNewRoll();
	}
	
	/**
	 * Set game state to: Waiting for user to roll the dice
	 */
	private void allowNewRoll(){
		didBust = false;
		gameOver = false;
		roll.setEnabled(true);
		submit.setEnabled(false);
		again.setEnabled(false);
		noMore.setEnabled(false);
		for(int i = 0; i < rolls.length; i++){
			rolls[i] = 0;
			selectedDice[i] = false;
		}
		statusLabel.setText("Roll the dice!");
		update();
	}
	
	/**
	 * Set game state to: Waiting for user to use their roll
	 */
	private void reactToRoll(){
		roll.setEnabled(false);
		submit.setEnabled(true);
		again.setEnabled(false);
		noMore.setEnabled(false);
		if(!didBust){
			statusLabel.setText("Choose your first dice pair!");
		} else {
			statusLabel.setText("Oh no! Better luck next time.");
			submit.setText("Oops!   Busted!");
		}
		update();
	}
	
	/**
	 * Set game state to: Waiting for user to decide if they "Can't Stop!"
	 */
	private void allowContinueOrStop(){
		roll.setEnabled(false);
		submit.setEnabled(false);
		again.setEnabled(true);
		noMore.setEnabled(true);
		statusLabel.setText("Feeling lucky?");
		update();
	}
	
	/**
	 * Ends the current turn. 
	 * If the game is over, set game state to: Wait for user to decide to play more or not
	 * If the game isn't over, set game state to: Wait for next players roll
	 */
	private void endTurn(){
    String currentPlayer = cantStop.getGameBoard().getCurrentPlayer().getName();
		gameOver = cantStop.getGameBoard().endTurn(didBust);
		
		if(gameOver){
			statusLabel.setText(currentPlayer + 
					" wins! Go again?");
			update();
		} else {
			cantStop.getGameBoard().startNewTurn();
			allowNewRoll();
		}
	}
	
	/**
	 * Closes the GUI and starts a new game if again == true 
	 * @param again true if another game will be played
	 */
	private void finishGame(boolean again){
		dispose();
		if(again) Util.showNewGameDialog("Choose player names:", cantStop.getGameBoard().getPlayers());
	}
	
	/**
	 * Update the entire gui
	 */
	public void update(){
		updateTurnLabel();
		updateCanvas();
	}
	
	/**
	 * Update the label that specifies the next player's turn
	 */
	private void updateTurnLabel()
	{
		Player currPlayer = cantStop.getGameBoard().getCurrentPlayer();
		pTurnLabel.setText( currPlayer.getName() + "'s turn.");
	}

	/**
	 * This method asks for the board and dice display to be redrawn when possible.
	 */
	private void updateCanvas()
	{
		boardCanvas.repaint();
		diceCanvas.repaint();
	}

	/**
	 * Encapsulates the component used to draw the board to the GUI.
	 * 
	 * @author schulzca, matthewb
	 *
	 */
	private class BoardCanvas extends JComponent
	{
		/**
		 * Constructor
		 */
		public BoardCanvas()
		{
		}

		/**
		 * Paints the graphics to this component
		 */
		public void paintComponent(Graphics g)
		{			
			super.paintComponent(g);
			this.draw(g);
		}	

		/**
		 * Draws the game board
		 * 
		 * @param g the graphics object used to create the drawing
		 */
		public void draw(Graphics g)
		{		
			try
			{
				//Background
				g.setColor(Color.DARK_GRAY);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				
				// Get info
				GameBoard game = cantStop.getGameBoard();
				if ( game == null ) {
					System.err.println("There was an error in your code! Check " +
							"that your CantStop.getGameBoard() returns a valid " +
							"GameGrid instance that is not null. " );
				}

				
				Track[] tracks = game.getTracks();
				if ( tracks == null ) {
					System.err.println("There was an error in your code! Check " +
							"that your GameBoard.getTracks() is not null.");
				}
				
				Player[] players = game.getPlayers();
				if ( players == null) {
					System.err.println("There was an error in your code! Check " +
							"that your GameBoard.getPlayers() is not null.");
				}

				//Column number headers
				Font f = new Font("Arial Bold", Font.BOLD, 24);
				g.setFont(f);
				g.setColor(Color.BLACK);
				int x = BORDER - 1;
				for(int ci=0; ci < tracks.length; ci++ ){
					Track t = tracks[ci];
					if ( t == null ) {
						System.err.println("There was an error in your code! Check " +
								"that none of your Tracks are null.");
					}
					int y = 5*BORDER/2 + Math.abs(12 - t.getLength()) * SLOT_WIDTH/2;
					
					g.drawString(String.format("%2d", ci+2), x, y);
					x += SLOT_WIDTH;
				}
				
				//TRACKS
				x = BORDER + DISK_DIAMETER/2;
				for (int ci=0; ci < tracks.length; ci++ )
				{				
					Track t = tracks[ci];
					if ( t == null ) {
						System.err.println("There was an error in your code! Check " +
								"that none of your Tracks are null.");
					}
					int y = BORDER + SLOT_WIDTH + Math.abs(12 - t.getLength()) * SLOT_WIDTH/2;
					//Owner spot
					drawCrown(g, x, y, t.getOwner());
					
					//Other spots
					y += SLOT_WIDTH;
					
					for (int ri = t.getLength() - 1; ri >=0 ; ri--)
					{
						char c = t.getIdAt(ri);
						switch(c)
						{
						case Util.EMPTY_SLOT:
							g.setColor(Color.lightGray);
							g.fillOval(x - DOT_DIAMETER/2, y - DOT_DIAMETER/2, DOT_DIAMETER, DOT_DIAMETER);
							g.setColor(Color.BLACK);
							g.drawOval(x - DOT_DIAMETER/2, y - DOT_DIAMETER/2, DOT_DIAMETER, DOT_DIAMETER);
							break;
						case Util.NEUTRAL_ID:
							g.setColor(Color.WHITE);
							g.fillOval(x - DISK_DIAMETER/2, y - DISK_DIAMETER/2, DISK_DIAMETER, DISK_DIAMETER);
							g.setColor(Color.BLACK);
							g.drawOval(x - DISK_DIAMETER/2, y - DISK_DIAMETER/2, DISK_DIAMETER, DISK_DIAMETER);
							break;
						default:
							boolean valid = false;
							for(int i = 0; i < 4; i++) valid |= c == Util.PLAYER_IDS[i];
							
							if(!valid){
								throw new RuntimeException(
										"Unknown disk character '"+c+"' found at row "+ri+" of column "+ci+".");
							}
							
							//Get correct player
							//Dont assume students store them in order
							Color color = Color.BLACK;
							for(Player p: players){
								if(p.getId() == c){
									color = p.getColor();
									break;
								}
							}
							
							g.setColor(color);
							g.fillOval(x - DISK_DIAMETER/2, y +- DISK_DIAMETER/2, DISK_DIAMETER, DISK_DIAMETER);
							g.setColor(Color.BLACK);
							g.drawOval(x - DISK_DIAMETER/2, y +- DISK_DIAMETER/2, DISK_DIAMETER, DISK_DIAMETER);
							break;
						}
						y += SLOT_WIDTH;
					}
					x += SLOT_WIDTH;

				}
			}
			catch(NullPointerException e)
			{
				e.printStackTrace();
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				System.err.println("The GUI tried to access a cone within" +
						" your GameBoard's tracks, but this " +
						"resulted in an ArrayIdexOutOfBounds " +
						"Exception.");
				e.printStackTrace();
			}

		}
		
		// Describes a crown shape for drawCrown
		private final int[] crownXs = {SLOT_WIDTH/8, 0, SLOT_WIDTH/4, SLOT_WIDTH/2, 3*SLOT_WIDTH/4,SLOT_WIDTH, 7*SLOT_WIDTH/8};
		private final int[] crownYs = {3*SLOT_WIDTH/4, SLOT_WIDTH/4, SLOT_WIDTH/2, SLOT_WIDTH/4, SLOT_WIDTH/2, SLOT_WIDTH/4, 3*SLOT_WIDTH/4};
		
		/**
		 * Draws a crown centered at (x,y). The color is determined
		 * by spot - the id of the player (or empty/neutral)
		 * @param g graphics object to draw to
		 * @param x center x coordinate
		 * @param y center y coordinate
		 * @param spot the id of the player who owns the crown (or empty/neutral)
		 */
		private void drawCrown(Graphics g, int x, int y, char spot){
			int[] xs = new int[crownXs.length];
			int[] ys = new int[crownYs.length];
			for(int i = 0; i < xs.length; i++){
				xs[i] = crownXs[i] + x - SLOT_WIDTH/2;
				ys[i] = crownYs[i] + y - SLOT_WIDTH/2;
			}
			
			switch(spot){
			case Util.NEUTRAL_ID:
				g.setColor(Color.WHITE);
				g.fillPolygon(xs,ys,xs.length);
				g.setColor(Color.BLACK);
				g.drawPolygon(xs, ys, xs.length);
				break;
			case Util.EMPTY_SLOT:
				g.setColor(Color.LIGHT_GRAY);
				g.drawPolygon(xs, ys, xs.length);
				break;
			default:
				//Get correct player
				//Dont assume students store them in order
				Color c = Color.BLACK;
				Player[] players = cantStop.getGameBoard().getPlayers();
				for(Player p: players){
					if(p.getId() == spot){
						c = p.getColor();
						break;
					}
				}
				g.setColor(c);
				g.fillPolygon(xs,ys,xs.length);
				g.setColor(Color.BLACK);
				g.drawPolygon(xs, ys, xs.length);
				break;
			}
			
		}
		
	}
	
	
	
	/**
	 * Encapsulates the component used to draw the board to the GUI.
	 * 
	 * @author schulzca, matthewb
	 *
	 */
	private class DiceCanvas extends JComponent implements MouseListener
	{
		/**
		 * Constructor
		 */
		public DiceCanvas()
		{
		}

		/**
		 * Paints the graphics to this component
		 */
		public void paintComponent(Graphics g)
		{			
			super.paintComponent(g);
			this.draw(g);
		}	

		/**
		 * Draws the dice display
		 * 
		 * @param g the graphics object used to create the drawing
		 */
		public void draw(Graphics g)
		{	
			try
			{
				int x = BORDER;
				int y = 2;
				for(int i = 0; i < rolls.length; i++){
					// Make selected dice darker
					if(selectedDice[i]){
						g.setColor(Color.DARK_GRAY);
					} else {
						g.setColor(Color.GRAY);
					}
					// Background
					g.fillRoundRect(x, y, SLOT_WIDTH*2, SLOT_WIDTH*2, SLOT_WIDTH/2, SLOT_WIDTH/2);
					
					// Outline
					g.setColor(cantStop.getGameBoard().getCurrentPlayer().getColor());
					g.drawRoundRect(x, y, SLOT_WIDTH*2, SLOT_WIDTH*2, SLOT_WIDTH/2, SLOT_WIDTH/2);
					
					// Dots
					drawDice(g, rolls[i], x, y);
					x+= SLOT_WIDTH*2 + BORDER*3;
				}
				
			}
			catch(NullPointerException e)
			{
				e.printStackTrace();
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				System.err.println("The GUI tried to access a cone within" +
						" your GameBoard's tracks, but this " +
						"resulted in an ArrayIdexOutOfBounds " +
						"Exception.");
				e.printStackTrace();
			}

		}
		
		/**
		 * Draws a dice with the top left corner at (x,y)
		 * @param g graphics object to draw to
		 * @param value which face of the die to draw
		 * @param x top left x coordinate
		 * @param y top left y coordinate
		 */
		private void drawDice(Graphics g, int value, int x, int y){
			int centerX = x+SLOT_WIDTH - DOT_DIAMETER/2;
			int centerY = centerX - x + y;
			switch(value){
			case 0:
				int size = 36;
				Font f = new Font("Arial Bold", Font.BOLD, size);
				g.setFont(f);
				g.drawString("?", x+SLOT_WIDTH - size/4, y + SLOT_WIDTH+size/4);
				break;
			case 1:
				g.fillOval(centerX, centerY, DOT_DIAMETER, DOT_DIAMETER);
				break;
			case 3:
				g.fillOval(centerX, centerY, DOT_DIAMETER, DOT_DIAMETER);
			case 2:
				g.fillOval(centerX - SLOT_WIDTH/2, centerY + SLOT_WIDTH/2, DOT_DIAMETER, DOT_DIAMETER);
				g.fillOval(centerX + SLOT_WIDTH/2, centerY - SLOT_WIDTH/2, DOT_DIAMETER, DOT_DIAMETER);
				break;
			case 5:
				g.fillOval(centerX, centerY, DOT_DIAMETER, DOT_DIAMETER);
			case 4:
				g.fillOval(centerX - SLOT_WIDTH/2, centerY + SLOT_WIDTH/2, DOT_DIAMETER, DOT_DIAMETER);
				g.fillOval(centerX + SLOT_WIDTH/2, centerY - SLOT_WIDTH/2, DOT_DIAMETER, DOT_DIAMETER);
				g.fillOval(centerX + SLOT_WIDTH/2, centerY + SLOT_WIDTH/2, DOT_DIAMETER, DOT_DIAMETER);
				g.fillOval(centerX - SLOT_WIDTH/2, centerY - SLOT_WIDTH/2, DOT_DIAMETER, DOT_DIAMETER);
				break;
			case 6:
				g.fillOval(centerX - SLOT_WIDTH/2, centerY + SLOT_WIDTH/2, DOT_DIAMETER, DOT_DIAMETER);
				g.fillOval(centerX + SLOT_WIDTH/2, centerY - SLOT_WIDTH/2, DOT_DIAMETER, DOT_DIAMETER);
				g.fillOval(centerX + SLOT_WIDTH/2, centerY + SLOT_WIDTH/2, DOT_DIAMETER, DOT_DIAMETER);
				g.fillOval(centerX - SLOT_WIDTH/2, centerY - SLOT_WIDTH/2, DOT_DIAMETER, DOT_DIAMETER);
				g.fillOval(centerX + SLOT_WIDTH/2, centerY, DOT_DIAMETER, DOT_DIAMETER);
				g.fillOval(centerX - SLOT_WIDTH/2, centerY, DOT_DIAMETER, DOT_DIAMETER);
				break;
			}
		}

		/**
		 * Used to select a dice. Only update selection if we are expecting 
		 * the user to  be selecting dice.
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if(!didBust && submit.isEnabled()){
				int x = e.getX() - BORDER*2;
				int y = e.getY() - 550;
				
				// Find correct dice
				int num = -1;
				for(int i = 0; i < 4; i++){
					if(x >=0 && x < SLOT_WIDTH*2){
						num = i;
						break;
					} else if(x < 0){
						break;
					} else {
						x -= SLOT_WIDTH*2 + BORDER*3;
					}
				}
				
				// Update
				if(num >= 0 && num < 4 && y >= 0 && y < SLOT_WIDTH*2){
					selectedDice[num] = !selectedDice[num];
				}
			}
			updateCanvas();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}
}
