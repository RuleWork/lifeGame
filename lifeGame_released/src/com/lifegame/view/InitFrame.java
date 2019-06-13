package com.lifegame.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.lifegame.control.MainListenerFrame;
import com.lifegame.utils.ImgPanel;


public class InitFrame extends JFrame implements ActionListener {
	//主界面窗体
	private MainListenerFrame frame = null;

	private JPanel bodyPanel = null;
	private JPanel centerPanel = null;
	private JPanel bottomPanel = null;

	private JLabel rowLabel = null;
	private JTextField rowField = null;
	private JLabel columnLabel = null;
	private JTextField columnField = null;

	private JButton startButton = null;
	private JButton resetButton = null;

	private void init() {
		bodyPanel = (JPanel) this.getContentPane();
		bodyPanel.setLayout(new BorderLayout());

		//添加背景图片
		this.centerPanel = new ImgPanel("./welcome.jpg");

		this.bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));// 水平间隔，垂直间隔

		this.rowLabel = new JLabel("网格行数：", JLabel.RIGHT);
		this.rowField = new JTextField(16);
		this.columnLabel = new JLabel("网格列数：", JLabel.RIGHT);
		this.columnField = new JTextField(16);

		Box box0 = Box.createVerticalBox();
		Box box1 = Box.createHorizontalBox();
		Box box2 = Box.createHorizontalBox();

		box1.add(this.rowLabel);
		box1.add(this.rowField);

		box2.add(this.columnLabel);
		box2.add(this.columnField);

		box0.add(Box.createVerticalStrut(250));
		box0.add(box1);
		box0.add(Box.createVerticalStrut(25));
		box0.add(box2);
		this.centerPanel.add(box0);

		this.startButton = new JButton("开始");
		this.startButton.addActionListener(this);
		this.resetButton = new JButton("重置");
		this.resetButton.addActionListener(this);

		this.bottomPanel.add(this.startButton);
		this.bottomPanel.add(this.resetButton);

		bodyPanel.add(this.centerPanel, BorderLayout.CENTER);
		bodyPanel.add(this.bottomPanel, BorderLayout.SOUTH);

		//给开始键设置回车快捷键
		this.startButton.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		this.setTitle("初始化网格");
		this.setSize(828, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public InitFrame() {
		this.init();
	}
	
	/**
	 * 判断输入的行列是否为空
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean checkBlank(String row, String column) {
		if (row == null || row.equals("") || column == null || column.equals("")) {
			JOptionPane.showMessageDialog(this.bodyPanel, "输入不能为空", "提示框", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	
	/**
	 * 设置窗体居中显示
	 */
	private void setCenter() {
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width / 2; // 获取屏幕的宽
		int screenHeight = screenSize.height / 2; // 获取屏幕的高
		int height = this.getHeight();
		int width = this.getWidth();
		setLocation(screenWidth - width / 2, screenHeight - height / 2);
	}
	
	/**
	 * 初始化菜单
	 */
	private void initMenu() {
		frame.addMouseMotionListener(frame);
		JMenuBar menu = new JMenuBar();
		frame.setJMenuBar(menu);
		
		JMenu options = new JMenu("Options");
		menu.add(options);
		JMenu changeSpeed = new JMenu("ChangeSpeed");
		menu.add(changeSpeed);
		JMenu other = new JMenu("Other");
		menu.add(other);

		JMenuItem start = options.add("Start");
		start.addActionListener(frame.new StartActionListener());
		JMenuItem random = options.add("Random");
		random.addActionListener(frame.new RandomActionListener());

		JMenuItem clean = options.add("Clean");
		clean.addActionListener(frame.new CleanActionListener());
		JMenuItem pause = options.add("Pause");
		pause.addActionListener(frame.new PauseActionListener());
		JMenuItem add = options.add("Add");
		add.addActionListener(frame.new AddActionListener());
		JMenuItem kill = options.add("Kill");
		kill.addActionListener(frame.new KillActionListener());

		JMenuItem slow = changeSpeed.add("Slow");
		slow.addActionListener(frame.new SlowActionListener());
		JMenuItem fast = changeSpeed.add("Fast");
		fast.addActionListener(frame.new FastActionListener());
		JMenuItem hyper = changeSpeed.add("Hyper");
		hyper.addActionListener(frame.new HyperActionListener());

		JMenuItem help = other.add("Help");
		help.addActionListener(frame.new HelpActionListener());

		JMenuItem about = other.add("About");
		about.addActionListener(frame.new AboutActionListener());
		
		JMenuItem back = other.add("Back");
		back.addActionListener(frame.new BackActionListener());
	}
	
	/**
	 * 初始化主页面窗体
	 * @param row
	 * @param col
	 */
	private void initMainListenerFrame(int row, int col) {
		this.frame = new MainListenerFrame(row, col);
		
		//初始化菜单
		this.initMenu();
		//居中显示
		this.setCenter();
		
		//默认操作模式
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置大小
		this.frame.setSize(1900, 1000);
		//设置标题
		this.frame.setTitle("Game of Life");
		//设置可见
		this.frame.setVisible(true);
		//设置大小不可变
		this.frame.setResizable(false);
		//让当前窗口消失
		this.dispose();
	}
	
	/**
	 * 监听开始和重置button的事件
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.startButton) {
			String rowStr = this.rowField.getText();
			String columnStr = this.columnField.getText();
			// 判断是否为空
			boolean flag = checkBlank(rowStr, columnStr);
			// 判断是否为数字，如果是数字是否都是正数
			try {
				int row = Integer.parseInt(rowStr);
				int col = Integer.parseInt(columnStr);

				if (flag && row > 0 && col > 0) {
					initMainListenerFrame(row, col);
				}
			} catch (Exception exception) {
				JOptionPane.showMessageDialog(this.bodyPanel, "请输入数字", "提示框", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
		if (e.getSource() == this.resetButton) {
			this.rowField.setText("");
			this.columnField.setText("");
		}
	}
}
