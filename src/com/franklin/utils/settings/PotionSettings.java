package com.franklin.utils.settings;

import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;

/**
*@author : 叶俊晖
*@date : 2019年4月27日下午8:03:11 
*/
public class PotionSettings {

	public final static int NORTH_EAST = 1;
	public final static int SOUTH_EAST = 2;
	public final static int NORTH_WEST = -2;
	public final static int SOUTH_WEST = -1;
	
	private final static String potionPath="/com/franklin/snake/images/potion.png";
	public static Image POTION_IMAGE = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource(potionPath));
	
	public final static int size = SnakeSettings.size*2;
	public final static int SPEED = 50;
	public final static int TURN_TIMES = 4;
}
