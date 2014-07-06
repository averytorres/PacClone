import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class Level1 {

	int[][] L1 = new int[31][32];
	int width=32;
	int height=31;
	Random r = new Random();
	public Level1(){

		generateLevel();


	}
	public void generateLevel() {

		generatHatch();
		removeWalls();
		drawSeperation();
		makeGhostCell();


	}

	private void makeGhostCell() {
		for(int i=0;i<L1.length;i++){
			for(int j=0;j<L1[i].length;j++){
				if(i>height-20&&j>11&&i<height-13&&j<width-11){
					L1[i][j]=0;
				}
			}
		}
		L1[13][13]=1;
		L1[13][14]=1;
		L1[13][15]=1;
		//L1[13][16]=1;
		L1[13][17]=1;
		L1[13][18]=1;
		L1[13][19]=1;
		
		L1[14][13]=1;
		L1[15][13]=1;
		L1[16][13]=1;
		
		L1[16][14]=1;
		L1[16][15]=1;
		L1[16][16]=1;
		L1[16][17]=1;
		L1[16][18]=1;
		L1[16][19]=1;
		
		L1[14][19]=1;
		L1[15][19]=1;
		L1[16][19]=1;
		
		
	}
	private void drawSeperation() {
		L1[3][5]=1;
		L1[3][15]=1;
		L1[3][25]=1;
		
		L1[29][21]=1;
		L1[29][10]=1;
		
		L1[15][2]=1;
		L1[15][30]=1;
		
	}
	private void createBorder() {
		for(int i=2;i<height;i++){
			for(int j=1;j<width;j++){


				//border
				if(i==2)
					L1[i][j]= 1;
				if(i==height-1)
					L1[i][j]= 1;
				if(j==1)
					L1[i][j]= 1;
				if(j==width-1)
					L1[i][j]= 1;
			}
		}

	}
	private void removeWalls() {
		for(int i=3;i<L1.length-1;i++){
			for(int j=2;j<L1[i].length-1;j++){
				int top=L1[i-1][j];
				int bottom=L1[i+1][j];
				int left=L1[i][j-1];
				int right=L1[i][j+1];

				if(top+bottom+left+right>2){

					if(r.nextInt(10)>5)
						L1[i][j+1]=0;
					//					if(r.nextInt(10)>5)
					//						L1[i][j-1]=0;
					//					if(r.nextInt(10)>5)
					//						L1[i-1][j]=0;
					if(r.nextInt(10)>5)
						L1[i+1][j]=0;
				}
				if(top==1&&bottom==1&&left==1&&right==1){
					//					L1[i][j+1]=0;
					L1[i][j-1]=0;
					//					L1[i-1][j]=0;
					L1[i+1][j+1]=0;

				}
				if(top==1&&bottom==1&&left==1&&right==1){
					L1[i][j+1]=0;
					//					L1[i][j-1]=0;
					//					L1[i-1][j]=0;
					L1[i+1][j+1]=0;

				}
				if(top==1&&bottom==1&&left==1||top==1&&bottom==1&&right==1){
					if(r.nextInt(10)>4)
						L1[i][j+1]=0;

				}
			}

		}

		int len=r.nextInt(10-3)+3;
		for(int i=0;i<len;i++){
			L1[29][30-i]=0;
			L1[3+i][2]=0;

		}
		len=r.nextInt(8-4)+4;
		for(int i=0;i<len;i++){
			L1[3][2+i]=0;
			L1[29-i][30]=0;
		}
		len=r.nextInt(8-4)+4;
		for(int i=0;i<len;i++){
			L1[29][2+i]=0;
			L1[29-i][2]=0;
		}
		for(int i=3;i<29;i++){
			L1[i][30]=0;
			L1[i][2]=0;
		}
		for(int i=2;i<30;i++){
			L1[29][i]=0;
		}

		createBorder();

	}
	private void generatHatch() {

		for(int i=2;i<L1.length;i++){
			for(int j=1;j<L1[i].length;j++){
				if(j%2!=0)
					L1[i][j]=1;
				if(i%2==0)
					L1[i][j]=1;
			}
		}

	}
	public int check(int x,int y){

		return L1[x][y];
	}
	public static void main(String[] args){
		Level1 l = new Level1();

	}
	public void drawLevel(Graphics g) {
		for(int i=0;i<L1.length;i++){
			for(int j=0;j<L1[i].length;j++){

				if(this.check(i, j)==1){
					g.setColor(Color.GRAY);
					g.fillRect(j*15, i*15, 15, 15);

				}
				if(i==13&&j==13){
					g.setColor(Color.RED);
					g.fillRect(j*15, i*15, 15, 15);
				}


			}

		}

	}
}
