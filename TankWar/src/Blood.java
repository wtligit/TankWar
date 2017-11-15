import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * 血块类
 * @author lwt
 *
 */
public class Blood {

	//血块应有四个值，即出现的位置x,y以及血块的大小w,h
	int x,y,w,h;
	TankClient tc;
	
	//血块应该是运动的所以应该有一个记录步数的step
	int step = 0;
	
	//血块被吃掉应该要消失，所以要给血块有一个表示生死的量，默认为true即活着
	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	//指明血块运动的轨迹
	private int[][] pos = {
			{350,300},{360,300},{375,275},{400,200},{360,270},{340,280}
	};
	
	//初始化
	public Blood(){
		x=pos[0][0];
		y=pos[0][1];
		w=h=15;
	}
	
	/**
	 * 画出这个血块
	 * @param g 画笔
	 */
	public void draw(Graphics g){
		//血块被吃掉直接return，即让血块消失
		if(!live){
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		
		move();
	}

	//血块以上面定义的pos来做有规律的往复运动
	private void move() {
		step ++;
		if(step == pos.length){
			step = 0;
		}
		x = pos[step][0];
		y = pos[step][1];
	}
	
	/**
	 * 血块的碰撞检测
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x,y,w,h);
	}
}
