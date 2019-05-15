package com.franklin.utils.settings;

import java.security.Key;

import com.franklin.domain.Position;
import com.franklin.snake.parts.Head;
import com.franklin.utils.KeyBoard;
import com.franklin.utils.Screen;

/**
*@author : 叶俊晖
*@date : 2019年4月24日下午2:33:00 
*/
public class GameRule {
	
	public static boolean isOutOfRange(Head head){
		Position now = head.getNow();
		if (now.getX()<SnakeSettings.size/3) {
			return true;
		}
		if (now.getY()<SnakeSettings.size/3) {
			return true;
		}
		if(now.getX()+3*SnakeSettings.size-SnakeSettings.size>Screen.Width){
			return true;
		}
		if(now.getY()+3*SnakeSettings.size+SnakeSettings.size/2>Screen.height){
			return true;
		}
		return false;
	}
	
	public static int isNearByRange(Head head){
		Position now = head.getNow();
		int size = SnakeSettings.size;
		int r = EnemySettings.VISION_DISTANCE+size;
		double angle = EnemySettings.VISION_ANGLE/360.0*Math.PI;
		int height = (int)(r*Math.sin(angle));
		if (now.getX()-height+size<0) {
			return KeyBoard.LEFT;
		}
		if (now.getY()-height+size<0) {
			return KeyBoard.UP;
		}
		if(now.getX()+height+size>Screen.Width){
			return KeyBoard.RIGHT;
		}
		if(now.getY()+height+2*size>Screen.height){
			return KeyBoard.DOWN;
		}
		return -1;
	}

}
