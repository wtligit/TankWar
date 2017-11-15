import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.*;
/**
 * 坦克类
 * @author lwt
 *
 */
public class Tank {
	/**
	 * 坦克在x方向上的前进速度
	 */
	public static final int XSPEED=5;
	/**
	 * 坦克在y方向上的前进速度
	 */
	public static final int YSPEED=5;
	/**
	 * 坦克的宽度
	 */
	public static final int WIDTH=30;
	/**
	 * 坦克的高度
	 */
	public static final int HEIGHT=30;
	/**
	 * 血条的长度，血块能恢复到的血条长度
	 */
	public static final int BLOODNUM=100;
	/**
	 * 默认坦克是存活的，用live表示坦克的生死
	 */
	private boolean live=true;
	/*
	 * live的set和get
	 */
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	
	//坦克的血条
	private BloodBar bb = new BloodBar();
	/**
	 * 生命值长度为100
	 */
	private int life=BLOODNUM;
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}

	//拿到TankClient
	TankClient tc;
	/**
	 * 区分坦克的敌我
	 */
	private boolean good;	
	public boolean isGood() {
		return good;
	}
	
	/*
	 * 坦克的位置以及上一个位置
	 */
	private int x,y;
	private int oldX,oldY;
	
	//敌方坦克的移动需要随机
	private static Random r = new Random();
	
	//八个方向
	private boolean bL=false,bU=false,bR=false,bD=false;
	enum Direction{L,LU,U,RU,R,RD,D,LD,STOP};
	
	//默认让坦克不动
	private Direction dir = Direction.STOP;
	//炮筒的方向默认朝下
	private Direction ptDir = Direction.D;
	//步数step的初始化
	private int step = r.nextInt(12)+3;
	
	/**
	 * 坦克的构造函数
	 * @param x 坦克在x方向的位置
	 * @param y 坦克在y方向的位置
	 * @param good 坦克是敌方坦克还是我方坦克
	 */
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.oldX=x;
		this.oldY=y;
		this.good=good;
	}
	
	/**
	 * 重载坦克的构造函数
	 * @param x 坦克在x方向的位置
	 * @param y 坦克在y方向的位置
	 * @param good 坦克是敌方坦克还是我方坦克
	 * @param dir 坦克的方向
	 * @param tc TankClient
	 */
	public Tank(int x,int y,boolean good,Direction dir,TankClient tc){
		this(x,y,good);
		this.dir=dir;
		this.tc=tc;
	}
	
	/**
	 * 画出这辆坦克
	 * @param g 画笔
	 */
	public void draw(Graphics g){
		//如果这辆坦克挂了就remove它，当然地方坦克是直接remove，我方坦克还能复活的
		if(!live){
			if(!good){
				tc.tanks.remove(this);
			}
			return;
		}
		
		Color c = g.getColor();
		
		/*
		 * 我方坦克是红色敌方坦克是蓝色
		 */
		if(good){
			g.setColor(Color.RED);
		}else{
			g.setColor(Color.BLUE);
		}
		//实心的坦克
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		//给我方坦克画出血条
		if(good){
			bb.draw(g);
		} 
		/*
		 * 画出炮筒，炮筒方向跟随移动方向，炮弹发出的方向跟着炮筒的方向走
		 */
		switch(ptDir){
		case L:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y+Tank.HEIGHT/2);
			break;
		case LU:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y);
			break;
		case U:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y);
			break;
		case RU:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y);
			break;
		case R:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y+Tank.HEIGHT/2);
			break;
		case RD:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH, y+Tank.HEIGHT);
			break;
		case D:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x+Tank.WIDTH/2, y+Tank.HEIGHT);
			break;
		case LD:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HEIGHT/2, x, y+Tank.HEIGHT);
			break;

		}
		move();
	}
	
	/**
	 * 坦克怎么移动
	 */
	void move(){
		/**
		 * 记录坦克的上一个位置
		 */
		this.oldX=x;
		this.oldY=y;
		
		/**
		 * 随方向移动
		 */
		switch(dir){
		case L:
			x-=XSPEED;
			break;
		case LU:
			x-=XSPEED;
			y-=YSPEED;
			break;
		case U:
			y-=YSPEED;
			break;
		case RU:
			x+=XSPEED;
			y-=YSPEED;
			break;
		case R:
			x+=XSPEED;
			break;
		case RD:
			x+=XSPEED;
			y+=YSPEED;
			break;
		case D:
			y+=YSPEED;
			break;
		case LD:
			x-=XSPEED;
			y+=YSPEED;
			break;
		case STOP:
			break;
		}
		//让炮筒方向跟着移动方向
		if(this.dir!=Direction.STOP){
			this.ptDir=this.dir;
		}
		
		/**
		 * 坦克不能走出我们的主窗口
		 */
		if(x<0) x=0;
		if(y<30) y=30;
		if(x+Tank.WIDTH>TankClient.GAME_WIDTH) x=TankClient.GAME_WIDTH-Tank.WIDTH;
		if(y+Tank.HEIGHT>TankClient.GAME_HEIGHT) y=TankClient.GAME_HEIGHT-Tank.HEIGHT;
		
		/**
		 * 设计地方坦克的随机移动轨迹，为了让敌方坦克看起来比较正常，是在一个方向走几步以后才换方向，而不是让它们像一群热锅蚂蚁
		 */
		if(!good){
			Direction[] dirs = Direction.values();
			if(step==0){
				step=r.nextInt(12)+3;
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];
			}
			step--;
			if(r.nextInt(40)>38)
			this.fire();
		}
	}
	
	/**
	 * 下面做坦克和墙的碰撞检测时用到的私有方法，为了不让坦克粘在墙上，让坦克回到粘在墙上之前的位置
	 */
	private void stay(){
		x=oldX;
		y=oldY;
	}
	
	/**
	 * 处理按下键盘的相应
	 * @param e
	 */
	public void KeyPressed(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
		//如果我方坦克挂了，按下F2复活
		case KeyEvent.VK_F2:
			if(!this.live){
				this.live=true;
				this.life=BLOODNUM;
			}
			break;
		case KeyEvent.VK_LEFT:
			bL=true;
			break;
		case KeyEvent.VK_UP:
			bU=true;
			break;
		case KeyEvent.VK_RIGHT:
			bR=true;
			break;
		case KeyEvent.VK_DOWN:
			bD=true;
			break;
		}
		locateDirection();
	}
	
	/**
	 * 处理移动方向
	 */
	void locateDirection(){
		if(bL&&!bU&&!bR&&!bD) dir=Direction.L;
		else if(bL&&bU&&!bR&&!bD) dir=Direction.LU;
		else if(!bL&&bU&&!bR&&!bD) dir=Direction.U;
		else if(!bL&&bU&&bR&&!bD) dir=Direction.RU;
		else if(!bL&&!bU&&bR&&!bD) dir=Direction.R;
		else if(!bL&&!bU&&bR&&bD) dir=Direction.RD;
		else if(!bL&&!bU&&!bR&&bD) dir=Direction.D;
		else if(bL&&!bU&&!bR&&bD) dir=Direction.LD;
		else if(!bL&&!bU&&!bR&&!bD) dir=Direction.STOP;
	}

	/**
	 * 处理松开键盘的响应
	 * @param e
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL=false;
			break;
		case KeyEvent.VK_UP:
			bU=false;
			break;
		case KeyEvent.VK_RIGHT:
			bR=false;
			break;
		case KeyEvent.VK_DOWN:
			bD=false;
			break;
		case KeyEvent.VK_A:
			superFire();
			break;
		}
		locateDirection();
	}
	
	/**
	 * 坦克被炮弹打中的处理
	 * @return
	 */
	public Missile fire(){
		if(!live){
			return null;
		}
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		//new一个炮弹
		Missile m = new Missile(x, y, good,ptDir,this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	/**
	 * 重载这个开火的处理
	 * @param dir
	 * @return
	 */
	public Missile fire(Direction dir){
		if(!live){
			return null;
		}
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		//new一个炮弹
		Missile m = new Missile(x, y, good,dir,this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	/**
	 * 碰撞检测
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	/**
	 * 与墙的碰撞检测
	 * @param w
	 * @return
	 */
	public boolean collidesWithWall(Wall w){
		if(this.live&&this.getRect().intersects(w.getRect())){
			//如果坦克遇到墙，返回它的上一个位置
			this.stay();
			return true;
		}
		return false;
	}
	//坦克之间不能相撞
	public boolean collidesWiteTanks(java.util.List<Tank> tanks){
		for(int i=0;i<tanks.size();i++){
			Tank t = tanks.get(i);
			//两辆坦克不是同一辆坦克
			if(this!=t){
				if(this.live&&t.isLive()&&this.getRect().intersects(t.getRect())){
					this.stay();
					t.stay();
					return true;
				}				
			}
		}
		return false;
	}
	
	/**
	 * 做一个大招
	 */
	public void superFire(){
		Direction[] dirs = Direction.values();
		//大招是朝八个方向打子弹
		for(int i=0;i<8;i++){
			fire(dirs[i]);
		}
	}
	
	/**
	 * 血条
	 * @author lwt
	 *
	 */
	private class BloodBar{
		public void draw(Graphics g){
			Color c=g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y-10, WIDTH, 10);
			int w = WIDTH*life/100;
			g.fillRect(x, y-10, w, 10);
			g.setColor(c);
		}
	}
	/**
	 * 吃下血块的处理
	 * @param b
	 * @return
	 */
	public boolean eat(Blood b){
		//当坦克活着且血块还存在且坦克碰撞到血块时吃啊血块，然后加满血，血块消失
		if(this.live&&b.isLive()&&this.getRect().intersects(b.getRect())){
			this.life=BLOODNUM;
			b.setLive(false);
			return true;
		}
		return false;
	}
}
