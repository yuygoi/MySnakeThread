package com.franklin.snake.item.eatthings;

import java.time.LocalTime;

import com.franklin.domain.Position;
import com.franklin.snake.Snake;
import com.franklin.snake.Actions.Actions;
import com.franklin.snake.parts.Body;
import com.franklin.snake.parts.Head;
import com.franklin.snake.properties.BasicProps;
import com.franklin.snake.role.Enemy;
import com.franklin.utils.KeyBoard;
import com.franklin.utils.MyKeyListener;
import com.franklin.utils.Screen;
import com.franklin.utils.settings.ItemSettings;
import com.franklin.utils.settings.PotionSettings;
import com.franklin.utils.settings.SnakeSettings;

/**
*@author : 叶俊晖
*@date : 2019年4月27日下午7:54:43 
*/
public class Potion implements Actions,Runnable{

	private int direction = 0;
	public void setDirection(int direction) {
		this.direction = direction;
	}
	private int speed = PotionSettings.SPEED;
	private int moving = PotionSettings.size;
	private int x=0;
	private int y=0;
	private int turnCount = 0;
	public int moveCount = 0;
	private boolean isDead = false;
	public boolean isFlash = false;
	public int flashCount = 0;
	public boolean isDead() {
		return isDead;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	
	
	/**
	 * @param x
	 * @param y
	 */
	public Potion(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.direction = getPotionDirection();
	}

	protected void flashVision(){
		if (turnCount==PotionSettings.TURN_TIMES) {
			isFlash = true;
		}
	}
	
	private int getPotionDirection(){
		int x = (int)(Math.random()*4);
		switch (x) {
		case 0:
			return PotionSettings.NORTH_EAST;
		case 1:
			return PotionSettings.NORTH_WEST;
		case 2:
			return PotionSettings.SOUTH_EAST;
		case 3:
			return PotionSettings.SOUTH_WEST;
		}
		return PotionSettings.NORTH_EAST;
	}
	
	@Override
	public void move() {
		moveCount++;
		switch (direction) {
		case PotionSettings.NORTH_EAST:
			x+=moving/8;
			y-=moving/8;
			break;
		case PotionSettings.NORTH_WEST:
			x-=moving/8;
			y-=moving/8;
			break;
		case PotionSettings.SOUTH_EAST:
			x+=moving/8;
			y+=moving/8;
			break;
		case PotionSettings.SOUTH_WEST:
			x-=moving/8;
			y+=moving/8;
			break;
		}
		
	}

	@Override
	public void run() {
		while (true) {
			move();
			needTurn();
			flashVision();
			if (isEated()) {
				break;
			}
			if (turnCount>PotionSettings.TURN_TIMES) {
				isDead = true;
				break;
			}
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**撞墙反弹，反射角  = 入射角（45）
	 * @return
	 */
	private boolean needTurn(){
		switch (direction) {
		case PotionSettings.NORTH_EAST:
			if (x+moving>=Screen.Width) {
				direction = PotionSettings.NORTH_WEST;
				turnCount++;
				return true;
			}else if(y<=0){
				direction = PotionSettings.SOUTH_EAST;
				turnCount++;
				return true;
			}
		case PotionSettings.NORTH_WEST:
			if (x-moving<=0) {
				direction = PotionSettings.NORTH_EAST;
				turnCount++;
				return true;
			}else if(y<=0){
				direction = PotionSettings.SOUTH_WEST;
				turnCount++;
				return true;
			}
		case PotionSettings.SOUTH_EAST:
			if (x+moving>=Screen.Width) {
				direction = PotionSettings.SOUTH_WEST;
				turnCount++;
				return true;
			}else if(y+2.5*moving>=Screen.height){
				direction = PotionSettings.NORTH_EAST;
				turnCount++;
				return true;
			}
		case PotionSettings.SOUTH_WEST:
			if (x+moving<=0) {
				direction = PotionSettings.SOUTH_EAST;
				turnCount++;
				return true;
			}else if(y+2.5*moving>=Screen.height){
				direction = PotionSettings.NORTH_WEST;
				turnCount++;
				return true;
			}
		}
		return false;
	}

	/**是否被吃(只能用头吃)
	 * @return
	 */
	private boolean isEated(){
		//初次生成不能被吃，移动10次才能被吃,不然打不死了
		if(moveCount>=10){
			//被玩家吃掉
			Position position = MyKeyListener.snake.head.getNow();
			if (checkPostion(position)) {
				MyKeyListener.snake.bodies.add(cure(MyKeyListener.snake));
				MyKeyListener.snake.bodies.add(cure(MyKeyListener.snake));
				isDead = true;
				return true;
			}
			for (Enemy enemy: MyKeyListener.enemies) {
				position = enemy.head.getNow();
				if (checkPostion(position)) {
					enemy.bodies.add(cure(enemy));
					enemy.bodies.add(cure(enemy));
					isDead = true;
					return true;
				}
			}
		}
		return false;
	}
	
	/**检查头部位置
	 * @param position
	 * @return
	 */
	private boolean checkPostion(Position position){
		if (x+moving>=position.getX()&&position.getX()+SnakeSettings.size>=x) {
			if (y+moving>=position.getY()&&position.getY()+SnakeSettings.size>=y) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 加血
	 */
	private Body cure(Snake snake){
		Body last = null;
		if (snake.bodies.size()>0) {
			last = snake.bodies.get(snake.bodies.size() - 1);
		}else{
			last = new Body(snake.head.getNow().getX(), snake.head.getNow().getY());
		}
		Body newBody = null;
		switch (getLastDirection(last)) {
		case KeyBoard.UP:
			newBody = new Body(last.getNow().getX(), last.getNow().getY() + SnakeSettings.size);
			break;
		case KeyBoard.DOWN:
			newBody = new Body(last.getNow().getX(), last.getNow().getY() - SnakeSettings.size);
			break;
		case KeyBoard.LEFT:
			newBody = new Body(last.getNow().getX() + SnakeSettings.size, last.getNow().getY());
			break;
		case KeyBoard.RIGHT:
			newBody = new Body(last.getNow().getX() - SnakeSettings.size, last.getNow().getY());
			break;
		}
		return newBody;
	}
	private int getLastDirection(Body last) {
		if (last.getBefore().getX() == last.getNow().getX()) {
			if (last.getBefore().getY() < last.getNow().getY()) {
				return KeyBoard.UP;
			} else {
				return KeyBoard.DOWN;
			}
		} else {
			if (last.getBefore().getX() < last.getNow().getX()) {
				return KeyBoard.LEFT;
			} else {
				return KeyBoard.RIGHT;
			}
		}
	}
}
