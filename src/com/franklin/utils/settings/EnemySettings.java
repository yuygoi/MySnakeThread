package com.franklin.utils.settings;

import java.awt.Color;

/**
*@author : 叶俊晖
*@date : 2019年4月23日下午8:25:22 
*/
public class EnemySettings {

	public static int DEFAULT_SPEED = 100;
	
	public static int DEFAULT_ENEMY_COUNT = 1;
	
	public static final int VISION_ANGLE = 90;
	public static final int WARNING_MESSAGE_TIME = 1200;
	
	public static final int VISION_DISTANCE = 8*SnakeSettings.size;
	public static final int ESCAPE_DISTANCE =VISION_DISTANCE*3+VISION_DISTANCE/4;
	
	public static final Color VISION_COLOR = Color.LIGHT_GRAY;
	public static final Color WARNING_COLOR = Color.RED;
}
