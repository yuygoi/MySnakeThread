package com.franklin.snake.item;

import com.franklin.snake.item.eatthings.Food;
import com.franklin.snake.item.equipment.Equipment;
import com.franklin.snake.item.equipment.gun.RailGun;
import com.franklin.snake.item.equipment.gun.ShotGun;
import com.franklin.snake.item.equipment.shield.Shield;
import com.franklin.utils.MyKeyListener;
import com.franklin.utils.settings.ItemSettings;

/**
*@author : 叶俊晖
*@date : 2019年4月29日上午10:24:15 
*/
public class CreateItem implements Runnable {

	@Override
	public void run() {
		while (true) {
			if (!MyKeyListener.hasFood) {
				Food food = new Food();
				Thread thread = new Thread(food);
				MyKeyListener.items.add(food);
				thread.start();
				MyKeyListener.hasFood = true;	
			}else if(!MyKeyListener.hasEquipment){
				int equipChance = (int)(Math.random()*100);
				if (equipChance<ItemSettings.WEAPON_CHANCE) {
					Equipment equipment = null;
					int probability = (int)(Math.random()*100);
					if (probability>ItemSettings.RARE_WEAPON_CHANCE) {
						equipment = new ShotGun();
					}else{
						equipment = new RailGun();
					}
					Thread thread = new Thread(equipment);
					MyKeyListener.items.add(equipment);
					thread.start();
					MyKeyListener.hasEquipment = true;
				}else if (equipChance<ItemSettings.SHIELD_CHANCE) {
					Equipment equipment = new Shield();
					Thread thread = new Thread(equipment);
					MyKeyListener.items.add(equipment);
					thread.start();
					MyKeyListener.hasEquipment = true;
				}
			}else{
				try {
					Thread.sleep(ItemSettings.CREATE_TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
