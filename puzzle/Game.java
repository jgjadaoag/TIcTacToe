package puzzle;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import puzzle.board.BoardController;

//from: http://www.java2s.com/Tutorial/Java/0240__Swing/LimitJTextFieldinputtoamaximumlength.htm
class JTextFieldLimit extends PlainDocument {
	private int limit;
	JTextFieldLimit(int limit) {
		super();
		this.limit = limit;
	}

	JTextFieldLimit(int limit, boolean upper) {
		super();
		this.limit = limit;
	}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null)
			return;

		if ((getLength() + str.length()) <= limit) {
			super.insertString(offset, str, attr);
		}
	}
}

public class Game {
	public static void main(String[] args) {
		final int ROW = 3;
		final int COL = 3;
		final int TILE_SIZE = 200;
		final Random r = new Random();


		final JFrame mainFrame = new JFrame("8-Puzzle");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setPreferredSize(new Dimension(650,780));
		mainFrame.setLayout(new FlowLayout());

		final BoardController board = new BoardController(ROW, COL, TILE_SIZE);


		JPanel reset = new JPanel();
		JButton reset1 = new JButton("New Game: Human First");
		reset1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.restart(true);
			}
		});
		JButton reset2 = new JButton("New Game: AI First");
		reset2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.restart(false);
			}
		});
		JButton reset3 = new JButton("New Game: Random");
		reset3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.restart(r.nextBoolean());
			}
		});
		reset.add(reset1);
		reset.add(reset2);
		reset.add(reset3);

		final JPanel symbolSettings = new JPanel();
		final JLabel symbolLabel = new JLabel("Symbol:");
		final JLabel p1Label = new JLabel("Human: ");
		final JTextField p1SymbolText = new JTextField(1);
		p1SymbolText.setDocument(new JTextFieldLimit(1));
		final JLabel p2Label = new JLabel("AI: ");
		final JTextField p2SymbolText = new JTextField(1);
		p2SymbolText.setDocument(new JTextFieldLimit(1));
		final JButton symbolSettingsButton = new JButton("Set Symbol");
		symbolSettingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String p1 = p1SymbolText.getText();
				String p2 = p2SymbolText.getText();

				if (p1.length() == 0 || p2.length() == 0) {
					JOptionPane.showMessageDialog(mainFrame, "Insufficient Input", "Invalid Input", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (p1.equals(p2)) {
					JOptionPane.showMessageDialog(mainFrame, "Symbols should be different", "Invalid Input", JOptionPane.ERROR_MESSAGE);
					return;
				}

				board.changeSymbol(p1.charAt(0), p2.charAt(0));


			}
		});

		symbolSettings.add(symbolLabel);
		symbolSettings.add(p1Label);
		symbolSettings.add(p1SymbolText);
		symbolSettings.add(p2Label);
		symbolSettings.add(p2SymbolText);
		symbolSettings.add(symbolSettingsButton);


		board.getView().setPreferredSize(new Dimension(600, 600));
		mainFrame.add(symbolSettings);
		mainFrame.add(board.getView());
		mainFrame.add(reset);

		mainFrame.pack();
		mainFrame.setVisible(true);
	}

}
