package com.liftgame.test;

import org.junit.Test;

import com.lifegame.control.CreateWorld;

public class CreateWorldTest {
	public static void main(String[] args) {
		CreateWorld createWorld = new CreateWorld();
		boolean flag = createWorld.isVaildCell(400, 500);
		System.out.println(flag);
	}
}
