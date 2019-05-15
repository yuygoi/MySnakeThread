package com.franklin.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.List;
import java.awt.Panel;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.franklin.domain.Position;
import com.franklin.snake.item.equipment.gun.bullet.BiasBullet;
import com.franklin.snake.item.equipment.gun.bullet.RailBullet;
import com.franklin.utils.KeyBoard;
import com.franklin.utils.settings.EnemySettings;
import com.franklin.utils.settings.SnakeSettings;


/**
*@author : 叶俊晖
*@date : 2019年4月27日上午9:28:14 
*/
public class TanA extends JFrame{
MyPanel2 mp=null;
	
	public static void main(String[] args) {
		new TanA();
	}
	
	public TanA(){
		RailBullet railBullet = new RailBullet();
//		BiasBullet biasBullet = new BiasBullet(KeyBoard.UP, 10);
		Thread thread = new Thread(railBullet);
		thread.start();
//		BiasBullet biasBullet2 = new BiasBullet(KeyBoard.UP, -10);
//		Thread thread2 = new Thread(biasBullet2);
//		thread2.start();
		mp=new MyPanel2();
//		mp.biasBullet=biasBullet;
//		mp.biasBullet2=biasBullet2;
		mp.railBullet=railBullet;
	    this.add(mp);
	    this.setSize(1280,720);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setVisible(true);
	    while (true) {

	    	mp.repaint();
		}
	}
	
}

class MyPanel extends JPanel{
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		/*
g.drawImage(SnakeSettings.BODY_IMAGE, body.getNow().getX(), body.getNow().getY(), SnakeSettings.size,SnakeSettings.size,this);
		 */
		int angle = 45;
		int x0 =400;
		int y0 =400;
		double tanA = Math.tan(getAngle());
		Vector<Position> list = new Vector<>();
		int b = (int) (x0 - y0/tanA);//b = y0/tan(pi+b/2)+x0
		// x = tanA*y + b
		for (int y = 0; y < y0; y+=SnakeSettings.size) {
			Position position = new Position((int) (tanA*y)+b, y);
			list.add(position);
		}
		tanA = -tanA;//b2 = x0 - y0/tan(b/2)  x = 
		int b2 = (int) (x0 - y0/tanA);
		for (int y = 0; y < y0; y+=SnakeSettings.size) {
			Position position = new Position((int) (tanA*y)+b2,y);
			list.add(position);
		}
		for (Position position : list) {
			g.drawImage(SnakeSettings.BODY_IMAGE, position.getX(), position.getY(), SnakeSettings.size,SnakeSettings.size,this);
		}
	}
	
	public static double getAngle(){
		return Math.PI*(EnemySettings.VISION_ANGLE/360.0);
	}
}

class MyPanel2 extends JPanel{
	
	BiasBullet biasBullet;
	BiasBullet biasBullet2;
	
	RailBullet railBullet;
	
	public MyPanel2() {
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Position position = railBullet.getBody().getNow();
//		g.drawImage(SnakeSettings.BODY_IMAGE, position.getX(), position.getY(), SnakeSettings.size,SnakeSettings.size,this);
//		position = biasBullet2.getBody().getNow();
//		g.drawImage(SnakeSettings.BODY_IMAGE, position.getX(), position.getY(), SnakeSettings.size,SnakeSettings.size,this);
		g.drawImage(railBullet.image, position.getX(), position.getY(), railBullet.width,railBullet.height,this);
		
	}
}
