package puzzle;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

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


		JFrame mainFrame = new JFrame("8-Puzzle");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setPreferredSize(new Dimension(650,780));
		mainFrame.setLayout(new FlowLayout());

		final BoardController board = new BoardController(ROW, COL, TILE_SIZE);


		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.restart();
			}
		});

		JPanel symbolSettings = new JPanel();
		JLabel symbolLabel = new JLabel("Symbol:");
		JLabel p1Label = new JLabel("Player1:");
		JTextField p1SymbolText = new JTextField(1);
		p1SymbolText.setDocument(new JTextFieldLimit(1));
		JLabel p2Label = new JLabel("Player2:");
		JTextField p2SymbolText = new JTextField(1);
		p2SymbolText.setDocument(new JTextFieldLimit(1));

		symbolSettings.add(symbolLabel);
		symbolSettings.add(p1Label);
		symbolSettings.add(p1SymbolText);
		symbolSettings.add(p2Label);
		symbolSettings.add(p2SymbolText);


		board.getView().setPreferredSize(new Dimension(600, 600));
		reset.setPreferredSize(new Dimension(100, 50));
		mainFrame.add(symbolSettings);
		mainFrame.add(board.getView());
		mainFrame.add(reset);

		mainFrame.pack();
		mainFrame.setVisible(true);
	}

}
