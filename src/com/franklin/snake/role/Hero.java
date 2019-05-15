package com.franklin.snake.role;

import java.awt.Image;

import com.franklin.domain.Position;
import com.franklin.snake.Snake;
import com.franklin.snake.Actions.Actions;
import com.franklin.snake.parts.Head;
import com.franklin.utils.KeyBoard;
import com.franklin.utils.Screen;
import com.franklin.utils.settings.SnakeSettings;

/**
*@author : 叶俊晖
*@date : 2019年4月23日下午6:43:18 
*/
public class Hero extends Snake{

	public Hero() {
		super(CreateRole.createHero());
	}

	public static int input;
	
	public void setInput(int input) {
		this.input = input;
	}
	
	private static final Image IMAGE = SnakeSettings.HERO_IMAGE;
	
	public static Image getImage() {
		return IMAGE;
	}

	@Override
	public void move() {
		this.head.setBefore(this.head.getNow());
		//玩家头部
		Position now = this.head.getNow();
		int x=0;
		int y=0;
		switch (this.head.getDirection()) {
		case KeyBoard.UP:
			x = now.getX();
			y = now.getY() - this.head.getSpeed();
			break;
		case KeyBoard.DOWN:
			x = now.getX();
			y = now.getY() + this.head.getSpeed();
			break;
		case KeyBoard.LEFT:
			x = now.getX()- this.head.getSpeed();
			y = now.getY();
			break;
		case KeyBoard.RIGHT:
			x = now.getX()+ this.head.getSpeed();
			y = now.getY();
			break;
		}
		Position position = new Position(x, y);
		this.head.setNow(position);
		super.moveBody();
		super.moveEquipment();
	}
	
	
}
