package com.franklin.snake.properties;

import com.franklin.domain.Position;
import com.franklin.utils.settings.SnakeSettings;

/**
*@author : 叶俊晖
*@date : 2019年4月23日下午2:49:54 
*/
public class BasicProps {
	
	protected int direction;
	
	public boolean isDead = false;
	
	protected int speed = SnakeSettings.speed_level*SnakeSettings.size;

	public int getSpeed() {
		return speed;
	}
	
	protected Position before = new Position();
	
	protected Position now = new Position();

	public Position getBefore() {
		return before;
	}

	public void setBefore(Position before) {
		this.before = before;
	}

	public Position getNow() {
		return now;
	}

	public void setNow(Position now) {
		this.now = now;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	
	
}
