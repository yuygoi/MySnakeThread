package com.franklin.snake.item.equipment.gun;

import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;

import com.franklin.utils.settings.SnakeSettings;

/**
*@author : 叶俊晖
*@date : 2019年4月29日下午9:03:39 
*/
public class GunSettings {

	
	private final static String shotGunPath="/com/franklin/snake/images/shotgun.png";
	public static Image SHOTGUN_IMAGE = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource(shotGunPath));
	private final static String railGunPath="/com/franklin/snake/images/railgun.png";
	private final static String railrowPath="/com/franklin/snake/images/railgunrow.png";
	private final static String railcolPath="/com/franklin/snake/images/railguncolum.png";
	public static final int size = SnakeSettings.size+SnakeSettings.size/2;
	public static final int RAILGUN_SIZE = SnakeSettings.size*2;
	public static final int MAX_LEVEL = 2;
	public static Image RAILGUN_IMAGE = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource(railGunPath));
	public static Image RAILROW_IMAGE = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource(railrowPath));
	public static Image RAILCOL_IMAGE = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource(railcolPath));
}
