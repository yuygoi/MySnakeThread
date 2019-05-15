package com.franklin.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPanel;

import com.franklin.domain.Position;
import com.franklin.snake.Snake;
import com.franklin.snake.item.CreateItem;
import com.franklin.snake.item.Item;
import com.franklin.snake.item.eatthings.Food;
import com.franklin.snake.item.eatthings.Potion;
import com.franklin.snake.item.equipment.Equipment;
import com.franklin.snake.item.equipment.gun.GunSettings;
import com.franklin.snake.item.equipment.gun.Guns;
import com.franklin.snake.item.equipment.gun.bullet.RailBullet;
import com.franklin.snake.item.equipment.gun.bullet.Shoot;
import com.franklin.snake.item.equipment.shield.Shield;
import com.franklin.snake.parts.Body;
import com.franklin.snake.parts.Head;
import com.franklin.snake.role.Enemy;
import com.franklin.snake.role.Hero;
import com.franklin.utils.settings.EnemySettings;
import com.franklin.utils.settings.GameRule;
import com.franklin.utils.settings.GameSettings;
import com.franklin.utils.settings.ItemSettings;
import com.franklin.utils.settings.PotionSettings;
import com.franklin.utils.settings.ShootSettings;
import com.franklin.utils.settings.SnakeSettings;
import com.franklin.utils.settings.UISettings;

/**
*@author : 叶俊晖
*@date : 2019年4月23日下午6:36:29 
*/
public class MyKeyListener extends JPanel implements KeyListener,Runnable{
	
	private int direction = SnakeSettings.DEFAULT_Direction;

	private int input = SnakeSettings.DEFAULT_Direction;

	private boolean isGameOver = false;
	private boolean isGameWin = false;
	
	public static Hero snake;
	
	private CreateItem createItem;
	
	public static boolean hasFood = false;
	public static boolean hasEquipment = false;
	
	public static Vector<Item> items = new Vector<>();
	
	public static Vector<Enemy> enemies = new Vector<>();
	
	public static Vector<Potion> potions =new Vector<>();
	
	public Hero getSnake() {
		return snake;
	}

	/**
	 * @param snake
	 */
	public MyKeyListener() {
		super();
		this.snake = new Hero();
		this.createItem = new CreateItem();
		Thread thread1 = new Thread(createItem);
		thread1.start();
		for (int i = 0; i < EnemySettings.DEFAULT_ENEMY_COUNT; i++) {
			Enemy enemy = new Enemy(snake.head, snake.getDirection(),snake);
			Thread thread = new Thread(enemy);
			enemies.add(enemy);
			thread.start();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// 控制
		input = e.getKeyCode();
		int way = snake.head.getDirection();
		if (input != way) {
			switch (input) {
			case KeyEvent.VK_UP:
				if (way == KeyBoard.DOWN) {
					return;
				}
				snake.head.setDirection(KeyBoard.UP);
				break;
			case KeyEvent.VK_DOWN:
				if (way == KeyBoard.UP) {
					return;
				}
				snake.head.setDirection(KeyBoard.DOWN);
				break;
			case KeyEvent.VK_LEFT:
				if (way == KeyBoard.RIGHT) {
					return;
				}
				snake.head.setDirection(KeyBoard.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				if (way == KeyBoard.LEFT) {
					return;
				}
				snake.head.setDirection(KeyBoard.RIGHT);
				break;
			}
					
		}
		if (input == KeyEvent.VK_A&&snake.bodies.size()>2) {
			snake.shootEnemy();
		}
		if (snake.guns.size()>0) {
			if (input == KeyEvent.VK_1) {
				snake.weapon = ShootSettings.WEAPON_1;
			}
			if (input == KeyEvent.VK_2) {
				snake.weapon = ShootSettings.WEAPON_2;
			}
			if (snake.guns.size()>1) {
				if (input == KeyEvent.VK_3) {
					snake.weapon = ShootSettings.WEAPON_3;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintEnemy(g);
		if (!snake.head.isDead) {
			g.drawImage(snake.getImage(), snake.head.getNow().getX(), snake.head.getNow().getY(), SnakeSettings.size,SnakeSettings.size,this);
			for (Body body : snake.bodies) {
				g.drawImage(SnakeSettings.BODY_IMAGE, body.getNow().getX(), body.getNow().getY(), SnakeSettings.size,SnakeSettings.size,this);
			}
		}else{
			isGameOver = true;
		}
		paintBullets(g);
		paintPotions(g);
		paintItems(g);
		paintWeapon(g);
		paintShield(g);
		paintUI(g);
		if (isGameOver) {
			g.setColor(GameSettings.GAME_OVER_COLOR);
			g.setFont(new Font("Times New Roman",Font.BOLD,100));
			g.drawString("Game Over!!!", Screen.Width/3, Screen.height/2);
		}
		if (isGameWin) {
			g.setColor(GameSettings.GAME_WIN_COLOR);
			g.setFont(new Font("Times New Roman",Font.BOLD,100));
			g.drawString("You Win!!!", Screen.Width/3, Screen.height/2);
		}
	}

	private void paintUI(Graphics g){
		g.setColor(UISettings.LEVEL_COLOR);
		g.setFont(new Font("Times New Roman",Font.BOLD,UISettings.LEVEL_FONT));
		if (snake.weapon == ShootSettings.WEAPON_1) {
			g.drawString("LEVEL : 1", UISettings.LEVEL_POSITION.getX(), UISettings.LEVEL_POSITION.getY());
			g.drawImage(SnakeSettings.BODY_IMAGE,UISettings.WEAPON_POSITION.getX(), UISettings.WEAPON_POSITION.getY(), UISettings.WEAPON_SIZE,UISettings.WEAPON_SIZE,this);
		}else{
			Guns gun = snake.guns.get(snake.weapon);
			g.drawString("LEVEL : "+gun.level, UISettings.LEVEL_POSITION.getX(), UISettings.LEVEL_POSITION.getY());
			g.drawImage(gun.image,UISettings.WEAPON_POSITION.getX(), UISettings.WEAPON_POSITION.getY(), UISettings.WEAPON_SIZE,UISettings.WEAPON_SIZE,this);
		}
		
	}
	
	private void paintItems(Graphics g){
		Item item = null;
		for (int i = 0; i < items.size(); i++) {
			item = items.get(i);
			if (!item.isDead) {
				if (!item.isFlash) {
					g.drawImage(item.image, item.position.getX(), item.position.getY(), item.size,item.size,this);
				}else{
					if ((item.flashCount++&1)==1) {
						g.drawImage(item.image, item.position.getX(), item.position.getY(), item.size,item.size,this);
					}
				}
			}else{
				if (item instanceof Food) {
					hasFood = false;
				}
				if (item instanceof Equipment) {
					hasEquipment = false;
				}
				items.remove(i);
				i--;
			}
		}
	}
	
	/**画药
	 * @param g
	 */
	private void paintPotions(Graphics g){
		Potion potion = null;
		for (int i=0;i<potions.size();i++) {
			potion = potions.get(i);
			if (!potion.isDead()) {
				if (!potion.isFlash) {
					g.drawImage(PotionSettings.POTION_IMAGE, potion.getX(), potion.getY(), PotionSettings.size,PotionSettings.size,this);
				}else{
					if ((potion.flashCount++&1)==1) {
						g.drawImage(PotionSettings.POTION_IMAGE, potion.getX(), potion.getY(), PotionSettings.size,PotionSettings.size,this);
					}
				}
			}else{
				potions.remove(i);
				i--;
			}
		}
	}
	
	/**画子弹
	 * @param g
	 */
	private void paintBullets(Graphics g){
		Shoot shoot = null;
		for (int i=0;i<snake.bullets.size();i++) {
			shoot = snake.bullets.get(i);
			if (!shoot.isDead()) {
				Body body = shoot.getBody();
				if (!(shoot instanceof RailBullet)) {
					g.drawImage(SnakeSettings.BODY_IMAGE, body.getNow().getX(), body.getNow().getY(), ShootSettings.SIZE,ShootSettings.SIZE,this);
				}else{
					RailBullet railBullet = (RailBullet) shoot;
					g.drawImage(railBullet.image, body.getNow().getX(), body.getNow().getY(), railBullet.width,railBullet.height,this);
				}
			}else {
				snake.bullets.remove(i);
				i--;
			}
		}
		for (Enemy enemy : enemies) {
			for (int i=0;i<enemy.bullets.size();i++) {
				shoot = enemy.bullets.get(i);
				if (!shoot.isDead()) {
					Body body =shoot.getBody();
					if (!(shoot instanceof RailBullet)) {
						g.drawImage(SnakeSettings.BODY_IMAGE, body.getNow().getX(), body.getNow().getY(), ShootSettings.SIZE,ShootSettings.SIZE,this);
					}else{
						RailBullet railBullet = (RailBullet) shoot;
						g.drawImage(railBullet.image, body.getNow().getX(), body.getNow().getY(), railBullet.width,railBullet.height,this);
					}
				}else{
					enemy.bullets.remove(i);
					i--;
				}
			}
		}
	}
	
	/**画敌人
	 * @param g
	 */
	private void paintEnemy(Graphics g){
		int size = 0;
		int r = 0;
		for (int i=0;i<enemies.size();i++) {
			Enemy enemy =enemies.get(i);
			if (!enemy.head.isDead) {
				Position now = enemy.head.getNow();
				size = SnakeSettings.size;
				r= EnemySettings.VISION_DISTANCE;
				g.setColor(EnemySettings.VISION_COLOR);
				g.fillArc(now.getX()+size/2-r,now.getY()+size/2-r,
						2*r, 2*r,enemy.getVisionAngle(),EnemySettings.VISION_ANGLE);	
			}else{
				enemies.remove(i);
				i--;
			}
		}
		if (enemies.isEmpty()) {
			isGameWin = true;
		}
		for (Enemy enemy : enemies) {
			Position now = enemy.head.getNow();
			if (enemy.isFindTarget()&&(!enemy.isMissedMsg())) {
				g.setColor(EnemySettings.WARNING_COLOR);
				g.setFont(new Font("Times New Roman",Font.BOLD,50));
				String msg=null;
				if (enemy.getFindCount()>1) {
					msg = "Is the stupid again!!!";
				}else{
					msg = "!!!";
				}
			    g.drawString(msg, now.getX()-size, now.getY()-size);
			    int rescape = EnemySettings.ESCAPE_DISTANCE;
			    g.drawOval(now.getX()+size/2-rescape, now.getY()+size/2-rescape,2*rescape+size*2 , 2*rescape+2*size);
			}
			if (enemy.isMissedMsg()&&!enemy.isFindTarget()) {
				g.setColor(EnemySettings.WARNING_COLOR);
				g.setFont(new Font("Times New Roman",Font.BOLD,50));
				String msg=null;
				if (enemy.getFindCount()>1) {
					msg = "FxxK I missed "+enemy.getFindCount()+" times!";
				}else{
					msg = "Dammit! He's gone!";
				}
			    g.drawString(msg, now.getX()-5*size, now.getY()-size);
			}
			size = SnakeSettings.size;		
			g.drawImage(enemy.getImage(), now.getX(), now.getY(), size,size,this);
			for (Body body : enemy.bodies) {
				g.drawImage(SnakeSettings.BODY_IMAGE, body.getNow().getX(), body.getNow().getY(), size,size,this);
			}
		}
	}
	
	private void paintWeapon(Graphics g){
		for (int i = 0; i < snake.guns.size(); i++) {
			Guns guns = snake.guns.get(i);
			g.drawImage(guns.image, guns.position.getX(), guns.position.getY(), SnakeSettings.size,SnakeSettings.size,this);
		}
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			for (int j = 0; j < enemy.guns.size(); j++) {
				Guns guns = enemy.guns.get(j);
				g.drawImage(guns.image, guns.position.getX(), guns.position.getY(), SnakeSettings.size,SnakeSettings.size,this);
			}
		}
	}
	private void paintShield(Graphics g){
		for (int i = 0; i < snake.shields.size(); i++) {
			Shield guns = snake.shields.get(i);
			g.drawImage(guns.image, guns.position.getX(), guns.position.getY(), SnakeSettings.size,SnakeSettings.size,this);
		}
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			for (int j = 0; j < enemy.shields.size(); j++) {
				Shield guns = enemy.shields.get(j);
				g.drawImage(guns.image, guns.position.getX(), guns.position.getY(), SnakeSettings.size,SnakeSettings.size,this);
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void run() {
		while(true){
			snake.move();
            try {
                Thread.sleep(SnakeSettings.default_speed);
            } catch (Exception e) {
                e.printStackTrace();
            }
            repaint();
            if (snake.isHited) {
				try {
					Thread.sleep(SnakeSettings.HITED_TIME/4);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				snake.isHited = false;
			}
            if (isGameOver||isGameWin) {
				break;
			}
        }
	}

}
