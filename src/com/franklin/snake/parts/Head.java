package com.franklin.snake.parts;

import com.franklin.domain.Position;
import com.franklin.snake.Actions.Actions;
import com.franklin.snake.properties.BasicProps;
import com.franklin.utils.Screen;
import com.franklin.utils.settings.SnakeSettings;

/**
*@author : 叶俊晖
*@date : 2019年4月23日下午2:45:41 
*/
public class Head extends BasicProps{

	private int direction = SnakeSettings.DEFAULT_Direction;
	
	

	public int getDirection() {
		return direction;
	}



	public void setDirection(int direction) {
		this.direction = direction;
	}



	public Head(int x,int y){
		this.now = new Position(x, y);
	}
	
}
