package com.franklin.snake.item.equipment.gun;

import com.franklin.domain.Position;
import com.franklin.snake.Snake;
import com.franklin.snake.item.Item;
import com.franklin.snake.item.equipment.gun.bullet.BiasBullet;
import com.franklin.snake.item.equipment.gun.bullet.Shoot;
import com.franklin.snake.parts.Head;
import com.franklin.utils.settings.ShootSettings;

/**
*@author : 叶俊晖
*@date : 2019年4月29日下午7:52:43 
*/
public class ShotGun extends Guns{

	/**
	 * 
	 */
	public ShotGun() {
		super();
		this.image = GunSettings.SHOTGUN_IMAGE;
		
	}

	public void shootting(Snake snake){
		Thread thread = null;
		int way = snake.head.getDirection();
		Position head = snake.head.getNow();
		BiasBullet biasBullet = new BiasBullet(way,head,snake, ShootSettings.BIAS_ANGLE);
		BiasBullet biasBullet2 = new BiasBullet(way,head,snake, -ShootSettings.BIAS_ANGLE);
		snake.bullets.add(biasBullet);
		snake.bullets.add(biasBullet2);
		thread = new Thread(biasBullet);
		thread.start();
		thread = new Thread(biasBullet2);
		thread.start();
		if (level>1) {
			Shoot shoot = new Shoot(way,head,snake);
			snake.bullets.add(shoot);
			thread = new Thread(shoot);
			thread.start();
		}
	}

}
