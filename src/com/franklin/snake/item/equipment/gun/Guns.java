package com.franklin.snake.item.equipment.gun;

import com.franklin.snake.Snake;
import com.franklin.snake.item.Item;
import com.franklin.snake.item.equipment.Equipment;

/**
*@author : 叶俊晖
*@date : 2019年4月29日下午8:23:05 
*/
public abstract class Guns extends Equipment {

	/**
	 * 
	 */
	public Guns() {
		super();
		this.size = GunSettings.size;
	}
	
	public abstract void shootting(Snake snake);
}
