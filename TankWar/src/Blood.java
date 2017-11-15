import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * Ѫ����
 * @author lwt
 *
 */
public class Blood {

	//Ѫ��Ӧ���ĸ�ֵ�������ֵ�λ��x,y�Լ�Ѫ��Ĵ�Сw,h
	int x,y,w,h;
	TankClient tc;
	
	//Ѫ��Ӧ�����˶�������Ӧ����һ����¼������step
	int step = 0;
	
	//Ѫ�鱻�Ե�Ӧ��Ҫ��ʧ������Ҫ��Ѫ����һ����ʾ����������Ĭ��Ϊtrue������
	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	//ָ��Ѫ���˶��Ĺ켣
	private int[][] pos = {
			{350,300},{360,300},{375,275},{400,200},{360,270},{340,280}
	};
	
	//��ʼ��
	public Blood(){
		x=pos[0][0];
		y=pos[0][1];
		w=h=15;
	}
	
	/**
	 * �������Ѫ��
	 * @param g ����
	 */
	public void draw(Graphics g){
		//Ѫ�鱻�Ե�ֱ��return������Ѫ����ʧ
		if(!live){
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		
		move();
	}

	//Ѫ�������涨���pos�����й��ɵ������˶�
	private void move() {
		step ++;
		if(step == pos.length){
			step = 0;
		}
		x = pos[step][0];
		y = pos[step][1];
	}
	
	/**
	 * Ѫ�����ײ���
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x,y,w,h);
	}
}
