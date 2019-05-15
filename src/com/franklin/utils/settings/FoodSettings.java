package com.franklin.utils.settings;

import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;

/**
*@author : 叶俊晖
*@date : 2019年4月28日下午7:52:38 
*/
public class FoodSettings {

private final static String foodPath="/src/com/franklin/snake/images/food.png";
	
	public static Image FOOD_IMAGE = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource(foodPath));
}
