package com.lifegame.utils;

import java.awt.Graphics;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class ImgPanel extends JPanel {
	private ImageIcon img = null;
	
	public ImgPanel() {
	}
	public ImgPanel(String imgPath) {
		File f = new File("");
		this.img = new ImageIcon(f.getAbsolutePath() + imgPath );
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(this.img.getImage(), 0, 0, this);
	}
	
}
