package com.lifegame.control;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import com.lifegame.model.WorldPanel;
import com.lifegame.utils.CellStatus;
import com.lifegame.utils.SpeedUtils;
/**
 * 核心业务逻辑世界,该类是一个一直启动的线程
 * 速度世界，画笔世界
 * 给主世界里加小世界的引用
 *
 * @author acer
 */
public class CreateWorld extends WorldPanel implements Runnable {
	//初始化速度为慢速
	private int speed = SpeedUtils.LOW;
	//
	private int lnum;
	
	public CreateWorld() {
	}
	
	public CreateWorld(int rows, int columns) {
		super(rows, columns);
	}
	
	/**
	 * 记录当前画板，以便暂停的时候显示
	 * @param generation
	 * @param pauseshape
	 */
	public void transfrom(CellStatus[][] generation, int pauseshape[][]) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (generation[i][j] == CellStatus.Active) {
					pauseshape[i][j] = 1;
				} else if (generation[i][j] == CellStatus.Dead) {
					pauseshape[i][j] = 0;
				}
			}
		}
	}

	/**
	 * 统计细胞存活的数量并在界面设置该值
	 */
	public void updateNumber() {
		String s = " 存活数量： " + lnum;
		record.setText(s);
	}

	/**
	 * 调整速度为慢速
	 */
	public void changeSpeedSlow() {
		speed = SpeedUtils.LOW;
	}
	
	/**
	 * 调整速度为快速
	 */
	public void changeSpeedFast() {
		speed = SpeedUtils.FAST;
	}

	/**
	 * 调整速度为极速
	 */
	public void changeSpeedHyper() {
		speed = SpeedUtils.HYPER;
	}

	/**
	 * 绘制组件
	 * @param Graphics 传入画笔 
	 * 该方法是一个线程方法，会出现线程安全问题，后续逻辑需要加锁
	 */
	public void paintComponent(Graphics g) {
		lnum = 0;
		super.paintComponent(g);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (currentGeneration[i][j] == CellStatus.Active) {
					Random random = new Random();
					g.setColor(new Color(127,255,0));
					g.fillRect(j * 20, i * 20, 20, 20);
					//每画一个生存的细胞，数量+1
					lnum++;
				} else {
					g.setColor(new Color(0,0,0));
					g.drawRect(j * 20, i * 20, 20, 20);
				}
			}
		}
	}

	/**
	 * 设置图形
	 */
	public void setShape() {
		setShape(shape);
	}

	public void setRandom() {
		Random a = new Random();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				//生成0或1随机数分配到shape数组中
				shape[i][j] = Math.abs(a.nextInt(2));
				pauseshape[i][j] = shape[i][j];
			}
		}
		setShapetemp(shape);
	}

	public void setZero() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				zero[i][j] = 0;
			}
		}
	}

	public void setStop() {
		setZero();
		shape = zero;
		setShape(shape);
		pauseshape = shape;
	}

	public void setPause() {
		shape = pauseshape;
		setShapetemp(pauseshape);
	}

	public void setDiy() {
		shape = pauseshape;
		setShapetemp(shape);
	}

	private void setShapetemp(int[][] shape) {
		isChanging = true;
		int arrowsRows = shape.length;
		int arrowsColumns = shape[0].length;
		int minimumRows = (arrowsRows < rows) ? arrowsRows : rows;
		int minimunColumns = (arrowsColumns < columns) ? arrowsColumns : columns;
		synchronized (this) {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					currentGeneration[i][j] = CellStatus.Dead;
				}
			}
			for (int i = 0; i < minimumRows; i++) {
				for (int j = 0; j < minimunColumns; j++) {
					if (shape[i][j] == 1) {
						currentGeneration[i][j] = CellStatus.Active;
					}
				}
			}
			repaint();
			updateNumber();
		}
	}

	/**
	 * 设置正确的图形，并初始化状态
	 * @param shape
	 */
	private void setShape(int[][] shape) {
		//正在准备绘制数据
		isChanging = true;
		int arrowsRows = shape.length;
		int arrowsColumns = shape[0].length;
		int minimumRows = (arrowsRows < rows) ? arrowsRows : rows;
		int minimunColumns = (arrowsColumns < columns) ? arrowsColumns : columns;
		synchronized (this) {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					currentGeneration[i][j] = CellStatus.Dead;
				}
			}
			for (int i = 0; i < minimumRows; i++) {
				for (int j = 0; j < minimunColumns; j++) {
					if (shape[i][j] == 1) {
						currentGeneration[i][j] = CellStatus.Active;
					}
				}
			}
			//准备初始化数据完成
			isChanging = false;
			//唤醒其他等待绘制的线程
			this.notifyAll();
		}
	}

	/**
	 * 核心方法，根据上一代的状态进化相应逻辑的下一代状态
	 * 坐标系由左上角开始
	 * @param x x坐标
	 * @param y y坐标
	 */
	public void evolve(int x, int y) {
		int activeSurroundingCell = 0;
		if (isVaildCell(x - 1, y - 1) && (currentGeneration[x - 1][y - 1] == CellStatus.Active)) {
			activeSurroundingCell++;
		}
		if (isVaildCell(x, y - 1) && (currentGeneration[x][y - 1] == CellStatus.Active)) {
			activeSurroundingCell++;
		}
		if (isVaildCell(x + 1, y - 1) && (currentGeneration[x + 1][y - 1] == CellStatus.Active)) {
			activeSurroundingCell++;
		}
		if (isVaildCell(x + 1, y) && (currentGeneration[x + 1][y] == CellStatus.Active)) {
			activeSurroundingCell++;
		}
		if (isVaildCell(x + 1, y + 1) && (currentGeneration[x + 1][y + 1] == CellStatus.Active)) {
			activeSurroundingCell++;
		}
		if (isVaildCell(x, y + 1) && (currentGeneration[x][y + 1] == CellStatus.Active)) {
			activeSurroundingCell++;
		}
		if (isVaildCell(x - 1, y + 1) && (currentGeneration[x - 1][y + 1] == CellStatus.Active)) {
			activeSurroundingCell++;
		}
		if (isVaildCell(x - 1, y) && (currentGeneration[x - 1][y] == CellStatus.Active)) {
			activeSurroundingCell++;
		}
		
		if (activeSurroundingCell == 3) {
			nextGeneration[x][y] = CellStatus.Active;
		} else if (activeSurroundingCell == 2) {
			nextGeneration[x][y] = currentGeneration[x][y];
		} else {
			nextGeneration[x][y] = CellStatus.Dead;
		}
	}

	/**
	 * 验证细胞的位置是否合法
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isVaildCell(int x, int y) {
		if ((x >= 0) && (x < rows) && (y >= 0) && (y < columns)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 代数更新之间的时间间距
	 * @param x
	 */
	private void sleep(int x) {
		try {
			Thread.sleep(80 * x);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * world线程的run方法
	 */
	@Override
	public void run() {
		while (true) {
			synchronized (this) {
				//如果画笔正在画，就让当前线程等待
				while (isChanging) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				//控制线程睡眠的时间从而达到控制速度的效果
				sleep(speed);
				//根据本代生成下一代是数据
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < columns; j++) {
						evolve(i, j);
					}
				}
				//交替状态
				CellStatus[][] temp = null;
				temp = currentGeneration;
				currentGeneration = nextGeneration;
				nextGeneration = temp;
				
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < columns; j++) {
						nextGeneration[i][j] = CellStatus.Dead;
					}
				}
				
				transfrom(currentGeneration, pauseshape);
				repaint();
				updateNumber();
			}
		}
	}
}
