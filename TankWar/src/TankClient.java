import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
/**
 * ̹����Ϸ��������
 * @author lwt
 *
 */
public class TankClient extends Frame {
	//����Ҫ��д
	//����һ����public static final
	/**
	 * ̹����Ϸ���ڵĿ��
	 */
	public static final int GAME_WIDTH = 800;
	/**
	 * ̹����Ϸ���ڵĸ߶�
	 */
	public static final int GAME_HEIGHT = 800;
	
	//newһ���ҷ���ս̹�˳�����������̹�˵�λ�ã�true��ʾ�Ǻõ�̹�ˣ���ʼʱ�Ǿ�ֹ״̬��������this��thisָ��ָ���ǵ�ǰ���ڷ��ʵ���δ���Ķ���
	Tank myTank = new Tank(50,50,true,Tank.Direction.STOP,this);
	
	/*
	 * new����ǽ��������ָ��������ǽ��λ���Լ��ߴ�
	 */
	Wall w1 = new Wall(100,200,20,150,this);
	Wall w2 = new Wall(300,100,300,20,this);

	/*
	 * ��list��װ̹�ˣ��ӵ��Լ��ӵ������ı�ը
	 */
	List<Tank> tanks = new ArrayList<Tank>();
	List<Missile> missiles = new ArrayList<Missile>();
	List<Explode> explodes = new ArrayList<Explode>();
	
	
	
	//��һ�ű���������ͼƬ
	Image offScreenImage = null;
	//Ѫ��
	Blood b = new Blood();
	
	/**
	 * ���������ϻ����������ĺ���
	 */
	public void paint(Graphics g) {
		/*
		 * ָ���ӵ�����ը��̹�˵������Լ�̹�˵�����ֵ
		 */
		g.drawString("missiles count:" + missiles.size(), 10, 50);
		g.drawString("explodes count:" + explodes.size(), 10, 70);
		g.drawString("tanks    count:" + tanks.size(), 10, 90);
		g.drawString("tanks     life:" + myTank.getLife(), 10, 110);
		
		//����з�̹�˱�������������һ��
		if(tanks.size()<=0){
			for(int i = 0;i < 10;i++){
				tanks.add(new Tank(50+40*(i+1),50,false,Tank.Direction.D,this));
			}
		}

		//���������ӵ��Ĳ���
		for(int i = 0;i<missiles.size();i++){
			Missile m = missiles.get(i);
			//���ӵ�������
			m.draw(g);
			//�ӵ���̹������ô��
			m.hitTank(myTank);
			m.hitTanks(tanks);
			//�ӵ����ܴﵽǽ��
			m.hitWall(w1);
			m.hitWall(w2);
		}
		
		//���������ӵ������ı�ըЧ��
		for(int i = 0;i<explodes.size();i++){
			Explode e = explodes.get(i);
			//�ѱ�ըЧ��������
			e.draw(g);
		}
		
		//�������ез�̹�˵���Ϊ
		for(int i = 0;i<tanks.size();i++){
			Tank t = tanks.get(i);
			/*
			 * �з�̹�˲���ײ������ǽҲ���ܻ���ײ��һ��
			 */
			t.collidesWithWall(w1);
			t.collidesWithWall(w2);
			t.collidesWiteTanks(tanks);
			t.draw(g);
		}
		//�����ҷ���ս̹��
		myTank.draw(g);
		//�ҷ�̹�˳ٵ�Ѫ�����Ϊ
		myTank.eat(b);
		//����Ѫ��
		b.draw(g);
		/*
		 * ��������ǽ
		 */
		w1.draw(g);
		w2.draw(g);		
	}
	
	/**
	 * ˢ��ҳ�棬Ϊ��������ˢ��ʱ��������˸
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
	 * ����
	 */
	public void lauchFrame(){
		//����һ��ʼ����10���з�̹��
		for(int i = 0;i<10;i++){
			tanks.add(new Tank(50+40*(i+1),50,false,Tank.Direction.D,this));
		}
		//���ھ�����Ļ���Ͻǵ�λ��
		this.setLocation(400, 300);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setTitle("TankWar");
		this.setBackground(Color.GREEN);
		//�������رմ���
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}			
		});
		this.addKeyListener(new KeyMonitor());
		setVisible(true);
		//�������ڵĴ�С�Ķ�
		setResizable(false);
		
		//��������߳��ػ����߳��ػ��ıȽϾ���
		new Thread(new PaintThread()).start();
	}

	/**
	 * ������
	 * @param args
	 */
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.lauchFrame();
	}
	
	//���߳�ˢ��
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
	 * ���̵ļ���
	 * @author lwt
	 *
	 */
	private class KeyMonitor extends KeyAdapter{
		//������̧������ʱ��
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}
		//�����°�����ʱ��
		public void keyPressed(KeyEvent e) {
			//����̹����İ�����Ӧ
			myTank.KeyPressed(e);
		}		
	}
}
