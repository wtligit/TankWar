import java.awt.Graphics;
import java.awt.*;
/**
 * 墙类
 * @author lwt
 *
 */
public class Wall {
	//这个墙应该有出现的位置以及宽度和高度
	int x,y,w,h;
	TankClient tc;
	
	/**
	 * 构造Wall这个类
	 * @param x 在x方向的位置
	 * @param y 在y方向的位置
	 * @param w 墙的宽度
	 * @param h 墙的高度
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
	 * 画出这个墙
	 * @param g
	 */
	public void draw(Graphics g){
		g.fillRect(x, y, w, h);
	}
	
	/**
	 * 碰撞检测
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x,y,w,h);
	}

}
