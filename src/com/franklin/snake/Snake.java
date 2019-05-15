package com.franklin.snake;

import java.util.Vector;

import com.franklin.snake.Actions.Actions;
import com.franklin.snake.item.eatthings.Potion;
import com.franklin.snake.item.equipment.gun.Guns;
import com.franklin.snake.item.equipment.gun.ShotGun;
import com.franklin.snake.item.equipment.gun.bullet.Shoot;
import com.franklin.snake.item.equipment.shield.Shield;
import com.franklin.snake.parts.*;
import com.franklin.snake.role.CreateRole;
import com.franklin.snake.role.Enemy;
import com.franklin.snake.role.Hero;
import com.franklin.utils.KeyBoard;
import com.franklin.utils.MyKeyListener;
import com.franklin.utils.settings.PotionSettings;
import com.franklin.utils.settings.ShootSettings;
import com.franklin.utils.settings.SnakeSettings;

/**
*@author : 叶俊晖
*@date : 2019年4月23日下午3:14:20 
*/
public abstract class Snake implements Actions{

	public Head head;
	public int weapon = ShootSettings.WEAPON_1;
	
	public Vector<Body> bodies;
	
	public boolean isHited = false;
	
	public Vector<Shoot> bullets = new Vector<>();
	
	private int direction;
	
	public Vector<Guns> guns = new Vector<>();
	public Vector<Shield> shields = new Vector<>();

	/**
	 * 
	 */
	protected Snake(Head head) {
		super();
		this.head = head;
		initBody();
	}

	public int getDirection() {
		return direction;
	}

	/**
	 * 
	 */
	public Snake() {
		super();
		// TODO Auto-generated constructor stub
	}



	/**
	 * 初始化蛇身体
	 */
	private void initBody(){
		direction = (int) (Math.random()*4);
		bodies = new Vector<>();
		head.setDirection(direction);
		switch (direction) {
		case KeyBoard.UP:
			bodies = CreateBody.up(head, bodies);
			break;
		case KeyBoard.DOWN:
			bodies = CreateBody.down(head, bodies);
			break;
		case KeyBoard.LEFT:
			bodies = CreateBody.left(head, bodies);
			break;
		case KeyBoard.RIGHT:
			bodies = CreateBody.right(head, bodies);
			break;
		default:
			System.out.println("Body has been created");
			break;
		}
	}

	/**
	 * 移动蛇身
	 */
	protected synchronized void moveBody() {
		if (bodies.size()>0) {
			bodies.get(0).setBefore(head.getBefore());
			for (int i = 1; i < bodies.size(); i++) {
				Body body = bodies.get(i);
				body.setBefore(bodies.get(i-1).getNow());
			}
			for (Body body : bodies) {
				body.move();
			}
		}
	}

	/**
	 * 移动装备
	 */
	protected synchronized void moveEquipment(){
		if (guns.size()>0) {
			if (bodies.size()>0) {
				guns.get(0).last = bodies.get(bodies.size()-1).getNow();
			}else{
				guns.get(0).last = head.getNow();
			}
			for (int i = 1; i < guns.size(); i++) {
				guns.get(i).last = guns.get(i-1).position;
			}
			for (Guns gun : guns) {
				gun.position = gun.last;
			}
		}
		if (shields.size()>0) {
			if (guns.size()>0) {
				shields.get(0).last = guns.get(guns.size()-1).position;
			}else if (bodies.size()>0) {
				shields.get(0).last = bodies.get(bodies.size()-1).getNow();
			}else{
				shields.get(0).last = head.getNow();
			}
			for (int i = 1; i < shields.size(); i++) {
				shields.get(i).last = shields.get(i-1).position;
			}
			for (Shield gun : shields) {
				gun.position = gun.last;
			}
		}
		
	}

	
	
	/**
	 * 射击
	 */
	public void shootEnemy(){
		if (this instanceof Hero) {
			bodies.remove(bodies.size()-1);
		}
		Shoot shoot = null;
		Thread thread =null;
		switch (weapon) {
		case ShootSettings.WEAPON_1:
			shoot = new Shoot(this);
			thread = new Thread(shoot);
			bullets.add(shoot);
			thread.start();
			break;
		case ShootSettings.WEAPON_2:
		case ShootSettings.WEAPON_3:
			guns.get(weapon).shootting(this);
			break;
		}
	}
	
	public synchronized void isHited(){
		if (isHited) {
			if (shields.size()>0) {
				shields.remove(shields.size()-1);
			}else if (guns.size()>0) {
				guns.remove(guns.size()-1);
				weapon = guns.size()-1;
			}else if (bodies.size()>0) {
				bodies.remove(bodies.size()-1);
				if (this instanceof Enemy) {
					//敌人受伤掉血
					Potion potion = new Potion(head.getNow().getX(),head.getNow().getY());
					MyKeyListener.potions.add(potion);
					Thread thread = new Thread(potion);
					thread.start();
				}
			}else{
				//死亡
				head.isDead = true;
			}
			
		}
	}
	
}
