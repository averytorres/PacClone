
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JFrame;
import javax.swing.JPanel;



@SuppressWarnings("serial")
public class Board extends JPanel implements KeyListener{

	final JFrame frame = new JFrame("PacClone");
	boolean up=false,down=false,left=false,right=false,fire=false,gameover=false,started=false,setIntro=false,setL1=false;
	int playerX,playerY;
	int graveyard=0;
	int score=0;
	int lives=5;
	int level=0;
	int highScore=0;
	long wait;
	Clip clip;
	Welcome w;  
	Player player;
	Level1 L1;
	
	
	public Board(){

		//settings
		boardSettings();
		frameSettings();

		//init timer for drawing
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				repaint();
			}
		}, 0, (long) 13);

		//initialize player, enemy and fire objects
		initObjects();
		
	}



	private void initObjects() {
		
		wait = System.nanoTime();
		w = new Welcome(this.getWidth(),this.getHeight(),wait,this,highScore);
		player = new Player(this);
		L1=new Level1();
		
	}



	private void frameSettings() {
		
		//set up frame location and size
		frame.add(this);
		frame.setPreferredSize(new Dimension(500, 500));
		frame.setSize(new Dimension(500, 500));
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
	}



	private void boardSettings() {
		
		//setting up board look and listeners 
		setBackground(Color.black);
		setFocusable(true);
		setPreferredSize(new Dimension(400, 400));

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}        
		});  
		this.addKeyListener(this);
		
	}



	protected void paintComponent(Graphics g) {
		super.paintComponent(g); 

		
		if(!started){
			
			//beginning of game, waiting on user input
			//welcomeScreen(g);
			w.welcomeScreen(g);
			if(!setIntro){
				setMusic("/music/intro.wav");
				
				setIntro=true;
			}
			
		}
		else if(!gameover){
			//playing
			if(level==0){
				
				updatePlayer(g);
				if(!setL1){
					setMusic("/music/L1.wav");
					setL1=true;
				}
				L1.drawLevel(g);
			}
		
		}
		else{
			//gameover
		}
	}
	private void updatePlayer(Graphics g) {

		//calculate player trajectory
		Dimension d = this.getSize();
		player.update(up,down,left,right,d);
		playerX=player.getPlayerX();
		playerY=player.getPlayerY();
		player.draw(g);

	}

	private void setMusic(String string) {
		if(clip!=null&&clip.isRunning()){
			clip.stop();
			clip.close();
			
		}
		
		try {
			
		    URL yourFile= this.getClass().getResource(string);
		    AudioInputStream stream;
		    AudioFormat format;
		    DataLine.Info info;
		    

		    stream = AudioSystem.getAudioInputStream(yourFile);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clip = (Clip) AudioSystem.getLine(info);
		    
		    clip.open(stream);
		    clip.loop(-1);
		    clip.start();
		}
		catch (Exception e) {
		    
			System.out.println("music fail");
		}
		
	}



	private void computeGame(Graphics g) {
		
		
	}


	private void updateHS() {
		
		//update the high score
		highScore=score;
	}	

	public void keyPressed(KeyEvent e) {

		//checks what direction is pressed for player movement
		if(e.getKeyCode()==KeyEvent.VK_UP){
			up=true;
			down=false;
			left=false;
			right=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN){
			up=false;
			down=true;
			left=false;
			right=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			up=false;
			down=false;
			left=true;
			right=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			up=false;
			down=false;
			left=false;
			right=true;
		}


	}

	public void keyReleased(KeyEvent e) {
		
		//checks if player has stopped moving in a direction or has fired a shot
		if(e.getKeyCode()==KeyEvent.VK_UP){	
			//up=false;

		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN){
			//down=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			//left=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			//right=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			L1.generateLevel();
		}
		
		//player has died and gameover screen should be displayed, player is able to exit and restart with button press
		if(gameover && e.getKeyCode()==KeyEvent.VK_SPACE&&(System.nanoTime()-wait>3000000000l)){
			wait=System.nanoTime();
			resetGame();
			
		}
		
		//game has been open and in starting screen, player is able to start actual gameplay with button press
		if(!started&& e.getKeyCode()==KeyEvent.VK_SPACE&&(System.nanoTime()-wait>900000000l)){
			started=true;
		}
	}

	private void resetGame() {
		
		//resets the information about gameplay including, lives, enemy location and score
		lives=5;
		started=false;
		score=0;
	}

	public static void main(String[] args) throws IOException{
		@SuppressWarnings("unused")
		Board b = new Board();
	}
	
	//unused
	public void dispatch() {
		// TODO Auto-generated method stub

	}

	//unused
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
