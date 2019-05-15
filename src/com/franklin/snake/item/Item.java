package com.franklin.snake.item;

import java.awt.Image;
import java.time.LocalTime;

import com.franklin.domain.Position;
import com.franklin.snake.Snake;
import com.franklin.snake.Actions.Actions;
import com.franklin.snake.parts.Body;
import com.franklin.utils.KeyBoard;
import com.franklin.utils.MyKeyListener;
import com.franklin.utils.Screen;
import com.franklin.utils.settings.ItemSettings;
import com.franklin.utils.settings.PotionSettings;
import com.franklin.utils.settings.SnakeSettings;

/**
*@author : 叶俊晖
*@date : 2019年4月28日下午8:04:38 
*/
public abstract class Item implements Actions,Runnable {
	
	protected int speed = ItemSettings.SPEED;
	
	public Position position;
	public Position last;
	
	
	public int size = ItemSettings.size;
	
	public Image image;
	
	private Position createPotion;
	
	private int radius;
	
	public boolean isDead =false;
	
	protected int moving = ItemSettings.size;
	
	protected Snake snake;
	
	public boolean isFlash = false;
	public int flashCount = 0;
	protected int direction = -1;
	protected LocalTime deadTime;
	
	/**
	 * 
	 */
	public Item() {
		super();
		initItem();
		this.direction = getItemDirection();
		LocalTime localTime = LocalTime.now();
		deadTime = localTime.withSecond((localTime.getSecond()+ItemSettings.ITEM_LIFE)%60);
	}

	protected void initItem() {
		createCircleCenter();
		createRadius();
		int x = 0;
		int y = 0;
		do {
			x = (int) (Math.random()*Screen.Width);
			y = (int) (Math.random()*Screen.height);
		} while (getDistance(x, y)>radius
				||(x+ItemSettings.size)>=Screen.Width
				||(y+ItemSettings.size)>=Screen.height
				||Math.abs(x+size)==Math.abs(y+size)
				||Math.abs(x-size)==Math.abs(y-size));
		position = new Position(x, y);
	}
	
	private void createCircleCenter(){
		int x = (int) (Math.random()*Screen.Width);
		int y = (int) (Math.random()*Screen.height);
		createPotion = new Position(x, y);
	}
	
	private void createRadius(){
		double random = Math.random()*10;
		radius = (int) (Screen.Width/random);
	}
	
	private double getDistance(int x,int y){
		return Math.pow(Math.pow(x-createPotion.getX(), 2)+Math.pow(y-createPotion.getY(), 2)
				, 0.5);
	}

	/**判断道具是否被拾取(只能用头)
	 * @return
	 */
	protected boolean isTaken(){
		//判断玩家
		Position position = MyKeyListener.snake.head.getNow();
		if (checkPostion(position)) {
			snake = MyKeyListener.snake;
			isDead = true;
			return true;
		}
		//判断敌人
		for (int i=0;i<MyKeyListener.enemies.size();i++) {
			position = MyKeyListener.enemies.get(i).head.getNow();
			if (checkPostion(position)) {
				isDead = true;
				snake = MyKeyListener.enemies.get(i);
				return true;
			}
		}
		return false;
	}
	/**检查头部位置
	 * @param position
	 * @return
	 */
	private boolean checkPostion(Position position){
		if (this.position.getX()+moving>=position.getX()&&position.getX()+SnakeSettings.size>=this.position.getX()) {
			if (this.position.getY()+moving>=position.getY()&&position.getY()+SnakeSettings.size>=this.position.getY()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 道具起作用
	 */
	protected abstract void effect();
	
	protected int getLastDirection(Body last) {
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

	protected void flashVision(){
		if (LocalTime.now().getSecond()+ItemSettings.ITEM_FLASH_TIME>=deadTime.getSecond()) {
			isFlash = true;
		}
	}

	@Override
	public void move() {
		int x = position.getX();
		int y = position.getY();
		switch (direction) {
		case PotionSettings.NORTH_EAST:
			x+=moving/4;
			y-=moving/4;
			break;
		case PotionSettings.NORTH_WEST:
			x-=moving/4;
			y-=moving/4;
			break;
		case PotionSettings.SOUTH_EAST:
			x+=moving/4;
			y+=moving/4;
			break;
		case PotionSettings.SOUTH_WEST:
			x-=moving/4;
			y+=moving/4;
			break;
		}
		position.setX(x);
		position.setY(y);
	}
	
	private int getItemDirection(){
		int x = (int)(Math.random()*4);
		switch (0) {
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
	
	protected void needTurn(){
		int x = position.getX();
		int y = position.getY();
		switch (direction) {
		case PotionSettings.NORTH_EAST:
			if (x+moving>=Screen.Width) {
				direction = PotionSettings.NORTH_WEST;
			}else if(y<=0){
				direction = PotionSettings.SOUTH_EAST;
			}
			break;
		case PotionSettings.NORTH_WEST:
			if (x<=0) {
				direction = PotionSettings.NORTH_EAST;
			}else if(y<=0){
				direction = PotionSettings.SOUTH_WEST;
			}
			break;
		case PotionSettings.SOUTH_EAST:
			if (x+moving>=Screen.Width) {
				direction = PotionSettings.SOUTH_WEST;
			}else if(y+moving>=Screen.height){
				direction = PotionSettings.NORTH_EAST;
			}
			break;
		case PotionSettings.SOUTH_WEST:
			if (x<=0) {
				direction = PotionSettings.SOUTH_EAST;
			}else if(y+moving>=Screen.height){
				direction = PotionSettings.NORTH_WEST;
			}
			break;
		}
	}
	protected Body cure(Snake snake){
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
	protected boolean isDeadTime(){
		if (LocalTime.now().getSecond()>=deadTime.getSecond()) {
			isDead = true;
		}
		return false;
	}
}
