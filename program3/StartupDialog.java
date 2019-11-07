import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/** 
 * Implements the Startup Dialog window that prompts the user to enter the 
 * new game's settings
 * 
 * Students should not edit or submit this file
 * 
 * @author schulzca, matthewb
 *
 */
@SuppressWarnings("serial")
public class StartupDialog extends JDialog implements ActionListener
{
		private JTextField t1;
		private JTextField t2;
		private JTextField t3;
		private JTextField t4;

		private JButton c1;
		private JButton c2;
		private JButton c3;
		private JButton c4;
		
		private JLabel status;
	
		public StartupDialog(String title){
			this(title, null);
		}
		
		@SuppressWarnings({ "static-access" })
		public StartupDialog(String title, Player[] players)
		{	
			// Create user name fields
			t1 = new JTextField();
			t2 = new JTextField();
			t3 = new JTextField();
			t4 = new JTextField();
			
			t1.setText("Deb");
			t2.setText("Hobbes");
			t3.setText(null);
			t4.setText(null);
			
			int length = 0;
			if(players != null) {
				length = players.length;
			}
			
			// Set defaults
			switch(length){
			case 4: t4.setText(players[3].getName());
			case 3: t3.setText(players[2].getName());
			case 2: t2.setText(players[1].getName());
			case 1: t1.setText(players[0].getName());
			}
			
			// Create color choice buttons
			c1 = new JButton("");
			if(length > 0){
				c1.setBackground(players[0].getColor());
			} else { 
				c1.setBackground(Color.BLUE);
			}
			c1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Color initialBackground = c1.getBackground();
			        Color background = JColorChooser.showDialog(null, t1.getText() + "'s Color",
			            initialBackground);
			        if (background != null) {
			          c1.setBackground(background);
			        }
				}
			});
			
			c2 = new JButton("");
			if(length > 1){
				c2.setBackground(players[1].getColor());
			} else { 
				c2.setBackground(Color.RED);
			}
			c2.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Color initialBackground = c2.getBackground();
			        Color background = JColorChooser.showDialog(null, t2.getText() + "'s Color",
			            initialBackground);
			        if (background != null) {
			          c2.setBackground(background);
			        }
				}
			});
			
			c3 = new JButton("");
			if(length > 2){
				c3.setBackground(players[2].getColor());
			} else { 
				c3.setBackground(Color.GREEN);
			}
			c3.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Color initialBackground = c3.getBackground();
			        Color background = JColorChooser.showDialog(null, t3.getText() + "'s Color",
			            initialBackground);
			        if (background != null) {
			          c3.setBackground(background);
			        }
				}
			});
			
			c4 = new JButton("");
			if(length > 3){
				c4.setBackground(players[3].getColor());
			} else { 
				c4.setBackground(Color.ORANGE);
			}
			c4.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Color initialBackground = c4.getBackground();
			        Color background = JColorChooser.showDialog(null, t4.getText() + "'s Color",
			            initialBackground);
			        if (background != null) {
			          c4.setBackground(background);
			        }
				}
			});
			
			// Error panel
			status = new JLabel("");
			status.setForeground(Color.RED);
			
			
			// Add all components
			JPanel center = new JPanel();
			center.setLayout(new GridLayout(4,3));
			center.add( new JLabel("   Player 1: ") );
			center.add(t1);
			center.add(c1);
			center.add( new JLabel("   Player 2: ") );
			center.add(t2);
			center.add(c2);
			center.add( new JLabel("   Player 3: ") );
			center.add(t3);
			center.add(c3);
			center.add( new JLabel("   Player 4: ") );
			center.add(t4);
			center.add(c4);
			
			JPanel south = new JPanel();
			south.setLayout(new GridLayout(2,1));
			JButton submitBtn = new JButton("Submit");
			submitBtn.setPreferredSize(new Dimension(100, 40));
			submitBtn.addActionListener(this);
			south.setPreferredSize(new Dimension(100, 50));
			south.add(submitBtn);
			south.add(status);
			
			JPanel main = new JPanel();
			main.setLayout(new BorderLayout());
			main.add(center, BorderLayout.CENTER);
			main.add(south, BorderLayout.SOUTH);
			main.setBorder(new TitledBorder("Player names and colors"));
			
			this.setTitle(title);
			this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
			this.add(main);
			this.setModal(true);
			this.setSize(130,180);
			this.setLocation(300, 300);
		}

		/**
		 * Handles submit button press
		 */
		@Override
		public void actionPerformed(ActionEvent e) 
		{			
			try
			{
				// get name values
				String p1Name = t1.getText();
				String p2Name = t2.getText();
				String p3Name = t3.getText();
				String p4Name = t4.getText();
				
				// validate number of players
				int numPlayers = validateNames(p1Name, p2Name, p3Name, p4Name);
				if (!(numPlayers >=2 && numPlayers <= 4))
				{
					status.setText("There must be 2-4 valid players!");					
				}
				else
				{
					// Prepare for CantStop creation
					String[] names = new String[numPlayers];
					Color[] colors = new Color[numPlayers];
					
					switch(numPlayers){
					case 4: 
						names[3] = p4Name;
						colors[3] = c4.getBackground();
					case 3:
						names[2] = p3Name;
						colors[2] = c3.getBackground();
					case 2:
						names[1] = p2Name;
						colors[1] = c2.getBackground();
					case 1:
						names[0] = p1Name;
						colors[0] = c1.getBackground();
					}
					
					// Destroy this modal window
					this.dispose();
					
				
					// Run the game entry into student code through CantStop constructor eventually
					Gui gui = new Gui(new CantStop(names, colors));
			    	gui.update();
			    	gui.start();
				}
			}
			catch(NumberFormatException x)
			{
				status.setText("Input must be an integer.");
			}
		}
		
		/**
		 * Requires name to be ordered sequentially and not be blank
		 * @param a first name
		 * @param b second name
		 * @param c third name
		 * @param d fouth name
		 * @return number of valid names
		 */
		private static int validateNames(String a, String b, String c, String d){
			int num = 0;
			if(a != null && b != null && !a.trim().equals("") && !b.trim().equals("")){
				num = 2;
				if(c != null && !c.trim().equals("")){
					num++;
					if(d != null && !d.trim().equals("")){
						num++;
					}
				}
			}
			return num;
		}
}
