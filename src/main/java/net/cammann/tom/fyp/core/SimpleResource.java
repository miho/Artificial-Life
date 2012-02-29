package net.cammann.tom.fyp.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

import net.cammann.tom.fyp.utils.Logger;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public class SimpleResource implements Resource {
	
	protected int x;
	protected int y;
	protected int calories;
	protected Image img = null;
	protected final int foodChainValue = 0;
	protected final boolean isCarried;
	
	public int MAX_CALORIES = 200;
	public int MIN_CALORIES = 100;
	protected ResourceType type;
	public static Logger log = new Logger("Apple");
	
	public SimpleResource(Point p) {
		this(p.x, p.y);
	}
	
	public SimpleResource(int x, int y) {
		this.x = x;
		this.y = y;
		calories = new Random().nextInt(MAX_CALORIES - MIN_CALORIES)
				+ MIN_CALORIES;
		isCarried = false;
	}
	
	public SimpleResource(int x, int y, int min_cals, int max_cals) {
		this.x = x;
		this.y = y;
		this.MAX_CALORIES = max_cals;
		this.MIN_CALORIES = min_cals;
		calories = new Random().nextInt(MAX_CALORIES - MIN_CALORIES)
				+ MIN_CALORIES;
		isCarried = false;
	}
	
	@Override
	public Point getPosition() {
		return new Point(x, y);
	}
	
	@Override
	public void setCalories(int calories) {
		this.calories = calories;
	}
	
	@Override
	public int getCalories() {
		return calories;
	}
	
	@Override
	public void setX(int x) {
		this.x = x;
		
	}
	
	@Override
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public void setPosition(Point p) {
		this.y = p.y;
		this.x = p.x;
		
	}
	
	@Override
	public ResourceType getResourceType() {
		return ResourceType.APPLE;
	}
	
	@Override
	public int getX() {
		return x;
	}
	
	@Override
	public int getY() {
		return y;
	}
	
	@Override
	public Image getImage() {
		
		return getResourceImage();
	}
	
	private Image getResourceImage() {
		if (img == null) {
			try {
				
				URL url = this.getClass().getResource("/redApple.png");
				
				BufferedImage bi = ImageIO.read(url);
				
				img = bi.getScaledInstance(15, 15, 0);
				
				return img;
			} catch (Exception e) {
				
				log.servere("Unable to locate Apple Image");
				
			}
		} else {
			return img;
		}
		
		BufferedImage b2 = new BufferedImage(10, 10,
				BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2 = b2.createGraphics();
		g2.setColor(Color.RED);
		g2.fill(new Ellipse2D.Double(0, 0, 10, 10));
		
		img = b2;
		return img;
		
	}
	
	@Override
	public boolean isConsumable() {
		return true;
	}
	
	@Override
	public int foodChainValue() {
		return foodChainValue;
	}
	
	@Override
	public boolean canBeCarried() {
		return true;
	}
	
	@Override
	public boolean isCarried() {
		return isCarried;
	}
}
