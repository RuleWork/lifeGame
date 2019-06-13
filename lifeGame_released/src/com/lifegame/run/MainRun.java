package com.lifegame.run;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.lifegame.view.InitFrame;

/**
 * Ö÷Æô¶¯Àà
 * @param args
 */
public class MainRun {
	
	public static void main(String[] args) {
		try {
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}
		new InitFrame();
	}
}
