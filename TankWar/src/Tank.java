import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.*;
/**
 * ̹����
 * @author lwt
 *
 */
public class Tank {
	/**
	 * ̹����x�����ϵ�ǰ���ٶ�
	 */
	public static final int XSPEED=5;
	/**
	 * ̹����y�����ϵ�ǰ���ٶ�
	 */
	public static final int YSPEED=5;
	/**
	 * ̹�˵Ŀ��
	 */
	public static final int WIDTH=30;
	/**
	 * ̹�˵ĸ߶�
	 */
	public static final int HEIGHT=30;
	/**
	 * Ѫ���ĳ��ȣ�Ѫ���ָܻ�����Ѫ������
	 */
	public static final int BLOODNUM=100;
	/**
	 * Ĭ��̹���Ǵ��ģ���live��ʾ̹�˵�����
	 */
	private boolean live=true;
	/*
	 * live��set��get
	 */
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	
	//̹�˵�Ѫ��
	private BloodBar bb = new BloodBar();
	/**
	 * ����ֵ����Ϊ100
	 */
	private int life=BLOODNUM;
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}

	//�õ�TankClient
	TankClient tc;
	/**
	 * ����̹�˵ĵ���
	 */
	private boolean good;	
	public boolean isGood() {
		return good;
	}
	
	/*
	 * ̹�˵�λ���Լ���һ��λ��
	 */
	private int x,y;
	private int oldX,oldY;
	
	//�з�̹�˵��ƶ���Ҫ���
	private static Random r = new Random();
	
	//�˸�����
	private boolean bL=false,bU=false,bR=false,bD=false;
	enum Direction{L,LU,U,RU,R,RD,D,LD,STOP};
	
	//Ĭ����̹�˲���
	private Direction dir = Direction.STOP;
	//��Ͳ�ķ���Ĭ�ϳ���
	private Direction ptDir = Direction.D;
	//����step�ĳ�ʼ��
	private int step = r.nextInt(12)+3;
	
	/**
	 * ̹�˵Ĺ��캯��
	 * @param x ̹����x�����λ��
	 * @param y ̹����y�����λ��
	 * @param good ̹���ǵз�̹�˻����ҷ�̹��
	 */
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.oldX=x;
		this.oldY=y;
		this.good=good;
	}
	
	/**
	 * ����̹�˵Ĺ��캯��
	 * @param x ̹����x�����λ��
	 * @param y ̹����y�����λ��
	 * @param good ̹���ǵз�̹�˻����ҷ�̹��
	 * @param dir ̹�˵ķ���
	 * @param tc TankClient
	 */
	public Tank(int x,int y,boolean good,Direction dir,TankClient tc){
		this(x,y,good);
		this.dir=dir;
		this.tc=tc;
	}
	
	/**
	 * ��������̹��
	 * @param g ����
	 */
	public void draw(Graphics g){
		//�������̹�˹��˾�remove������Ȼ�ط�̹����ֱ��remove���ҷ�̹�˻��ܸ����
		if(!live){
			if(!good){
				tc.tanks.remove(this);
			}
			return;
		}
		
		Color c = g.getColor();
		
		/*
		 * �ҷ�̹���Ǻ�ɫ�з�̹������ɫ
		 */
		if(good){
			g.setColor(Color.RED);
		}else{
			g.setColor(Color.BLUE);
		}
		//ʵ�ĵ�̹��
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		//���ҷ�̹�˻���Ѫ��
		if(good){
			bb.draw(g);
		} 
		/*
		 * ������Ͳ����Ͳ��������ƶ������ڵ������ķ��������Ͳ�ķ�����
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
	 * ̹����ô�ƶ�
	 */
	void move(){
		/**
		 * ��¼̹�˵���һ��λ��
		 */
		this.oldX=x;
		this.oldY=y;
		
		/**
		 * �淽���ƶ�
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
		//����Ͳ��������ƶ�����
		if(this.dir!=Direction.STOP){
			this.ptDir=this.dir;
		}
		
		/**
		 * ̹�˲����߳����ǵ�������
		 */
		if(x<0) x=0;
		if(y<30) y=30;
		if(x+Tank.WIDTH>TankClient.GAME_WIDTH) x=TankClient.GAME_WIDTH-Tank.WIDTH;
		if(y+Tank.HEIGHT>TankClient.GAME_HEIGHT) y=TankClient.GAME_HEIGHT-Tank.HEIGHT;
		
		/**
		 * ��Ƶط�̹�˵�����ƶ��켣��Ϊ���õз�̹�˿������Ƚ�����������һ�������߼����Ժ�Ż����򣬶�������������һȺ�ȹ�����
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
	 * ������̹�˺�ǽ����ײ���ʱ�õ���˽�з�����Ϊ�˲���̹��ճ��ǽ�ϣ���̹�˻ص�ճ��ǽ��֮ǰ��λ��
	 */
	private void stay(){
		x=oldX;
		y=oldY;
	}
	
	/**
	 * �����¼��̵���Ӧ
	 * @param e
	 */
	public void KeyPressed(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
		//����ҷ�̹�˹��ˣ�����F2����
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
	 * �����ƶ�����
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
	 * �����ɿ����̵���Ӧ
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
	 * ̹�˱��ڵ����еĴ���
	 * @return
	 */
	public Missile fire(){
		if(!live){
			return null;
		}
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		//newһ���ڵ�
		Missile m = new Missile(x, y, good,ptDir,this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	/**
	 * �����������Ĵ���
	 * @param dir
	 * @return
	 */
	public Missile fire(Direction dir){
		if(!live){
			return null;
		}
		int x=this.x+Tank.WIDTH/2-Missile.WIDTH/2;
		int y=this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
		//newһ���ڵ�
		Missile m = new Missile(x, y, good,dir,this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	/**
	 * ��ײ���
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	/**
	 * ��ǽ����ײ���
	 * @param w
	 * @return
	 */
	public boolean collidesWithWall(Wall w){
		if(this.live&&this.getRect().intersects(w.getRect())){
			//���̹������ǽ������������һ��λ��
			this.stay();
			return true;
		}
		return false;
	}
	//̹��֮�䲻����ײ
	public boolean collidesWiteTanks(java.util.List<Tank> tanks){
		for(int i=0;i<tanks.size();i++){
			Tank t = tanks.get(i);
			//����̹�˲���ͬһ��̹��
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
	 * ��һ������
	 */
	public void superFire(){
		Direction[] dirs = Direction.values();
		//�����ǳ��˸�������ӵ�
		for(int i=0;i<8;i++){
			fire(dirs[i]);
		}
	}
	
	/**
	 * Ѫ��
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
	 * ����Ѫ��Ĵ���
	 * @param b
	 * @return
	 */
	public boolean eat(Blood b){
		//��̹�˻�����Ѫ�黹������̹����ײ��Ѫ��ʱ�԰�Ѫ�飬Ȼ�����Ѫ��Ѫ����ʧ
		if(this.live&&b.isLive()&&this.getRect().intersects(b.getRect())){
			this.life=BLOODNUM;
			b.setLive(false);
			return true;
		}
		return false;
	}
}
