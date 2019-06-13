package com.lifegame.model;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.lifegame.utils.CellStatus;

/**
 * 主世界页面的面板类
 * 只负责初始化初始两代的数据
 * @author acer
 *
 */
public class WorldPanel extends JPanel{
	protected int rows;
	protected int columns;
	
	protected JLabel record;
	
	//监听的内部类要使用，所以定义成公共的
	public boolean start = false;//是否开始
	public boolean clean = false;//是否清除
	
	//保存暂停时候的图形，全局都要用
	public static int pauseshape[][] = new int[100][100];

	protected int shape[][];
	protected int zero[][];
 	
	protected CellStatus[][] generation1;
	protected CellStatus[][] generation2;
	protected CellStatus[][] currentGeneration;
	protected CellStatus[][] nextGeneration;
	protected volatile boolean isChanging = false;

	/**
	 * 初始化世界
	 * @param rows  行数
	 * @param columns  列数
	 */
	public WorldPanel(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;

		this.shape =  new int[rows][columns];
		this.zero = new int[rows][columns]; 
		
		//显示当前存活细胞的数量的标签
		record = new JLabel();
		//将标签添加到Panel上
		this.add(record);
		
		//初始化第一代
		generation1 = new CellStatus[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				generation1[i][j] = CellStatus.Dead;
			}
		}
		//初始化下一代
		generation2 = new CellStatus[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				generation2[i][j] = CellStatus.Dead;
			}
		}
		//赋值
		currentGeneration = generation1;
		nextGeneration = generation2;
	}
	
	public WorldPanel() {}
}
