
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class Bricks {
	
	public int map [][];
	public int brickWidth;
	public int brickHeight;
	int rowLength;
	int colLength;
	private Map<String, BufferedImage> images ;//Using the hash map to store images to avoid low performance of the game.
	public Bricks(int row, int col) {
		images=new HashMap<>();
		try {
			images.put("brick2.png", ImageIO.read(new File("C:\\Users\\krish\\Downloads\\darkbrown.png")));
			images.put("brick6.png", ImageIO.read(new File("C:\\Users\\krish\\Downloads\\Brick6.png")));
			images.put("brick7.png", ImageIO.read(new File("C:\\Users\\krish\\Downloads\\brick_wall_64x64\\Brick7.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rowLength=row;
		colLength=col;
		map = new int [rowLength][colLength];
		for (int i = 0; i < map.length; i++) { 
			for (int j=0; j< map[0].length;j++) {				
				   map[i][j] = 3;
			}
		}
		
		
	}
	
	public void draw(Graphics2D g) {
		brickWidth = 30;
		brickHeight = 10;
		for (int i = 0; i < map.length; i++) {
			for (int j=0; j<map[0].length;j++) {
				if(map[i][j] > 0) {
					int x = j * brickWidth+110;
	                int y = i * brickHeight+20;
	                if(map[i][j]==1) {
	                	g.drawImage(images.get("brick7.png"), x, y, 30, 10, null);}
	                else if(map[i][j]==2) {
	                	g.drawImage(images.get("brick6.png"), x, y, 30, 10, null);
	                }
	                else if(map[i][j]==3) {
	                	g.drawImage(images.get("brick2.png"), x, y, 30, 10, null);
	                }
	                
	                g.setColor(Color.white);
	                g.drawRect(x, y, brickWidth, brickHeight);
				}
			}
			
		}
	}
	
	// this decrease the values of bricks
	public void setBrickValue(int value, int row, int col) {
		map[row][col] = value;
	}

}

class GamePlay extends JPanel implements KeyListener, ActionListener  {
	private boolean play = true;
	private int score = 0;
	
	private int totalBricks=20*14;
	
	private Timer timer;
	private int delay =8;
	
	private int playerX = 310;
	
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;


	private Bricks map;
	
	private Map<String, BufferedImage> pics;
	public GamePlay() {
		map = new Bricks(20, 14);
		addKeyListener(this);
		setFocusable(true);
	    pics=new HashMap<>();
	    try {
			pics.put("bar.png", ImageIO.read(new File("C:\\Users\\krish\\Downloads\\bloodbar.png")));
			pics.put("bar2.png", ImageIO.read(new File("C:\\Users\\krish\\Downloads\\bar2.png")));
			pics.put("background.png", ImageIO.read(new File("C:\\Users\\krish\\Downloads\\City Background\\background.png")));
			pics.put("background2.png", ImageIO.read(new File("C:\\Users\\krish\\Downloads\\background2.png")));
			pics.put("background4.png", ImageIO.read(new File("C:\\Users\\krish\\Downloads\\background4.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}

	public void paint(Graphics g) {
		
		g.setColor(Color.YELLOW);
		 g.drawImage(pics.get("background4.png"), 0, 0, 700, 600, null);
		  map.draw((Graphics2D)g);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		g.drawImage(pics.get("bar2.png"), playerX, 550, 100, 40, null);
		
		g.setColor(Color.RED);  // ball color
		g.fillOval(ballposX, ballposY, 15, 15);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		timer.start();
		if(play) {
			// Ball - Pedal  interaction 
			if(new Rectangle(ballposX, ballposY, 15, 15).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYdir = - ballYdir;
			}
			
					
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX < 0) { 
				ballXdir = -ballXdir;
			}
			if(ballposY < 0) {  
				ballYdir = -ballYdir;
			}
			if(ballposX > 670) {
				ballXdir = -ballXdir;  
			
			}
			
		}
			
		repaint();

	}
	

	@Override
	public void keyTyped(KeyEvent key) {
		System.out.println(key);
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_RIGHT) { 
			if(playerX >= 600) {
				playerX = 600;
			} else {
				if(play==true)
				playerX += 50;
					
			}
		}
		if(key.getKeyCode() == KeyEvent.VK_LEFT) { 
			if(playerX < 10) {
				playerX = 10;
			} else {
				if(play==true)
				   playerX -= 50;
					
			}
		}
		
		if(key.getKeyCode() == KeyEvent.VK_ENTER) { 
		
		}
		
	}	
	
	@Override
	public void keyReleased(KeyEvent key) {
		
	}

}
public class MyGame {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		GamePlay gamePlay = new GamePlay();
		frame.setBounds(10, 10, 700, 650);
		frame.setTitle("Brick Breaker");
		frame.setResizable(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(gamePlay);
		
	}

}
