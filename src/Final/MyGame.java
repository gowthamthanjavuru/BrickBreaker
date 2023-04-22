package Final;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
///Main Assignment
class Bricks {
	
	public int arr [][];
	public int brickWidth;
	public int brickHeight;
	
	private Map<String, BufferedImage> images ;
	// this creates the brick of size 3x7
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
	
		arr = new int [row][col];
		for (int i = 0; i < arr.length; i++) { 
			for (int j=0; j< arr[0].length;j++) {
				   arr[i][j] = 3;
			}
		}
		
		
	}
	
	// this draws the bricks
	public void draw(Graphics2D g) {
		brickWidth = 30;
		brickHeight = 10;
		for (int i = 0; i < arr.length; i++) {
			for (int j=0; j<arr[0].length;j++) {
				if(arr[i][j] > 0) {
					int x = j * brickWidth+110;
	                int y = i * brickHeight+20;
	                if(arr[i][j]==1) {
	                	g.drawImage(images.get("brick7.png"), x, y, 30, 10, null);}
	                else if(arr[i][j]==2) {
	                
	                	g.drawImage(images.get("brick6.png"), x, y, 30, 10, null);
	                }
	                else if(arr[i][j]==3) {
	                	g.drawImage(images.get("brick2.png"), x, y, 30, 10, null);
	                }
	                
	                g.setColor(Color.white);
	                g.drawRect(x, y, brickWidth, brickHeight);
				}
			}
			
		}
	}
	
	// this sets the value of brick to 0 if it is hit by the ball
	public void setBrickValue(int value, int row, int col) {
		arr[row][col] = value;
	}

}

class GamePlay extends JPanel implements KeyListener,ActionListener  {
	private boolean play = true;
	private int score = 0;
	
	private int totalBricks=20*14*3;
	
	private Timer timer;
	private int delay =8;
	
	private int playerX = 310;
	private int playerY=550;
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;

	private boolean pause;
	
	private Bricks map;
		
	private BufferedImage bird;
	private Map<String, BufferedImage> pics;
	public GamePlay() {
		addKeyListener(this);
		map = new Bricks(20, 14);
		
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
		
		g.setColor(Color.black);
		g.fillRect(0, 0, 3, 600);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 600);
		g.drawImage(pics.get("bar.png"), playerX, playerY, 120, 20, null);
		
		g.setColor(Color.RED);  
		g.fillOval(ballposX, ballposY, 15, 15);
		
		g.setColor(Color.white);
		g.setFont(new Font("MV Boli", Font.BOLD, 25));
		g.drawString("Score: " + score, 530, 30);
		
		if (totalBricks <= 0) {// if all bricks are destroyed then you win
			
			play = false;
			g.setColor(new Color(0XFF6464));
			g.setFont(new Font("MV Boli", Font.BOLD, 30));
			g.drawString("You Won, Score: " + score, 190, 300);
			
			g.setFont(new Font("MV Boli", Font.BOLD, 20));
			g.drawString("Press Enter to Restart.", 230, 350);
		}
		
		if(ballposY > 570) { // if ball goes below the paddle then you lose 
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.BLACK);
			g.setFont(new Font("MV Boli", Font.BOLD, 30));
			g.drawString("Game Over, Score: " + score, 190, 300);
			
			g.setFont(new Font("MV Boli", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230, 350);
				
		} 
		g.dispose();
	}

	
	
	@Override
	public void keyTyped(KeyEvent key) {
		System.out.println(key);
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_RIGHT) { // if right arrow key is pressed then paddle moves right
			if(playerX >= 600) {
				playerX = 600;
			} else {
				
				if(play == true)
				playerX += 50;
					
			}
		}
		if(key.getKeyCode() == KeyEvent.VK_LEFT) { // if left arrow key is pressed then paddle moves left
			if(playerX < 10) {
				playerX = 10;
			} else {
				if(play == true)
				playerX -= 50;
					
			}
		}
		
		if(key.getKeyCode() == KeyEvent.VK_ENTER) { // if enter key is pressed then game restarts
			if(!play) {
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				score = 0;
				totalBricks = 20*14*3;
				map = new Bricks(20,14);
				
				repaint();
			}
		}
		
	}	
		

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		timer.start();
		
		if(play) {
			// Ball - Pedal  interaction 
			if(new Rectangle(ballposX, ballposY, 15, 15).intersects(new Rectangle(playerX, playerY, 120, 20))) {
				ballYdir = - ballYdir;
			}
			
			for( int i = 0; i<map.arr.length; i++) { // Ball - Brick interaction
				for(int j = 0; j<map.arr[0].length; j++) { 
					if(map.arr[i][j] > 0) {
						int brickX = j*map.brickWidth + 110;
						int brickY = i*map.brickHeight + 20;
						int brickWidth= map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 15,15);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect) ) {
							int num=map.arr[i][j];
							map.setBrickValue(num-1, i, j);
							totalBricks--;
							score+=5;
							
							if(ballposX + 14 <= brickRect.x || ballposX +1 >= brickRect.x + brickRect.width) 
								ballXdir = -ballXdir;
							 else {
								ballYdir = -ballYdir;
							}
						}
						
					}
					
				}
			   }
			
			if(score>0 && score %250==0)
			{
				playerY-=15;
				score+=10;
				System.out.println(playerY);
				
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
	

	


}
public class MyGame {

	public static void main(String[] args) {
		JFrame obj = new JFrame();
		GamePlay gamePlay = new GamePlay();
		obj.setBounds(10, 10, 710, 640);
		obj.setTitle("Brick Breaker");
		obj.setResizable(true);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
		
	}

}
