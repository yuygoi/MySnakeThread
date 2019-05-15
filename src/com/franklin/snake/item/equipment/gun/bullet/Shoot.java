  package com.franklin.snake.item.equipment.gun.bullet;

import java.awt.Image;
import java.util.Vector;

import com.franklin.domain.Position;
import com.franklin.snake.Snake;
import com.franklin.snake.Actions.Actions;
import com.franklin.snake.parts.Body;
import com.franklin.snake.role.CreateRole;
import com.franklin.snake.role.Enemy;
import com.franklin.snake.role.Hero;
import com.franklin.utils.KeyBoard;
import com.franklin.utils.MyKeyListener;
import com.franklin.utils.Screen;
import com.franklin.utils.settings.ShootSettings;
import com.franklin.utils.settings.SnakeSettings;

/**
*@author : 叶俊晖
*@date : 2019年4月24日上午9:36:51 
*/
public class Shoot implements Runnable,Actions{
	
	protected int speed = ShootSettings.DEFAULT_BULLET_SPEED;
	
	public int direction;
	
	protected Position head;
	protected Snake snake;
	
	protected int x = 0;
	protected int y = 0;
	
	protected Body body;

	protected boolean isDead = false;
	
	public boolean isDead() {
		return isDead;
	}
	protected static int moving = SnakeSettings.size;
	/**
	 * @param shots
	 * @param direction
	 * @param head
	 */
	
	
	
	public Shoot(Snake snake) {
		super();
		this.direction = snake.head.getDirection();
		this.head = snake.head.getNow();
		x = head.getX();
		y = head.getY();
		this.body = new Body(head.getX(), head.getY());
		this.snake = snake;
		if (snake instanceof Enemy) {
			speed = speed+speed/5;
		}else{
			speed = speed-10;
		}
	}
	public Shoot(int direction,Position head,Snake snake) {
		super();
		this.direction = snake.head.getDirection();
		this.head = snake.head.getNow();
		x = head.getX();
		y = head.getY();
		this.body = new Body(head.getX(), head.getY());
		this.snake = snake;
		if (snake instanceof Enemy) {
			speed = speed+speed/5;
		}else{
			speed = speed-10;
		}
	}

	/**
	 * 
	 */
	public Shoot() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Body getBody() {
		return body;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			move();
			if (isHited()) {
				isDead = true;
				break;
			}
			Position now = body.getNow();
			if (now.getX()<0||(now.getX()+ShootSettings.SIZE*2)>Screen.Width||now.getY()<0||(now.getY()+ShootSettings.SIZE*4-10)>Screen.height) {
				isDead = true;
				break;
			}
		}
	}

	@Override
	public void move() {
		switch (direction) {
		case KeyBoard.UP:
			y -= moving;
			break;
		case KeyBoard.DOWN:
			y += moving;
			break;
		case KeyBoard.LEFT:
			x -= moving;
			break;
		case KeyBoard.RIGHT:
			x += moving;
			break;
		}
		body.setNow(new Position(x, y));
	}

	protected synchronized boolean isHited(){
		int size =SnakeSettings.size;
		if (snake instanceof Enemy) {
			//打玩家
			Hero hero = MyKeyListener.snake;
			Position position = hero.head.getNow();
			if (x+size>position.getX()&&position.getX()>x-size) {
				if(y+size>position.getY()&&position.getY()>y-size){
					hero.isHited =true;
					isDead = true;
					hero.isHited();
					return true;
				}
			}
			int len =hero.bodies.size();
			for (int i=0;i<len;i++) {
				Body body = null;
				if (i<hero.bodies.size()) {
					body=hero.bodies.get(i);
					position = body.getNow();
					if (x+size>position.getX()&&position.getX()>x-size) {
						if(y+size>position.getY()&&position.getY()>y-size){
							hero.isHited =true;
							isDead = true;
							hero.isHited();
							return true;
						}
					}
				}
			}
		}else{
			//玩家打敌人
			for (Enemy enemy : MyKeyListener.enemies) {
				Position position = enemy.head.getNow();
				if (x+size>position.getX()&&position.getX()>x-size) {
					if(y+size>position.getY()&&position.getY()>y-size){
						enemy.isHited=true;
						isDead = true;
						enemy.isHited();						
						return true;
					}
				}
				for (Body body : enemy.bodies) {
					position = body.getNow();
					if (x+size>position.getX()&&position.getX()>x-size) {
						if(y+size>position.getY()&&position.getY()>y-size){
							enemy.isHited=true;
							isDead = true;
							enemy.isHited();
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
