import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Player {
	
	int playerX;
	int playerY;
	int closeTimer,openTimer;
	private Image pac;
	Board board;
	Boolean open;
	ImageIcon iipR,iipD,iipL,iipU,iipC;
	
	protected int getPlayerX() {
		return playerX;
	}

	public void setPlayerX(int playerX) {
		this.playerX = playerX;
	}

	protected int getPlayerY() {
		return playerY;
	}

	public void setPlayerY(int playerY) {
		this.playerY = playerY;
	}

	
	
	public Player(Board board){
		playerX=245;
		playerY=400;
		this.board=board;
		load();
		open=true;
		closeTimer=0;
		openTimer=0;
	}

	public void draw(Graphics g){

	    //body
	    g.drawImage(pac, getPlayerX(), getPlayerY(), board);
	}

	public void update(boolean up, boolean down, boolean left, boolean right, Dimension d) {
		//check for movment 
		if(up){
			this.setPlayerY(this.getPlayerY()-3);
			checkAni(iipU);

		}
		else if(down){
			this.setPlayerY(this.getPlayerY()+3);
			checkAni(iipD);
		}
		if(left){
			this.setPlayerX(this.getPlayerX()-3);
			checkAni(iipL);
		}
		else if(right){
			this.setPlayerX(this.getPlayerX()+3);
			checkAni(iipR);
		}

		//create bounds the player cannot pass through
		
		if(d.height-15<this.getPlayerY()){
			this.setPlayerY(d.height-15);
		}
		if(10>this.getPlayerY()){
			this.setPlayerY(10);
		}
		if(d.width-15<this.getPlayerX()){
			this.setPlayerX(d.width-15);
		}
		if(10>this.getPlayerX()){
			this.setPlayerX(10);
		}

		//draw location of player
		playerX=this.getPlayerX();
		playerY=this.getPlayerY();
		
	}
	private void checkAni(ImageIcon in){
		if(closeTimer>5){
			pac=iipC.getImage();
			if(openTimer>5){
			closeTimer=0;
			openTimer=0;
			}
			openTimer++;
		}
		else{
			pac = in.getImage();
			closeTimer++;
		}
	}
	private void load() {
		
		iipR = new ImageIcon(this.getClass().getResource("/img/pacR.png"));
		iipD = new ImageIcon(this.getClass().getResource("/img/pacD.png"));
		iipL = new ImageIcon(this.getClass().getResource("/img/pacL.png"));
		iipU = new ImageIcon(this.getClass().getResource("/img/pacU.png"));
		iipC = new ImageIcon(this.getClass().getResource("/img/close.png"));
		
		
		
	}
}
