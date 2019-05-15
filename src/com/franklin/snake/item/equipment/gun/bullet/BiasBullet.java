package com.franklin.snake.item.equipment.gun.bullet;

import com.franklin.domain.Position;
import com.franklin.snake.Snake;
import com.franklin.snake.item.eatthings.Potion;
import com.franklin.snake.parts.Body;
import com.franklin.utils.KeyBoard;
import com.franklin.utils.Screen;
import com.franklin.utils.settings.EnemySettings;
import com.franklin.utils.settings.ShootSettings;

/**
*@author : 叶俊晖
*@date : 2019年4月30日上午11:49:37 
*/
public class BiasBullet extends Shoot{
	
	
	private int angle;

	private double tanA;
	
	public BiasBullet(int direction,Position head,Snake snake,int angle) {
		super(snake);
		this.angle = angle;
		this.tanA = Math.tan(getAngle());
		this.direction = direction;
		this.head = head;
	}
	
	public BiasBullet(int direction,int angle) {
		body = new Body(500, 500);
		this.angle = angle;
	}

	@Override
	public void move() {
		x= body.getNow().getX();
		y= body.getNow().getY();
		switch (direction) {
		case KeyBoard.UP:
			x += moving*tanA;
			y -= moving;
			
			break;
		case KeyBoard.DOWN:
			x -= moving*tanA;
			y += moving;
			break;
		case KeyBoard.LEFT:
			x -= moving;
			y -= moving*tanA;
			break;
		case KeyBoard.RIGHT:
			x += moving;
			y += moving*tanA;
			break;
		}
		body.setNow(new Position(x, y));
	}
	
	private double getAngle(){
		return Math.PI*(angle/180.0);
	}
}
