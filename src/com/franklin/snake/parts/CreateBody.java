package com.franklin.snake.parts;

import java.util.List;
import java.util.Vector;

import com.franklin.utils.settings.SnakeSettings;

/**
*@author : 叶俊晖
*@date : 2019年4月20日下午2:02:48 
*/
public class CreateBody {
	
	

	public static Vector<Body> up(Head head,Vector<Body> bodies) {
		int x = head.getNow().getX();
		int y = head.getNow().getY()+SnakeSettings.size;
		for (int i = 0; i < SnakeSettings.DEFAULT_Length; i++) {
			bodies.add(new Body(x, y));
			y+=SnakeSettings.size;
		}
		return bodies;
	}
	public static Vector<Body> down(Head head,Vector<Body> bodies) {
		int x = head.getNow().getX();
		int y = head.getNow().getY()-SnakeSettings.size;
		for (int i = 0; i < SnakeSettings.DEFAULT_Length; i++) {
			bodies.add(new Body(x, y));
			y-=SnakeSettings.size;
		}
		return bodies;
	}
	public static Vector<Body> left(Head head,Vector<Body> bodies) {
		int x = head.getNow().getX()+SnakeSettings.size;
		int y = head.getNow().getY();
		for (int i = 0; i < SnakeSettings.DEFAULT_Length; i++) {
			bodies.add(new Body(x, y));
			x+=SnakeSettings.size;
		}
		return bodies;
	}
	public static Vector<Body> right(Head head,Vector<Body> bodies) {
		int x = head.getNow().getX()-SnakeSettings.size;
		int y = head.getNow().getY();
		for (int i = 0; i < SnakeSettings.DEFAULT_Length; i++) {
			bodies.add(new Body(x, y));
			x-=SnakeSettings.size;
		}
		return bodies;
	}
}
