package com.franklin.snake.role;

import com.franklin.domain.Position;
import com.franklin.snake.parts.Head;
import com.franklin.utils.KeyBoard;
import com.franklin.utils.Screen;
import com.franklin.utils.settings.SnakeSettings;

/**
*@author : 叶俊晖
*@date : 2019年4月23日下午8:39:42 
*/
public class CreateRole {
	
	public static Head createHero() {
		int x = (int) (Math.random()*(Screen.Width-Screen.layer)+Screen.layer/2);
		int y = (int) (Math.random()*(Screen.height-Screen.layer)+Screen.layer/2);
		return new Head(x, y);
	}
	
	public static Head createEnemy(Head hero,int direction) {
		int x = 0;
		int y = 0;
		boolean correct = false;
		do {
			x = (int) (Math.random()*(Screen.Width-Screen.layer)+Screen.layer/2);
			y = (int) (Math.random()*(Screen.height-Screen.layer)+Screen.layer/2);
			switch (direction) {
			case KeyBoard.UP:
				correct = correctUp(hero.getNow(),x,y);
				break;
			case KeyBoard.DOWN:
				correct = correctDown(hero.getNow(),x,y);
				break;
			case KeyBoard.LEFT:
				correct = correctLeft(hero.getNow(),x,y);
				break;
			case KeyBoard.RIGHT:
				correct = correctRight(hero.getNow(),x,y);
				break;
			}
		} while (!correct);
		
		return new Head(x, y);
	}

	private static boolean correctUp(Position now,int x,int y) {
		int size = SnakeSettings.size;
		int len = SnakeSettings.DEFAULT_Length;
		if ((now.getX()+size)>x&&(now.getX()<(x+size))
				&&(now.getY()+(len+1)*size>y)&&(y+size>now.getY())) {
			return false;
		}
		return true;
	}
	private static boolean correctDown(Position now,int x,int y) {
		int size = SnakeSettings.size;
		int len = SnakeSettings.DEFAULT_Length;
		if ((now.getX()+size)>x&&(now.getX()<(x+size))
				&&(now.getY()-len*size>y+size)&&(y<now.getY()+size)) {
			return false;
		}
		return true;
	}
	private static boolean correctLeft(Position now,int x,int y) {
		int size = SnakeSettings.size;
		int len = SnakeSettings.DEFAULT_Length;
		if ((now.getX()+(len+1)*size)>x&&(now.getX()<(x+size))
				&&(now.getY()+size>y)&&(y+size>now.getY())) {
			return false;
		}
		return true;
	}
	private static boolean correctRight(Position now,int x,int y) {
		int size = SnakeSettings.size;
		int len = SnakeSettings.DEFAULT_Length;
		if ((now.getX()-len*size)<x&&(now.getX()+size<x)
				&&(now.getY()+len*size>y)&&(y+size>now.getY())) {
			return false;
		}
		return true;
	}
}
