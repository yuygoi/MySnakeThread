package com.franklin.domain;

import com.franklin.snake.role.Enemy;
import com.franklin.utils.settings.EnemySettings;

/**
*@author : 叶俊晖
*@date : 2019年4月27日下午4:23:15 
*/
public class TargetMessage implements Runnable {

	private String message = "Dammit! He's gone!";
	
	public String getMessage() {
		return message;
	}
	
	public TargetMessage(Enemy enemy) {
		if (enemy.isMissedMsg()) {
			try {
				Thread.sleep(EnemySettings.WARNING_MESSAGE_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			enemy.setMissedMsg(false);
		}
	}
	
	@Override
	public void run() {
	}

}
