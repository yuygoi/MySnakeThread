package com.franklin.snake.parts;

import com.franklin.domain.Position;
import com.franklin.snake.Actions.Actions;
import com.franklin.snake.properties.BasicProps;
import com.franklin.utils.KeyBoard;

/**
*@author : 叶俊晖
*@date : 2019年4月23日下午2:47:13 
*/
public class Body extends BasicProps implements Actions{

	
	/**
	 * 
	 */
	public Body(int x,int y) {
		this.now.setX(x);
		this.now.setY(y);
	}

	@Override
	public void move() {
		this.now=this.before;
	}

}
