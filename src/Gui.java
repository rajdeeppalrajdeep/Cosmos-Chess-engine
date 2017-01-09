import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Gui extends JFrame 
{

	private JPanel contentPane;
	private JTextField[] textField = new JTextField[64];
	private JLabel[] rows = new JLabel[8];
	private JTextField textField_1;
	private JLabel[] columns = new JLabel[8];
	private JPanel panel;
	private JTextField textField_2;
	private JButton btnSetUpBoard;
	private JButton btnGo;
	private JLabel label;
	int startpos = -1, endpos = -1;
	int test = 0;
	boolean start = true;
	private JLabel lblLastMove;
	public static JLabel lastLabel = null;
	/**
	 * Launch the application.
	 */
	public  void disp() 
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() 
			{
				try 
				{
					Gui frame = new Gui();
					frame.drawBoard();
					frame.setVisible(true);
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public void drawBoard()
	{
		//textField_1.setForeground(new Color(255, 255, 255));
		//textField_1.setText(" \u2654");
		if(Cosmos.whiteToMove)
		{
			label.setText("WHITE TO PLAY");
		}
		else
		{
			label.setText("BLACK TO PLAY");
		}
		 for (int i=0;i<64;i++) 
			{
	            if (((Cosmos.wPawn>>i)&1)==1) 
	            {
	            	textField[i].setForeground(new Color(255, 255, 255));
	            	textField[i].setText(" \u2659");
	            }
	            else if (((Cosmos.wKnight>>i)&1)==1) 
	            {
	            	textField[i].setForeground(new Color(255, 255, 255));
	            	textField[i].setText(" \u2658");
	            }
	            else if (((Cosmos.wBishop>>i)&1)==1) 
	            {
	            	textField[i].setForeground(new Color(255, 255, 255));
	            	textField[i].setText(" \u2657");
	            }
	            else if (((Cosmos.wRook>>i)&1)==1) 
	            {
	            	textField[i].setForeground(new Color(255, 255, 255));
	            	textField[i].setText(" \u2656");
	            }
	            else if (((Cosmos.wQueen>>i)&1)==1) 
	            {
	            	textField[i].setForeground(new Color(255, 255, 255));
	            	textField[i].setText(" \u2655");
	            }
	            else if (((Cosmos.wKing>>i)&1)==1) 
	            {
	            	textField[i].setForeground(new Color(255, 255, 255));
	            	textField[i].setText(" \u2654");
	            }
	            else if (((Cosmos.bPawn>>i)&1)==1) 
	            {
	            	textField[i].setForeground(new Color(0, 0, 0));
	            	textField[i].setText(" \u265F");
	            }
	            else if (((Cosmos.bKnight>>i)&1)==1) 
	            {
	            	textField[i].setForeground(new Color(0, 0, 0));
	            	textField[i].setText(" \u265E");
	            }
	            else if (((Cosmos.bBishop>>i)&1)==1) 
	            {
	            	textField[i].setForeground(new Color(0, 0, 0));
	            	textField[i].setText(" \u265D");
	            }
	            else if (((Cosmos.bRook>>i)&1)==1) 
	            {
	            	textField[i].setForeground(new Color(0, 0, 0));
	            	textField[i].setText(" \u265C");
	            }
	            else if (((Cosmos.bQueen>>i)&1)==1) 
	            {
	            	textField[i].setForeground(new Color(0, 0, 0));
	            	textField[i].setText(" \u265B");
	            }
	            else if (((Cosmos.bKing>>i)&1)==1) 
	            {
	            	textField[i].setForeground(new Color(0, 0, 0));
	            	textField[i].setText(" \u265A");
	            }
	            else 
	            {
	            	textField[i].setText("");
	            }
	        }
	}
	public Gui() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 663, 597);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField_2 = new JTextField();
		
		
		textField_2.setBounds(149, 497, 200, 50);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton = new JButton("Make Move ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				String pos = "position moves "+ textField_2.getText();
				UCI.inputPosition(pos);
				drawBoard();
				textField_2.setText("");
			}
		});
		btnNewButton.setBounds(469, 507, 122, 30);
		contentPane.add(btnNewButton);
		
		btnSetUpBoard = new JButton("Set Up board");
		btnSetUpBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				UCI.inputPosition("position startpos");
				drawBoard();
			}
		});
		btnSetUpBoard.setBounds(22, 457, 117, 23);
		contentPane.add(btnSetUpBoard);
		
		btnGo = new JButton("Go");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UCI.inputGo();
				drawBoard();
			}
		});
		btnGo.setBounds(157, 457, 89, 23);
		contentPane.add(btnGo);
		
		label = new JLabel("");
		label.setBounds(530, 21, 107, 30);
		contentPane.add(label);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(10, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		lblLastMove = new JLabel("Last Move");
		lblLastMove.setBounds(563, 62, 74, 14);
		contentPane.add(lblLastMove);
		
		lastLabel = new JLabel("");
		lastLabel.setBounds(563, 88, 46, 14);
		contentPane.add(lastLabel);
		
		//JLabel lblNewLabel = new JLabel("   H");
		//lblNewLabel.setBounds(70, 21, 23, 50);
		//contentPane.add(lblNewLabel);
		for(int i=0; i<8; i++)
		{
			rows[i] = new JLabel("   "+(8-i));
			rows[i].setBounds(70, 21+50*i, 23, 50);
			contentPane.add(rows[i]);
		}
		for(int i=0; i<8; i++)
		{
			columns[i] = new JLabel(" "+(char)(65+i));
			columns[i].setBounds(110+50*i, 410, 23, 50);
			contentPane.add(columns[i]);
		}
		boolean white = true;
		for( test = 0; test<64; test++)
		{
			textField[test] = new JTextField();
			if(white)
			{
				textField[test].setBackground(new Color(218, 165, 32));
			}
			else
			{
				textField[test].setBackground(new Color(139, 69, 19));
			}
			white = !white;
			int x =90+50*((test)%8);
			int y = (21+50*(test/8));
			textField[test].setBounds(x, y, 50, 50);
			textField[test].setColumns(10);
			textField[test].setFont(new Font("Arial Unicode MS", Font.BOLD, 45));

			contentPane.add(textField[test]);
			if(test%8 == 7)
			{
				white = !white;
			}
		}
		

		
	}
}
