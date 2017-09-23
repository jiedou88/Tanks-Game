/*
 * function: Tank game 3.0
*/

package Tank3;
import java.awt.*;
import java.util.*;

import javax.swing.*;

import java.awt.event.*;
public class MyTankGame3 extends JFrame{
	MyPanel mp=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyTankGame3 mt=new MyTankGame3();
	}
	public MyTankGame3(){
		mp=new MyPanel();
		Thread t=new Thread(mp);
		t.start();
		this.add(mp);
		this.addKeyListener(mp);
		this.setSize(500, 400);
		this.setTitle("My Tank Game 1.0 ");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}

class MyPanel extends JPanel implements KeyListener,Runnable{
	Hero hero=null;
	Vector<Enemy> es=new Vector<Enemy>();
	int eSize=3;
	//draw panel by using a painter=Graphics g
	Image image1=null;
	Image image2=null;
	Image image3=null;
	Vector <Bomb> bombs=new Vector<Bomb>();
	//constructor
	public MyPanel(){
		image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tank_big.gif"));
		image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tank_medi.gif"));
		image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/tank_small.gif"));
		hero=new Hero(100,100);
		for (int i=0;i<eSize;i++){
			Enemy enemy=new Enemy((i+1)*50, 0);
			enemy.setColor(1);
			enemy.setDirection(2);
			es.add(enemy);
		}
		
	}
	//draw 
	public void paint(Graphics g){
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		if (hero.isLive) {
			//draw hero
			this.drawTank(hero.getX(), hero.getY(), g,
					this.hero.getDirection(), 0);
		}
		//draw Bomb Effect
		for (int i=0;i<bombs.size();i++){
			if(bombs.get(i).isLive){
				// 
				if(bombs.get(i).life>6){
					g.drawImage(image1, bombs.get(i).x, bombs.get(i).y,30,30,this);
				}else if(bombs.get(i).life>4){
					g.drawImage(image2, bombs.get(i).x, bombs.get(i).y,30,30,this);
				}else{
					g.drawImage(image3, bombs.get(i).x, bombs.get(i).y,30,30,this);
				}
				bombs.get(i).liftDown();
				if(bombs.get(i).life==0){
					bombs.remove(i);
				}
			}
		}
			

		//draw enemies
		for (int i=0;i<es.size();i++){
			if (es.get(i).isLive) {
				this.drawTank(es.get(i).getX(), es.get(i).getY(), g, es.get(i)
						.getDirection(), es.get(i).getColor());
			}
		}
		// draw hero multiple bullets
		for (int j = 0; j < hero.bullets.size(); j++) {
			Bullet herobullet = hero.bullets.get(j);
			// draw One bullet
			if (herobullet != null && herobullet.isLive) {
				g.draw3DRect(herobullet.x, herobullet.y, 1, 1, false);
			}
			if (herobullet.isLive==false){
				hero.bullets.remove(j);
			}
		}
	}
	//check whether bullet hit tanks
	public void isHitTank(Vector<Enemy> t,Vector<Bullet> bullets){
		
		for(int i=0;i<t.size();i++){//i tank
			for(int j=0;j<bullets.size();j++){//j bullet
				
				if (bullets.get(j).isLive) {
					switch (t.get(i).direction) {
					case 0:
					case 2:
						if (bullets.get(j).getX() >= t.get(i).getX()
								&& bullets.get(j).getX() <= (t.get(i).getX() + 20)
								&& bullets.get(j).getY() >= t.get(i).getY()
								&& bullets.get(j).getY() <= (t.get(i).getY() + 30)) {
							t.get(i).isLive = false;
							bullets.get(j).isLive = false;
							//create Bomb,add into Vector
							Bomb b=new Bomb(t.get(i).getX(),t.get(i).getY());
							bombs.add(b);
						}
						break;
					case 1:
					case 3:
						if (bullets.get(j).getX() >= t.get(i).getX()
								&& bullets.get(j).getX() <= (t.get(i).getX() + 30)
								&& bullets.get(j).getY() >= t.get(i).getY()
								&& bullets.get(j).getY() <= (t.get(i).getY() + 20)) {
							t.get(i).isLive = false;
							bullets.get(j).isLive = false;
							Bomb b=new Bomb(t.get(i).getX(),t.get(i).getY());
							bombs.add(b);
						}
						break;
					}//end switch
				}//end if
				
			}
		}
	}
	//draw tank function
	public void drawTank(int x,int y,Graphics g,int direct,int type){
		
		// enemy or hero
		switch(type){
			case 0:
				g.setColor(Color.cyan);
				break;
			case 1:
				g.setColor(Color.yellow);
				break;
			default:
				break;
		}
		
		// direction of bullet
		switch(direct){
		case 0:
			g.fill3DRect(x, y, 5, 30,false);
			g.fill3DRect((x+15), y, 5, 30,false);
			g.fill3DRect((x+5), y+5, 10, 20,false);
			g.fillOval((x+5), y+10, 9,10);
			g.drawLine(x+10, y+15,x+10,y);
			break;
		//right
		case 1:
			g.fill3DRect(x, y, 30, 5,false);
			g.fill3DRect(x, y+15, 30, 5,false);
			g.fill3DRect((x+5), y+5, 20, 10,false);
			g.fillOval((x+10), y+5, 9,10);
			g.drawLine(x+15, y+10,x+30,y+10);
			break;
		//down
		case 2:
			g.fill3DRect(x, y, 5, 30,false);
			g.fill3DRect((x+15), y, 5, 30,false);
			g.fill3DRect((x+5), y+5, 10, 20,false);
			g.fillOval((x+5), y+10, 9,10);
			g.drawLine(x+10, y+15,x+10,y+30);
			break;
		//left
		case 3:
			g.fill3DRect(x, y, 30, 5,false);
			g.fill3DRect(x, y+15, 30, 5,false);
			g.fill3DRect((x+5), y+5, 20, 10,false);
			g.fillOval((x+10), y+5, 9,10);
			g.drawLine(x+15, y+10,x,y+10);
			break;
			
	}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_S){
			hero.moveDown();
			this.hero.setDirection(2);
		}else if(e.getKeyCode()==KeyEvent.VK_W){
				hero.moveUp();
				this.hero.setDirection(0);
		}else if(e.getKeyCode()==KeyEvent.VK_A){
				
			    hero.moveLeft();
				this.hero.setDirection(3);
		}else if(e.getKeyCode()==KeyEvent.VK_D){
				
			    hero.moveRight();
				this.hero.setDirection(1);
		}
		if(e.getKeyCode()==KeyEvent.VK_J){
			this.hero.shotBullet();
		}
		super.repaint();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(50);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			isHitTank(es,hero.bullets);
			this.repaint();
		}
	}
	
	
}