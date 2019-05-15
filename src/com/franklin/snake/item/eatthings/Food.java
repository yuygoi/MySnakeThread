package com.franklin.snake.item.eatthings;


import java.time.LocalTime;

import com.franklin.domain.Position;
import com.franklin.snake.Snake;
import com.franklin.snake.item.Item;
import com.franklin.snake.parts.Body;
import com.franklin.utils.KeyBoard;
import com.franklin.utils.MyKeyListener;
import com.franklin.utils.settings.ItemSettings;
import com.franklin.utils.settings.SnakeSettings;

/**
*@author : 叶俊晖
*@date : 2019年4月28日下午8:09:50 
*/
public class Food extends Item{
	
	
	public Food() {
		super();
		this.image = ItemSettings.FOOD_IMAGE;
	}


	@Override
	public void run() {
		while (!isDead) {
			flashVision();
			if (isTaken()) {
				effect();
				MyKeyListener.hasFood = false;
			}
			if (LocalTime.now().compareTo(deadTime)>0) {
				MyKeyListener.hasFood = false;
				isDead = true;
			}
		}
	}


	@Override
	protected void effect() {
		//治疗一滴血
		snake.bodies.add(cure(snake));
	}
	
}
