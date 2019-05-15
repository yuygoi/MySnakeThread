package com.franklin.utils.settings;

import java.awt.Color;

import com.franklin.domain.Position;
import com.franklin.snake.item.equipment.gun.GunSettings;
import com.franklin.utils.Screen;

/**
*@author : 叶俊晖
*@date : 2019年5月5日上午10:33:57 
*/
public class UISettings {

	
	public final static Position WEAPON_POSITION=new Position(Screen.Width-300, Screen.height-150);
	
	public final static int WEAPON_SIZE = GunSettings.size*2;
	
	public final static Position LEVEL_POSITION = new Position(Screen.Width-200, Screen.height-100);
	
	public final static int LEVEL_FONT = 30;
	
	public final static Color LEVEL_COLOR = Color.BLACK;
}
