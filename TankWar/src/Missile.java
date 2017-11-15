import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
/**
 * �ڵ���
 * @author lwt
 *
 */
public class Missile {
	//�ڵ���λ��
	int x,y;
	
	/*
	 * �ڵ���x��y�����ϵ��ٶ��Լ��ڵ��Լ��ĳߴ�
	 */
	public static final int XSPEED=10;
	public static final int YSPEED=10;
	public static final int WIDTH=10;
	public static final int HEIGHT=10;
	
	//�ڵ�����ķ���Ӧ�ú�̹�˴�ʱ�ķ�����һ�µ�
	Tank.Direction dir;
	
	//��Ȼ��Ҫ�����ⷢ�ڵ���˭������ģ���������ͬ���ݵ�̹�������Լ�
	private boolean good;
	
	//�ڵ�����һֱ���ڣ�����Ӧ����һ������������������ڵ��Ƿ񻹴���
	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}
	
	private TankClient tc;

	/**
	 * ����Missile��
	 * @param x ��x�����λ��
	 * @param y ��y�����λ��
	 * @param dir ������̹��ͬ����
	 */
	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	/**
	 * �ع�missile��
	 * @param x ��x�����λ��
	 * @param y ��y�����λ��
	 * @param good ����˵�Ǻû�������˵���ж�����ڵ���˭�������
	 * @param dir dir ������̹��ͬ����
	 * @param tc
	 */
	public Missile(int x, int y,boolean good,Tank.Direction dir,TankClient tc){
		this(x,y,dir);
		this.good=good;
		this.tc=tc;
	}
	
	/**
	 * ��������ڵ�
	 * @param g ����
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
	 * �ƶ�����
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
		//���ڵ�������ˣ���˵����ڵ��Ѿ��������ˣ���Ҫ����һֱռ���ڴ�
		if(x<0||y<0||x>TankClient.GAME_WIDTH||y>TankClient.GAME_HEIGHT){
			live=false;
		}
	}
	
	//�õ������
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	/**
	 * �ڵ���̹�˵Ĵ���
	 * @param t
	 * @return
	 */
	public boolean hitTank(Tank t){
		if(this.live&&this.getRect().intersects(t.getRect()) && t.isLive()&&this.good!=t.isGood()){
			//�жϵ����ҷ�̹�˵�20Ѫ���ط�̹��ֱ�ӻ���
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
	 * �ڵ��򵽶��̹�˵Ĵ���������ڵ�������һ��̹��
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
	 * ��ǽ�Ĵ����ڵ����ܹ�ǽ����ǽ����ʧ
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


