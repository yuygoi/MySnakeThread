package com.franklin.utils.settings;

import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;

/**
*@author : 叶俊晖
*@date : 2019年4月23日下午4:05:22 
*/
public class SnakeSettings {
	/**
	 * 默认长度
	 */
	public static int DEFAULT_Length = 5;
	
	/**
	 * 尺寸
	 */
	public static final int size = 20;
	
	public static int DEFAULT_Direction = -1;
	
	public static int speed_level = 1;
	
	public static final int HITED_TIME = 1000;
	
	public static int default_speed = 100;
	public static int speed_up = 30;
	public static int mini_speed = 30;
	
	private final static String bodyPath="/com/franklin/snake/images/body.png";	
	private final static String headPath="/com/franklin/snake/images/head.png";
	private final static String heroPath="/com/franklin/snake/images/hero.png";
	
	
	public static Image BODY_IMAGE = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource(bodyPath));
	
	public static Image HEAD_IMAGE = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource(headPath));
	
	public static Image HERO_IMAGE = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource(heroPath));

}
