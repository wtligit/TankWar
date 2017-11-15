import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
/**
 * 坦克游戏的主窗口
 * @author lwt
 *
 */
public class TankClient extends Frame {
	//常量要大写
	//常量一般是public static final
	/**
	 * 坦克游戏窗口的宽度
	 */
	public static final int GAME_WIDTH = 800;
	/**
	 * 坦克游戏窗口的高度
	 */
	public static final int GAME_HEIGHT = 800;
	
	//new一个我方主战坦克出来，并声明坦克的位置，true表示是好的坦克，初始时是静止状态，并传入this，this指针指的是当前正在访问的这段代码的对象
	Tank myTank = new Tank(50,50,true,Tank.Direction.STOP,this);
	
	/*
	 * new两堵墙出来，并指明这两堵墙的位置以及尺寸
	 */
	Wall w1 = new Wall(100,200,20,150,this);
	Wall w2 = new Wall(300,100,300,20,this);

	/*
	 * 用list来装坦克，子弹以及子弹产生的爆炸
	 */
	List<Tank> tanks = new ArrayList<Tank>();
	List<Missile> missiles = new ArrayList<Missile>();
	List<Explode> explodes = new ArrayList<Explode>();
	
	
	
	//做一张背景的虚拟图片
	Image offScreenImage = null;
	//血块
	Blood b = new Blood();
	
	/**
	 * 在主窗口上画东西出来的函数
	 */
	public void paint(Graphics g) {
		/*
		 * 指明子弹，爆炸，坦克的数量以及坦克的生命值
		 */
		g.drawString("missiles count:" + missiles.size(), 10, 50);
		g.drawString("explodes count:" + explodes.size(), 10, 70);
		g.drawString("tanks    count:" + tanks.size(), 10, 90);
		g.drawString("tanks     life:" + myTank.getLife(), 10, 110);
		
		//如果敌方坦克被打完了再生成一批
		if(tanks.size()<=0){
			for(int i = 0;i < 10;i++){
				tanks.add(new Tank(50+40*(i+1),50,false,Tank.Direction.D,this));
			}
		}

		//主窗口中子弹的操作
		for(int i = 0;i<missiles.size();i++){
			Missile m = missiles.get(i);
			//把子弹画出来
			m.draw(g);
			//子弹打到坦克上怎么样
			m.hitTank(myTank);
			m.hitTanks(tanks);
			//子弹不能达到墙上
			m.hitWall(w1);
			m.hitWall(w2);
		}
		
		//主窗口中子弹产生的爆炸效果
		for(int i = 0;i<explodes.size();i++){
			Explode e = explodes.get(i);
			//把爆炸效果画出来
			e.draw(g);
		}
		
		//主窗口中敌方坦克的行为
		for(int i = 0;i<tanks.size();i++){
			Tank t = tanks.get(i);
			/*
			 * 敌方坦克不能撞到两堵墙也不能互相撞在一起
			 */
			t.collidesWithWall(w1);
			t.collidesWithWall(w2);
			t.collidesWiteTanks(tanks);
			t.draw(g);
		}
		//画出我方主战坦克
		myTank.draw(g);
		//我方坦克迟到血块的行为
		myTank.eat(b);
		//画出血块
		b.draw(g);
		/*
		 * 画出两堵墙
		 */
		w1.draw(g);
		w2.draw(g);		
	}
	
	/**
	 * 刷新页面，为了主窗口刷新时不过分闪烁
	 */
	public void update(Graphics g) {
		if(offScreenImage == null){
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	/**
	 * 窗口
	 */
	public void lauchFrame(){
		//窗口一开始加载10辆敌方坦克
		for(int i = 0;i<10;i++){
			tanks.add(new Tank(50+40*(i+1),50,false,Tank.Direction.D,this));
		}
		//窗口距离屏幕左上角的位置
		this.setLocation(400, 300);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setTitle("TankWar");
		this.setBackground(Color.GREEN);
		//监听，关闭窗口
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}			
		});
		this.addKeyListener(new KeyMonitor());
		setVisible(true);
		//不允许窗口的大小改动
		setResizable(false);
		
		//启动这个线程重画，线程重画的比较均匀
		new Thread(new PaintThread()).start();
	}

	/**
	 * 主函数
	 * @param args
	 */
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.lauchFrame();
	}
	
	//用线程刷新
	private class PaintThread implements Runnable{
		public void run(){
			while(true){
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 键盘的监听
	 * @author lwt
	 *
	 */
	private class KeyMonitor extends KeyAdapter{
		//当按键抬起来的时候
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}
		//当按下按键的时候
		public void keyPressed(KeyEvent e) {
			//调用坦克类的按键响应
			myTank.KeyPressed(e);
		}		
	}
}
