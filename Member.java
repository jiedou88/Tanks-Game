package Tank3;
import java.util.*;
class Tank{
	int x=0;
	int y=0;
	int direction=0;
	int color;
	boolean isLive=true;
	
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	// up
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	//construct the father tank
	public Tank(int x,int y){
		this.x=x;
		this.y=y;
	}
	
}
//Father Tank,and show the position in My Panel
class Enemy extends Tank{
	int speed=1;
	public Enemy(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
}
class Hero extends Tank {
	int speed=1;
	Bullet bul=null;
	Vector<Bullet> bullets=new Vector<Bullet>();
	public Hero(int x,int y){
		super(x,y);
	}
	// shot action 
	public void shotBullet(){
		
		
		switch(this.getDirection()){
			case 0: 
				bul=new Bullet(this.x+10,this.y);
				break;
			case 1:
				bul=new Bullet(this.x+30,this.y+10);
				break;
			case 2:
				bul=new Bullet(this.x+10,this.y+30);
				break;
			case 3:
				bul=new Bullet(this.x,this.y+10);
				break;
		}
		
		bul.direction=this.getDirection();
				if (bullets.size()<=5) {
			bullets.add(bul);
		}
	
		Thread t=new Thread(bul);
		t.start();
	}
	public void moveUp(){
		this.y-=speed;
	}
	
	public void moveDown(){
		this.y+=speed;
	}
	public void moveRight(){
		this.x+=speed;
	}
	public void moveLeft(){
		this.x-=speed;
	}
}
class Bomb{
	int x;
	int y;
	int life=9;// call different pics
	boolean isLive=true;
	public Bomb(int x,int y){
		this.x=x;
		this.y=y;
	}
	public void liftDown(){
		if(life>0){
			life--;
		}else{
			isLive=false;
		}
	}
}
class Bullet implements Runnable{
	int x;
	
	int y;
	int speed=1;
	int direction=0;
	boolean isLive=true;
	public Bullet(int x,int y){
		this.x=x;
		this.y=y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			
				e.printStackTrace();
				}
		
			switch(this.direction){
				case 0:
					y-=speed;
					break;
				case 1:
					x+=speed;
					break;
				case 2:
					y+=speed;
					break;
				case 3:
					x-=speed;
					break;
			
			}
			//System.out.println("Debug :bullet x="+x+"y="+y);
			
			if(this.x>=400 || this.x<=0 ||
					this.y>=300 || this.y<=0){
				isLive=false;
				break;
			}
         }
	}
}