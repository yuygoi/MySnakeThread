package com.franklin.snake.item.equipment;

import com.franklin.snake.item.Item;
import com.franklin.snake.item.equipment.gun.GunSettings;
import com.franklin.snake.item.equipment.gun.Guns;
import com.franklin.snake.item.equipment.gun.ShotGun;
import com.franklin.snake.item.equipment.shield.Shield;
import com.franklin.utils.settings.ItemSettings;

/**
*@author : 叶俊晖
*@date : 2019年4月29日下午8:35:47 
*/
public abstract class Equipment extends Item {

	public int level = 1;
	
	
	
	/**
	 * 
	 */
	public Equipment() {
		super();
		this.moving = GunSettings.size;
	}

	@Override
	public void run() {
		while (!isDead) {
			move();
			needTurn();
			flashVision();
			isDeadTime();
			if (isTaken()) {
				isDead = true;
				effect();
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
	
	@Override
	protected void effect() {
		if (this instanceof Guns) {
			int index = checkGuns((Guns) this);
			if (index<0) {
				this.position = cure(snake).getNow();
				snake.guns.add((Guns) this);
				snake.weapon = snake.guns.size()-1;
			}else{
				if (snake.guns.get(index).level<GunSettings.MAX_LEVEL) {
					snake.guns.get(index).level++;
				}
			}
		}
		if (this instanceof Shield) {
			this.position = cure(snake).getNow();
			snake.shields.add((Shield) this);
		}
	}
	
	private int checkGuns(Guns gun){
		for (int i=0;i<snake.guns.size();i++) {
			Guns guns = snake.guns.get(i);
			if (gun.getClass().getName().equals(guns.getClass().getName())) {
				return i;
			}
		}
		return -1;
	}
}
