package com.franklin;

import javax.swing.JFrame;
import com.franklin.utils.MyKeyListener;
import com.franklin.utils.Screen;

/**
*@author : 叶俊晖
*@date : 2019年4月23日下午7:26:33 
*/
public class GameApplication extends JFrame{

	public static void main(String[] args) {
		new GameApplication();
	}
	
	public GameApplication(){
		MyKeyListener keyListener =new MyKeyListener();
		Thread thread = new Thread(keyListener);
		thread.start();
	    this.add(keyListener);
	    this.addKeyListener(keyListener);
	    this.setSize(Screen.Width,Screen.height);
	    this.setVisible(true);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
