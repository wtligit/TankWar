import java.awt.Color;
import java.awt.Graphics;
/**
 * ��ը�࣬���ڵ����в������ڵ�
 * @author lwt
 *
 */
public class Explode {
	//��ը���ֵ�λ��
	int x,y;
	private boolean live = true;
	
	private TankClient tc;
	
	//��ը������Բ�εİ뾶
	int[] diameter = {4,7,12,18,26,32,49,30,14,6};
	int step=0;
	
	/**
	 * ���챬ը�����
	 * @param x ���ֵĺ�����
	 * @param y ���ֵ�������
	 * @param tc �õ�TankClient��ָ��
	 */
	public Explode(int x,int y,TankClient tc){
		this.x=x;
		this.y=y;
		this.tc=tc;
	}
	
	/**
	 * ���������ը
	 * @param g ����
	 */
	public void draw(Graphics g){
		//ը���˾Ͱ������ը�Ƴ�
		if(!live){
			tc.explodes.remove(this);
			return;
		} 
		//��step�����ը���е��ĸ��׶���
		if(step==diameter.length){
			live=false;
			step=0;
			return;
		}
		
		Color c=g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x, y, diameter[step], diameter[step]);
		step++;
	}

}
