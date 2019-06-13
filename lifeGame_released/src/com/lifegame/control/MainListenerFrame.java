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
 * ��������ҳ���࣬��ͨ���ڲ���ķ�ʽ����ͬһ�����磬�����ý���������¼�
 * @author acer
 *
 */
public class MainListenerFrame extends JFrame implements MouseMotionListener {
	//������һ��Ψһ�����磬���������һ��panelҲ��һ���̣߳����ǵ���ǰ��������
	CreateWorld world = (CreateWorld)BeanFactory.createObject("createWorld");
	
	private final static JMenu location = new JMenu();

	public MainListenerFrame(int rows, int columns) {
		//��ʼ�������������
		world = new CreateWorld(rows, columns);
		//���ø�����ı�����ɫ
		world.setBackground(new Color(248,248,255));
		
		//���������磬�����罫�������У�ֱ��ǿ�йر�
		new Thread(world).start();
		//����������뵽��ҳ����
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
			JOptionPane.showMessageDialog(null, "����������Ϸ����  \n������Ϸ��Ӣ����ѧ��Լ�����ζ١�������1970�귢����ϸ���Զ���\n "
					+ "1�����һ��ϸ����Χ��3��ϸ��Ϊ�������ϸ��Ϊ��;\n" + "2�� ���һ��ϸ����Χ��2��ϸ��Ϊ�������ϸ��������״̬���ֲ���;\n" + "3�� ����������£���ϸ��Ϊ����");
		}
	}

	public class AboutActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "��Ϸ���ߣ�����   ��");
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
			//��50������ı߿��ȣ���Ҫ��ȥ
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
