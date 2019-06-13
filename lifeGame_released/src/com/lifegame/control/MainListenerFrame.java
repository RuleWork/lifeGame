package com.lifegame.control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JOptionPane;

import com.lifegame.utils.BeanFactory;
import com.lifegame.view.InitFrame;


/**
 * 该类是主页面类，并通过内部类的方式共享同一个世界，监听该界面的所有事件
 * @author acer
 *
 */
public class MainListenerFrame extends JFrame implements MouseMotionListener {
	//仅创造一个唯一的世界，该世界既是一个panel也是一个线程，覆盖到当前主窗体上
	CreateWorld world = (CreateWorld)BeanFactory.createObject("createWorld");
	
	private final static JMenu location = new JMenu();

	public MainListenerFrame(int rows, int columns) {
		//初始化世界的行列数
		world = new CreateWorld(rows, columns);
		//设置该世界的背景颜色
		world.setBackground(new Color(248,248,255));
		
		//启动该世界，该世界将永久运行，直到强行关闭
		new Thread(world).start();
		//将该世界加入到主页面中
		this.add(world);
	}

	public class RandomActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.setBackground(new Color(248,248,255));
			world.start = false;
			world.clean = false;
			world.setRandom();
		}
	}

	public class StartActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.setBackground(new Color(248,248,255));
			world.start = false;
			world.clean = false;
			world.setShape();
		}
	}

	public class CleanActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.setBackground(new Color(248,248,255));
			world.start = false;
			world.clean = false;
			world.setStop();
		}
	}

	public class PauseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.setBackground(new Color(248,248,255));
			world.start = false;
			world.clean = false;
			world.setPause();
		}
	}

	public class SlowActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.changeSpeedSlow();
		}
	}

	public class FastActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.changeSpeedFast();
		}
	}

	public class HyperActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.changeSpeedHyper();
		}
	}

	public class HelpActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "这是生命游戏介绍  \n生命游戏是英国数学家约翰·何顿·康威在1970年发明的细胞自动机\n "
					+ "1．如果一个细胞周围有3个细胞为生，则该细胞为生;\n" + "2． 如果一个细胞周围有2个细胞为生，则该细胞的生死状态保持不变;\n" + "3． 在其它情况下，该细胞为死。");
		}
	}

	public class AboutActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "游戏作者：张领   许芳");
		}
	}

	public class KillActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.setBackground(new Color(248,248,255));
			world.setPause();
			world.clean = true;
			world.start = false;
		}
	}

	public class AddActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.setBackground(new Color(248,248,255));
			world.setPause();
			world.start = true;
			world.clean = false;
		}
	}
	
	public class BackActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			world.setBackground(new Color(248,248,255));
			world.setPause();
			world.start = false;
			world.clean = false;

			MainListenerFrame.this.dispose();
			new InitFrame();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (world.start) {
			int x = e.getX();
			int y = e.getY();
			//该50是上面的边框宽度，需要减去
			CreateWorld.pauseshape[(y - 50) / 20][x / 20] = 1;
			world.setDiy();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (world.clean) {
			int x = e.getX();
			int y = e.getY();
			CreateWorld.pauseshape[(y - 50) / 20][x / 20] = 0;
			world.setDiy();
		}
	}
}
