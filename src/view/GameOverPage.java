package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameOverPage extends JFrame {

	private JPanel gameOverPanel;
	private JLabel gameOverText;
	private JLabel scoreText;
	public GameOverPage() {
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Game Over");
		this.setSize(1400, 200);
		this.setLocationRelativeTo(null);
		
		ImageIcon img = new ImageIcon("resources/gameover.png");
		Image image = img.getImage().getScaledInstance(1400, 200, Image.SCALE_SMOOTH);
		JLabel bg = new JLabel(new ImageIcon(image));
		this.setContentPane(bg);
		
		this.setLayout(new BorderLayout());
		
		constructGameOverPanel();
		constructGameOverText();
		constructScoreText();
		this.validate();

	}
	
	private void constructGameOverPanel() {
		gameOverPanel = new JPanel();
		gameOverPanel.setLayout(new GridLayout(2,1));
		gameOverPanel.setBackground(Color.WHITE);
		this.gameOverPanel.setOpaque(false);
		
		this.add(gameOverPanel, BorderLayout.CENTER);
	}
	
	private void constructGameOverText() {
		gameOverText = new JLabel("GAME OVER", JLabel.CENTER);
		gameOverText.setFont(new Font("Arial", 1, 50));
		gameOverText.setForeground(Color.WHITE);
		gameOverPanel.add(gameOverText);
	}
	
	private void constructScoreText() {
		scoreText = new JLabel("SCORE: " , JLabel.CENTER);
		scoreText.setFont(new Font("Arial", 1, 50));
		scoreText.setForeground(Color.WHITE);
		gameOverPanel.add(scoreText);
	}
	
	public JLabel getScoreText() {
		return this.scoreText;
	}
	
	public static void main(String[] args) {
		GameOverPage p = new GameOverPage();
		p.setVisible(true);
	}
}