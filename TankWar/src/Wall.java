import java.awt.Graphics;
import java.awt.*;
/**
 * ǽ��
 * @author lwt
 *
 */
public class Wall {
	//���ǽӦ���г��ֵ�λ���Լ���Ⱥ͸߶�
	int x,y,w,h;
	TankClient tc;
	
	/**
	 * ����Wall�����
	 * @param x ��x�����λ��
	 * @param y ��y�����λ��
	 * @param w ǽ�Ŀ��
	 * @param h ǽ�ĸ߶�
	 * @param tc
	 */
	public Wall(int x, int y, int w, int h, TankClient tc) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tc = tc;
	}

	/**
	 * �������ǽ
	 * @param g
	 */
	public void draw(Graphics g){
		g.fillRect(x, y, w, h);
	}
	
	/**
	 * ��ײ���
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x,y,w,h);
	}

}
