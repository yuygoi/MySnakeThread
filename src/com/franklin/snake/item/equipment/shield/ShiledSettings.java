package com.franklin.snake.item.equipment.shield;

import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;

/**
*@author : 叶俊晖
*@date : 2019年5月5日上午9:32:56 
*/
public class ShiledSettings {

	private final static String shiledPath="/com/franklin/snake/images/shield.png";
	public static Image IMAGE = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource(shiledPath));
	
}
