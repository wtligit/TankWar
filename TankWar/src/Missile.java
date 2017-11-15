import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
/**
 * 炮弹类
 * @author lwt
 *
 */
public class Missile {
	//炮弹的位置
	int x,y;
	
	/*
	 * 炮弹在x，y方向上的速度以及炮弹自己的尺寸
	 */
	public static final int XSPEED=10;
	public static final int YSPEED=10;
	public static final int WIDTH=10;
	public static final int HEIGHT=10;
	
	//炮弹打出的方向应该和坦克此时的方向是一致的
	Tank.Direction dir;
	
	//当然还要区分这发炮弹是谁打出来的，不能让相同阵容的坦克误伤自己
	private boolean good;
	
	//炮弹不能一直存在，所以应该有一个生死变量来看这个炮弹是否还存在
	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}
	
	private TankClient tc;

	/**
	 * 构造Missile类
	 * @param x 在x方向的位置
	 * @param y 在y方向的位置
	 * @param dir 方向，与坦克同方向
	 */
	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	/**
	 * 重构missile类
	 * @param x 在x方向的位置
	 * @param y 在y方向的位置
	 * @param good 与其说是好坏，不如说是判断这个炮弹是谁打出来的
	 * @param dir dir 方向，与坦克同方向
	 * @param tc
	 */
	public Missile(int x, int y,boolean good,Tank.Direction dir,TankClient tc){
		this(x,y,dir);
		this.good=good;
		this.tc=tc;
	}
	
	/**
	 * 画出这个炮弹
	 * @param g 画笔
	 */
	public void draw(Graphics g){
		if(!live){
			tc.missiles.remove(this);
			return;
		}
		
		Color c=g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		move();
	}
	
	/**
	 * 移动方法
	 */
	private void move() {
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
		//当炮弹打出界了，就说这个炮弹已经‘死’了，不要让它一直占着内存
		if(x<0||y<0||x>TankClient.GAME_WIDTH||y>TankClient.GAME_HEIGHT){
			live=false;
		}
	}
	
	//拿到这个块
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	/**
	 * 炮弹打到坦克的处理
	 * @param t
	 * @return
	 */
	public boolean hitTank(Tank t){
		if(this.live&&this.getRect().intersects(t.getRect()) && t.isLive()&&this.good!=t.isGood()){
			//判断到是我方坦克掉20血，地方坦克直接击毙
			if(t.isGood()){
				t.setLife(t.getLife()-20);
				if(t.getLife()<=0){
					t.setLive(false);
				}
			}else{
				t.setLive(false);
			}
			this.live=false;
			Explode e = new Explode(x, y, tc);
			tc.explodes.add(e);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 炮弹打到多个坦克的处理，看这个炮弹打到了哪一辆坦克
	 * @param tanks
	 * @return
	 */
	public boolean hitTanks(List<Tank> tanks){
		for(int i=0;i<tanks.size();i++){
			if(hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 打到墙的处理，炮弹不能过墙，打到墙就消失
	 * @param w
	 * @return
	 */
	public boolean hitWall(Wall w){
		if(this.live&&this.getRect().intersects(w.getRect())){
			this.live=false;
			return true;
		}
		return false;
	}
}


