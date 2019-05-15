package com.franklin.utils.settings;

import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;

/**
*@author : 叶俊晖
*@date : 2019年4月28日下午9:21:17 
*/
public class ItemSettings {
	public static final int size = SnakeSettings.size;
	public static final int CREATE_TIME = 12000;
	public static final int ITEM_LIFE = 15;
	public static final int ITEM_FLASH_TIME = ITEM_LIFE/5;
	
	private final static String foodPath="/com/franklin/snake/images/food.png";
	public static final int SPEED = 50;
	public static Image FOOD_IMAGE = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource(foodPath));

	public static int WEAPON_CHANCE = 40;
	public static int RARE_WEAPON_CHANCE = 30;
	public static int SHIELD_CHANCE = 90;
}
