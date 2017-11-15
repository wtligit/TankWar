import java.awt.Color;
import java.awt.Graphics;
/**
 * 爆炸类，即炮弹击中产生的炮弹
 * @author lwt
 *
 */
public class Explode {
	//大爆炸出现的位置
	int x,y;
	private boolean live = true;
	
	private TankClient tc;
	
	//大爆炸产生的圆形的半径
	int[] diameter = {4,7,12,18,26,32,49,30,14,6};
	int step=0;
	
	/**
	 * 构造爆炸这个类
	 * @param x 出现的横坐标
	 * @param y 出现的纵坐标
	 * @param tc 拿到TankClient的指针
	 */
	public Explode(int x,int y,TankClient tc){
		this.x=x;
		this.y=y;
		this.tc=tc;
	}
	
	/**
	 * 画出这个爆炸
	 * @param g 画笔
	 */
	public void draw(Graphics g){
		//炸完了就把这个爆炸移除
		if(!live){
			tc.explodes.remove(this);
			return;
		} 
		//用step管理大爆炸进行到哪个阶段了
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
