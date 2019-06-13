package com.lifegame.control;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import com.lifegame.model.WorldPanel;
import com.lifegame.utils.CellStatus;
import com.lifegame.utils.SpeedUtils;
/**
 * ����ҵ���߼�����,������һ��һֱ�������߳�
 * �ٶ����磬��������
 * �����������С���������
 *
 * @author acer
 */
public class CreateWorld extends WorldPanel implements Runnable {
	//��ʼ���ٶ�Ϊ����
	private int speed = SpeedUtils.LOW;
	//
	private int lnum;
	
	public CreateWorld() {
	}
	
	public CreateWorld(int rows, int columns) {
		super(rows, columns);
	}
	
	/**
	 * ��¼��ǰ���壬�Ա���ͣ��ʱ����ʾ
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
	 * ͳ��ϸ�������������ڽ������ø�ֵ
	 */
	public void updateNumber() {
		String s = " ��������� " + lnum;
		record.setText(s);
	}

	/**
	 * �����ٶ�Ϊ����
	 */
	public void changeSpeedSlow() {
		speed = SpeedUtils.LOW;
	}
	
	/**
	 * �����ٶ�Ϊ����
	 */
	public void changeSpeedFast() {
		speed = SpeedUtils.FAST;
	}

	/**
	 * �����ٶ�Ϊ����
	 */
	public void changeSpeedHyper() {
		speed = SpeedUtils.HYPER;
	}

	/**
	 * �������
	 * @param Graphics ���뻭�� 
	 * �÷�����һ���̷߳�����������̰߳�ȫ���⣬�����߼���Ҫ����
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
					//ÿ��һ�������ϸ��������+1
					lnum++;
				} else {
					g.setColor(new Color(0,0,0));
					g.drawRect(j * 20, i * 20, 20, 20);
				}
			}
		}
	}

	/**
	 * ����ͼ��
	 */
	public void setShape() {
		setShape(shape);
	}

	public void setRandom() {
		Random a = new Random();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				//����0��1��������䵽shape������
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
	 * ������ȷ��ͼ�Σ�����ʼ��״̬
	 * @param shape
	 */
	private void setShape(int[][] shape) {
		//����׼����������
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
			//׼����ʼ���������
			isChanging = false;
			//���������ȴ����Ƶ��߳�
			this.notifyAll();
		}
	}

	/**
	 * ���ķ�����������һ����״̬������Ӧ�߼�����һ��״̬
	 * ����ϵ�����Ͻǿ�ʼ
	 * @param x x����
	 * @param y y����
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
	 * ��֤ϸ����λ���Ƿ�Ϸ�
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
	 * ��������֮���ʱ����
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
	 * world�̵߳�run����
	 */
	@Override
	public void run() {
		while (true) {
			synchronized (this) {
				//����������ڻ������õ�ǰ�̵߳ȴ�
				while (isChanging) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				//�����߳�˯�ߵ�ʱ��Ӷ��ﵽ�����ٶȵ�Ч��
				sleep(speed);
				//���ݱ���������һ��������
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < columns; j++) {
						evolve(i, j);
					}
				}
				//����״̬
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
