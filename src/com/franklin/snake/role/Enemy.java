package com.franklin.snake.role;

import java.awt.Image;
import java.security.Key;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import com.franklin.domain.Position;
import com.franklin.domain.TargetMessage;
import com.franklin.snake.Snake;
import com.franklin.snake.item.Item;
import com.franklin.snake.item.eatthings.Potion;
import com.franklin.snake.parts.Body;
import com.franklin.snake.parts.Head;
import com.franklin.utils.KeyBoard;
import com.franklin.utils.MyKeyListener;
import com.franklin.utils.Screen;
import com.franklin.utils.settings.EnemySettings;
import com.franklin.utils.settings.GameRule;
import com.franklin.utils.settings.SnakeSettings;

/**
 * @author : 叶俊晖
 * @date : 2019年4月23日下午6:44:05
 */
public class Enemy extends Snake implements Runnable{

	private static final Image IMAGE = SnakeSettings.HEAD_IMAGE;

	private Hero target;

	private boolean findTarget = false;
	private boolean missedMsg = false;
	private int findCount = 0;
	private int lastDirection = -1;
	private int[] bestDirection;

	public static Image getImage() {
		return IMAGE;
	}
	
	public int getFindCount() {
		return findCount;
	}
	public boolean isFindTarget() {
		return findTarget;
	}
	public boolean isMissedMsg() {
		return missedMsg;
	}
	public void setMissedMsg(boolean missedMsg) {
		this.missedMsg = missedMsg;
	}

	/**
	 * 
	 */
	public Enemy(Head hero, int direction,Hero target) {
		super(CreateRole.createEnemy(hero, direction));
		this.target = target;
	}
	
	private boolean isBack(){
		switch (lastDirection) {
		case KeyBoard.UP:
			if (head.getDirection() == KeyBoard.DOWN) {
				return true;
				
			}
			break;
		case KeyBoard.DOWN:
			if (head.getDirection() == KeyBoard.UP) {
				return true;
			}
			break;
		case KeyBoard.LEFT:
			if (head.getDirection() == KeyBoard.RIGHT) {
				return true;
			}
			break;
		case KeyBoard.RIGHT:
			if (head.getDirection() == KeyBoard.LEFT) {
				return true;
			}
			break;
		}
		return false;
	}
	
	private boolean isMoveToBody(Position position){
		for (Body body : bodies) {
			Position position2 = body.getNow();
			if (p2pDistance(position, position2)==0) {
				return true;
			}
		}
		return false;
	}

	private double p2pDistance(Position p1,Position p2){
		return Math.pow(Math.pow(p1.getX()-p2.getX(), 2)+Math.pow(p1.getY()-p2.getY(), 2), 0.5);
	}
	
	@Override
	public void move() {
		if (!isBack()) {
			head.setBefore(head.getNow());
			int x = head.getNow().getX();
			int y = head.getNow().getY();
			switch (head.getDirection()) {
			case KeyBoard.UP:
				y -= head.getSpeed();
				break;
			case KeyBoard.DOWN:
				y += head.getSpeed();
				break;
			case KeyBoard.LEFT:
				x -= head.getSpeed();
				break;
			case KeyBoard.RIGHT:
				x += head.getSpeed();
				break;
			}
			head.setNow(new Position(x, y));
			super.moveBody();
			super.moveEquipment();
		}
	}

	@Override
	public void run() {
		while (true) {
			threadSleep(1);
			move();
			if (isHited) {
				try {
					Thread.sleep(SnakeSettings.HITED_TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isHited = false;
				findTarget =true;
				findCount++;
			}
			int needTurn = GameRule.isNearByRange(head);
			if (needTurn >= 0) {
				turnBack(needTurn);
			}
			if (!findTarget) {
				if (isTarget(true)) {
					findTarget = true;
					missedMsg = false;
					findCount++;
				}else{
					//没有找到目标去找药
					int findPotion= findPotion();
					if (findPotion>=0) {
						lastDirection = head.getDirection();
						head.setDirection(findPotion);
					}else{
						//没有药找道具
						findPotion = findItem();
						if (findPotion>=0) {
							lastDirection = head.getDirection();
							head.setDirection(findPotion);
						}
					}
				}
			}else{
				//找到了目标
				if (isMissedTarget()) {
					//跟丢了
					findTarget = false;
					missedMsg = true;
					new TargetMessage(this);
				}else{
					//开始自动追踪玩家
					bestDirection = findTheBestDirection();
					lastDirection = head.getDirection();
					head.setDirection(bestDirection[0]);
					for (int i = 0; i < bestDirection[2]/SnakeSettings.size; i++) {
						move();
						threadSleep(1);
					}
					lastDirection = head.getDirection();
					head.setDirection(bestDirection[1]);
					shootEnemy();
					threadSleep(5);
				}
			}
			if (head.isDead) {
				break;
			}
		}
	}

	/**
	 * 敌人蛇遇墙自动掉头
	 * 
	 * @param needTurn
	 */
	private void turnBack(int needTurn) {
		switch (needTurn) {
		case KeyBoard.UP:
			if (head.getNow().getX() > Screen.Width / 2) {
				lastDirection = head.getDirection();
				head.setDirection(KeyBoard.LEFT);
			} else {
				lastDirection = head.getDirection();
				head.setDirection(KeyBoard.RIGHT);
			}
			move();
			lastDirection = head.getDirection();
			head.setDirection(KeyBoard.DOWN);
			break;
		case KeyBoard.DOWN:
			if (head.getNow().getX() > Screen.Width / 2) {
				lastDirection = head.getDirection();
				head.setDirection(KeyBoard.LEFT);
			} else {
				lastDirection = head.getDirection();
				head.setDirection(KeyBoard.RIGHT);
			}
			move();
			lastDirection = head.getDirection();
			head.setDirection(KeyBoard.UP);
			break;
		case KeyBoard.LEFT:
			if (head.getNow().getY() > Screen.height / 2) {
				lastDirection = head.getDirection();
				head.setDirection(KeyBoard.UP);
			} else {
				lastDirection = head.getDirection();
				head.setDirection(KeyBoard.DOWN);
			}
			move();
			lastDirection = head.getDirection();
			head.setDirection(KeyBoard.RIGHT);
			break;
		case KeyBoard.RIGHT:
			if (head.getNow().getY() > Screen.height / 2) {
				lastDirection = head.getDirection();
				head.setDirection(KeyBoard.UP);
			} else {
				lastDirection = head.getDirection();
				head.setDirection(KeyBoard.DOWN);
			}
			move();
			lastDirection = head.getDirection();
			head.setDirection(KeyBoard.LEFT);
			break;
		}
	}


	/**
	 * (圆形视野)判断是否是目标
	 * 
	 * @return
	 */
	private boolean isTarget() {
		if (getTargetDisctance(target.head.getNow())<=EnemySettings.VISION_DISTANCE) {
			return true;
		}
		for (Body body : target.bodies) {
			if (getTargetDisctance(body.getNow())<=EnemySettings.VISION_DISTANCE) {
				return true;
			}
		}
		return false;
	}

	
	/**求玩家距离敌人的距离
	 * @param position
	 * @return
	 */
	private double getTargetDisctance(Position position){
		return Math.pow(Math.pow(position.getX()-head.getNow().getX(), 2)
				+Math.pow(position.getY()-head.getNow().getY(), 2)
				, 0.5);
	}
	
	/**判断玩家是否逃跑了
	 * @return
	 */
	private boolean isMissedTarget(){
		if (getTargetDisctance(target.head.getNow())<=EnemySettings.ESCAPE_DISTANCE) {
			return false;
		}
		for (Body body : target.bodies) {
			if (getTargetDisctance(body.getNow())<=EnemySettings.ESCAPE_DISTANCE) {
				return false;
			}
		}
		return true;
	}

	/**返回最近方向 攻击方向 移动距离
	 * @return
	 */
	private int[] findTheBestDirection(){
		double min = getTargetDisctance(target.head.getNow());
		int goodDirection = 0;
		int targetDirection = 0;
		Position minP = target.head.getNow();
		//先找到最近的玩家部件
		int listsize = target.bodies.size();
		for (int i=0;i<listsize;i++) {
			Body body = target.bodies.get(i);
			double dis = getTargetDisctance(body.getNow());
			if (dis<min) {
				min = dis;
				minP = body.getNow();
			}
		}
		//判断横向与纵向差距
		Position enemy = head.getNow();
		int x = Math.abs(minP.getX()-enemy.getX())<Math.abs(minP.getY()-enemy.getY())? 1:2;
		int resDis = minP.getX()-enemy.getX();
		int next = minP.getY()-enemy.getY();
		if (x == 1) {
			if (resDis<0) {
				goodDirection = KeyBoard.LEFT;
			}else{
				goodDirection = KeyBoard.RIGHT;
			}
			if (next>0) {
				targetDirection = KeyBoard.DOWN;
			}else {
				targetDirection = KeyBoard.UP;
			}
			return new int[]{goodDirection,targetDirection,Math.abs(resDis)};
		}else{
			if (resDis<0) {
				targetDirection = KeyBoard.LEFT;
			}else{
				targetDirection = KeyBoard.RIGHT;
			}
			if (next>0) {
				goodDirection = KeyBoard.DOWN;
			}else {
				goodDirection = KeyBoard.UP;
			}
			return new int[]{goodDirection,targetDirection,Math.abs(next)};
		}
	}

	/**返回视野开始角度
	 * @return
	 */
	public int getVisionAngle(){
		int angle = (180 - EnemySettings.VISION_ANGLE)/2;
		switch (head.getDirection()) {
		case KeyBoard.UP:
			return angle;
		case KeyBoard.DOWN:
			return angle + 180;
		case KeyBoard.LEFT:
			return angle + 90;
		case KeyBoard.RIGHT:
			return angle + 270;
		}
		return angle;
	}

	/**(扇形视野)根据扇形区域判断是否有敌人
	 * @param sector
	 * @return
	 */
	private boolean isTarget(boolean sector){
		switch (head.getDirection()) {
		case KeyBoard.UP:
			return targetUP();
		case KeyBoard.DOWN:
			return targetDOWN();
		case KeyBoard.LEFT:
			return targetLEFT();
		case KeyBoard.RIGHT:
			return targetRIGHT();
		}
		return false;
	}
	
	/**判断各方向视野是否有玩家
	 * @return
	 */
	private boolean targetUP(){
		double angle = getAngle();
		// x = k*y + b
		//右斜线
		double tanA1 = -Math.tan(angle);
		int x0 = head.getNow().getX()+SnakeSettings.size/2;
		int y0 = head.getNow().getY()+SnakeSettings.size/2;
		int b1 = (int) (x0 - y0/tanA1);
		//左斜线
		double tanA2 = -tanA1;
		int b2 = (int) (x0 - y0/tanA2);
		//判断头部
		Position position = target.head.getNow();
		if (getTargetDisctance(position)<=EnemySettings.VISION_DISTANCE+SnakeSettings.size) {
			//右斜线
			if (position.getX()<=position.getY()*tanA1+b1) {
				//左斜线
				if (position.getX()+SnakeSettings.size>=position.getY()*tanA2+b2) {
					return true;
				}
			}
		}
		for (Body body : target.bodies) {
			position = body.getNow();
			if (getTargetDisctance(position)<=EnemySettings.VISION_DISTANCE+SnakeSettings.size) {
				//右斜线
				if (position.getX()<=position.getY()*tanA1+b1) {
					//左斜线
					if (position.getX()+SnakeSettings.size>=position.getY()*tanA2+b2) {
						return true;
					}
				}
			}
		}
		return false;
	}
	private boolean targetDOWN(){
		double angle = getAngle();
		// x = k*y + b
		//右斜线
		double tanA1 = -Math.tan(angle);
		int x0 = head.getNow().getX()+SnakeSettings.size/2;
		int y0 = head.getNow().getY()+SnakeSettings.size/2;
		int b1 = (int) (x0 - y0/tanA1);
		//左斜线
		double tanA2 = -tanA1;
		int b2 = (int) (x0 - y0/tanA2);
		//判断头部
		Position position = target.head.getNow();
		if (getTargetDisctance(position)<=EnemySettings.VISION_DISTANCE+SnakeSettings.size) {
			//右斜线
			if (position.getX()+SnakeSettings.size>=position.getY()*tanA1+b1) {
				//左斜线
				if (position.getX()<=position.getY()*tanA2+b2) {
					return true;
				}
			}
		}
		for (Body body : target.bodies) {
			position = body.getNow();
			if (getTargetDisctance(position)<=EnemySettings.VISION_DISTANCE+SnakeSettings.size) {
				//右斜线
				if (position.getX()+SnakeSettings.size>=position.getY()*tanA1+b1) {
					//左斜线
					if (position.getX()<=position.getY()*tanA2+b2) {
						return true;
					}
				}
			}
		}
		return false;
	}
	private boolean targetLEFT(){
		double angle = getAngle();
		// x = k*y + b
		//右斜线
		double tanA1 = -Math.tan(angle);
		int x0 = head.getNow().getX()+SnakeSettings.size/2;
		int y0 = head.getNow().getY()+SnakeSettings.size/2;
		int b1 = (int) (x0 - y0/tanA1);
		//左斜线
		double tanA2 = -tanA1;
		int b2 = (int) (x0 - y0/tanA2);
		//判断头部
		Position position = target.head.getNow();
		if (getTargetDisctance(position)<=EnemySettings.VISION_DISTANCE+SnakeSettings.size) {
			//右斜线
			if (position.getX()-2*SnakeSettings.size<=position.getY()*tanA1+b1) {
				//左斜线
				if (position.getX()<=position.getY()*tanA2+b2) {
					return true;
				}
			}
		}
		for (Body body : target.bodies) {
			position = body.getNow();
			if (getTargetDisctance(position)<=EnemySettings.VISION_DISTANCE+SnakeSettings.size) {
				//右斜线
				if (position.getX()-2*SnakeSettings.size<=position.getY()*tanA1+b1) {
					//左斜线
					if (position.getX()<=position.getY()*tanA2+b2) {
						return true;
					}
				}
			}
		}
		return false;
	}
	private boolean targetRIGHT(){
		double angle = getAngle();
		// x = k*y + b
		//右斜线
		double tanA1 = -Math.tan(angle);
		int x0 = head.getNow().getX()+SnakeSettings.size/2;
		int y0 = head.getNow().getY()+SnakeSettings.size/2;
		int b1 = (int) (x0 - y0/tanA1);
		//左斜线
		double tanA2 = -tanA1;
		int b2 = (int) (x0 - y0/tanA2);
		//判断头部
		Position position = target.head.getNow();
		if (getTargetDisctance(position)<=EnemySettings.VISION_DISTANCE+SnakeSettings.size) {
			//右斜线
			if (position.getX()+SnakeSettings.size>=position.getY()*tanA1+b1) {
				//左斜线
				if (position.getX()+SnakeSettings.size>=position.getY()*tanA2+b2) {
					return true;
				}
			}
		}
		for (Body body : target.bodies) {
			position = body.getNow();
			if (getTargetDisctance(position)<=EnemySettings.VISION_DISTANCE+SnakeSettings.size) {
				//右斜线
				if (position.getX()+SnakeSettings.size>=position.getY()*tanA1+b1) {
					//左斜线
					if (position.getX()+SnakeSettings.size>=position.getY()*tanA2+b2) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**将角度制转换为弧度制
	 * @return
	 */
	private double getAngle(){
		return Math.PI*(EnemySettings.VISION_ANGLE/360.0);
	}

	/**找药
	 * @return
	 */
	private int findPotion(){
		if (MyKeyListener.potions.size()>0) {
			Position position =new Position(MyKeyListener.potions.get(0).getX(), MyKeyListener.potions.get(0).getY());
			double dis = getTargetDisctance(position);
			double temp_dis = 0;
			Position temp = new Position();
			Potion potion = null;
			for (int i = 1; i < MyKeyListener.potions.size(); i++) {
				potion = MyKeyListener.potions.get(i);
				temp.setX(potion.getX());
				temp.setY(potion.getY());
				temp_dis = getTargetDisctance(temp);
				if (dis>temp_dis) {
					dis = temp_dis;
					position = temp;
				}
			}
			Position headP = head.getNow();
			int subx = headP.getX()-position.getX();
			int suby = headP.getY()-position.getY();
			int way = Math.abs(subx)>Math.abs(suby)? 1:2;
			int goodDirection = 0;
			if (way == 1) {
				if (subx<0) {
					goodDirection = KeyBoard.RIGHT;
				}else {
					goodDirection = KeyBoard.LEFT;
				}
			}else {
				if (suby<0) {
					goodDirection = KeyBoard.DOWN;
				}else {
					goodDirection =KeyBoard.UP;
				}
			}
			return goodDirection;
		}
		return -1;
	}

	/**线程休眠
	 * @param speedLevel休眠等级
	 */
	private void threadSleep(int speedLevel){
		try {
			Thread.sleep(EnemySettings.DEFAULT_SPEED*speedLevel);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private int findItem(){
		if (MyKeyListener.items.size()>0) {
			Position position =new Position(MyKeyListener.items.get(0).position.getX(), MyKeyListener.items.get(0).position.getY());
			double dis = getTargetDisctance(position);
			double temp_dis = 0;
			Position temp = new Position();
			Item item = null;
			for (int i = 1; i < MyKeyListener.items.size(); i++) {
				item = MyKeyListener.items.get(i);
				temp.setX(item.position.getX());
				temp.setY(item.position.getY());
				temp_dis = getTargetDisctance(temp);
				if (dis>temp_dis) {
					dis = temp_dis;
					position = temp;
				}
			}
			Position headP = head.getNow();
			int subx = headP.getX()-position.getX();
			int suby = headP.getY()-position.getY();
			int way = Math.abs(subx)>Math.abs(suby)? 1:2;
			int goodDirection = 0;
			if (way == 1) {
				if (subx<0) {
					goodDirection = KeyBoard.RIGHT;
				}else {
					goodDirection = KeyBoard.LEFT;
				}
			}else {
				if (suby<0) {
					goodDirection = KeyBoard.DOWN;
				}else {
					goodDirection =KeyBoard.UP;
				}
			}
			return goodDirection;
		}
		return -1;
	}
}
