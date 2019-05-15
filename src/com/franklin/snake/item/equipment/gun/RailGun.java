package com.franklin.snake.item.equipment.gun;

import com.franklin.snake.Snake;
import com.franklin.snake.item.equipment.gun.bullet.RailBullet;
import com.franklin.utils.KeyBoard;

/**
*@author : 叶俊晖
*@date : 2019年5月1日上午9:46:28 
*/
public class RailGun extends Guns {

	
	
	/**
	 * 
	 */
	public RailGun() {
		super();
		this.image = GunSettings.RAILGUN_IMAGE;
	}

	@Override
	public void shootting(Snake snake) {
		RailBullet bullet = new RailBullet(snake);
		if (level>1) {
			switch (bullet.direction) {
			case KeyBoard.UP:
			case KeyBoard.DOWN:
				bullet.width=GunSettings.RAILGUN_SIZE+GunSettings.RAILGUN_SIZE/4;
				break;
			case KeyBoard.LEFT:
			case KeyBoard.RIGHT:
				bullet.height=GunSettings.RAILGUN_SIZE+GunSettings.RAILGUN_SIZE/4;
				break;
			}
		}
		snake.bullets.add(bullet);
		Thread thread = new Thread(bullet);
		thread.start();
	}

	
}
