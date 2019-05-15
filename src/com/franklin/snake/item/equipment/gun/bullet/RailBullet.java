package com.franklin.snake.item.equipment.gun.bullet;

import java.awt.Image;

import com.franklin.domain.Position;
import com.franklin.snake.Snake;
import com.franklin.snake.item.equipment.gun.GunSettings;
import com.franklin.snake.parts.Body;
import com.franklin.snake.role.Enemy;
import com.franklin.snake.role.Hero;
import com.franklin.utils.KeyBoard;
import com.franklin.utils.MyKeyListener;
import com.franklin.utils.settings.ShootSettings;

/**
*@author : 叶俊晖
*@date : 2019年5月1日上午9:51:39 
*/
public class RailBullet extends Shoot {
	
	public int width;
	public int height;
	public Image image;
	
	private int m;
	private int n;
	
	/**
	 * @param snake
	 * @param width
	 * @param height
	 */
	public RailBullet(Snake snake) {
		super(snake);
		width = GunSettings.RAILGUN_SIZE;
		height = GunSettings.RAILGUN_SIZE;
		moving = moving+moving/4; 
		initRailBulllet();
		m=head.getX()-width/4;
		n=head.getY()-width/4;
	}
	public RailBullet() {
		width = GunSettings.RAILGUN_SIZE;
		height = GunSettings.RAILGUN_SIZE;
		moving *=2; 
		this.direction = KeyBoard.UP;
		head = new Position(600, 600);
		body = new Body(600, 600);
		initRailBulllet();
	}
	
	private void initRailBulllet() {
		switch (direction) {
		case KeyBoard.UP:		
		case KeyBoard.DOWN:
			this.image = GunSettings.RAILCOL_IMAGE;	
			break;
		case KeyBoard.LEFT:		
		case KeyBoard.RIGHT:
			this.image = GunSettings.RAILROW_IMAGE;
			break;
		}
	}
	
	@Override
	public void move() {
		x = body.getNow().getX();
		y = body.getNow().getY();
		switch (direction) {
		case KeyBoard.UP:
			x=m;
			y-=moving;
			height+=moving;
			break;
		case KeyBoard.DOWN:
			x=m;
			y+=moving;
			height+=moving;
			break;
		case KeyBoard.LEFT:
			x-=moving;
			y=n;
			width+=moving;
			break;
		case KeyBoard.RIGHT:
			x+=moving;
			y=n;
			width+=moving;
			break;
		}
		body.setNow(new Position(x, y));
	}
	
	@Override
	protected synchronized boolean isHited() {
		/*
		 * 电磁炮穿透效果，能破坏普通子弹
		 */
		//先判断玩家打电脑
		if (snake instanceof Hero) {
			for (int i=0;i<MyKeyListener.enemies.size();i++) {
				Enemy enemy = MyKeyListener.enemies.get(i);
				//判断子弹
				for (int j=0;j<enemy.bullets.size();j++) {
					Shoot shoot = enemy.bullets.get(i);
					if (!(shoot instanceof RailBullet)) {
						if (checkRange(shoot.body.getNow())) {
							shoot.isDead = true;
							return true;
						}
					}
				}
				//判断蛇头
				if (checkRange(enemy.head.getNow())) {
					enemy.isHited = true;
					enemy.isHited();
					enemy.isHited();
					return true;
				}
				//判断蛇身
				for (int j = 0; j < enemy.bodies.size(); j++) {
					Body body = enemy.bodies.get(j);
					if (checkRange(body.getNow())) {
						enemy.isHited = true;
						enemy.isHited();
						enemy.isHited();
						return true;
					}
				}
				
			}
		}else{
			//判断电脑打玩家
			//判断子弹
			for (int i = 0; i < MyKeyListener.snake.bullets.size(); i++) {
				Shoot shoot = MyKeyListener.snake.bullets.get(i);
				if (!(shoot instanceof RailBullet)) {
					if (checkRange(shoot.body.getNow())) {
						shoot.isDead = true;
						return true;
					}
				}
			}
			//判断头部
			if (checkRange(MyKeyListener.snake.head.getNow())) {
				MyKeyListener.snake.isHited = true;
				MyKeyListener.snake.isHited();
				MyKeyListener.snake.isHited();
				return true;
			}
			for (int i = 0; i < MyKeyListener.snake.bodies.size(); i++) {
				Body body = MyKeyListener.snake.bodies.get(i);
				if (checkRange(body.getNow())) {
					MyKeyListener.snake.isHited = true;
					MyKeyListener.snake.isHited();
					MyKeyListener.snake.isHited();
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkRange(Position position){
		int x = position.getX();
		int y = position.getY();
		int x0 = this.body.getNow().getX();
		int y0 = this.body.getNow().getY();
		if (x+ShootSettings.SIZE>x0&&x0+width>x) {
			if (y+ShootSettings.SIZE>y0&&y0+height>y) {
				return true;
			}
		}
		return false;
	}
}
