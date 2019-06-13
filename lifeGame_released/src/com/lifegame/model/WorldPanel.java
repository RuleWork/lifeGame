package com.lifegame.model;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.lifegame.utils.CellStatus;

/**
 * ������ҳ��������
 * ֻ�����ʼ����ʼ����������
 * @author acer
 *
 */
public class WorldPanel extends JPanel{
	protected int rows;
	protected int columns;
	
	protected JLabel record;
	
	//�������ڲ���Ҫʹ�ã����Զ���ɹ�����
	public boolean start = false;//�Ƿ�ʼ
	public boolean clean = false;//�Ƿ����
	
	//������ͣʱ���ͼ�Σ�ȫ�ֶ�Ҫ��
	public static int pauseshape[][] = new int[100][100];

	protected int shape[][];
	protected int zero[][];
 	
	protected CellStatus[][] generation1;
	protected CellStatus[][] generation2;
	protected CellStatus[][] currentGeneration;
	protected CellStatus[][] nextGeneration;
	protected volatile boolean isChanging = false;

	/**
	 * ��ʼ������
	 * @param rows  ����
	 * @param columns  ����
	 */
	public WorldPanel(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;

		this.shape =  new int[rows][columns];
		this.zero = new int[rows][columns]; 
		
		//��ʾ��ǰ���ϸ���������ı�ǩ
		record = new JLabel();
		//����ǩ��ӵ�Panel��
		this.add(record);
		
		//��ʼ����һ��
		generation1 = new CellStatus[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				generation1[i][j] = CellStatus.Dead;
			}
		}
		//��ʼ����һ��
		generation2 = new CellStatus[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				generation2[i][j] = CellStatus.Dead;
			}
		}
		//��ֵ
		currentGeneration = generation1;
		nextGeneration = generation2;
	}
	
	public WorldPanel() {}
}
