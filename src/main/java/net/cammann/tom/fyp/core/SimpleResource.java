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

import org.apache.log4j.Logger;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public class SimpleResource extends Resource {
	
	Logger logger = Logger.getLogger(SimpleResource.class);
	
	protected int calories;
	protected Image img = null;
	protected final int foodChainValue = 0;
	protected final boolean isCarried;
	
	public int MAX_CALORIES = 200;
	public int MIN_CALORIES = 100;
	protected ResourceType type;
	
	public static Logger log = Logger.getLogger(Resource.class);
	
	public SimpleResource(final Point p) {
		this(p.x, p.y);
	}
	
	public SimpleResource(final int x, final int y) {
		setPosition(x, y);
		
		calories = new Random().nextInt(MAX_CALORIES - MIN_CALORIES)
				+ MIN_CALORIES;
		isCarried = false;
	}
	
	public SimpleResource(final int x, final int y, final int min_cals,
			final int max_cals) {
		
		setPosition(x, y);
		
		this.MAX_CALORIES = max_cals;
		this.MIN_CALORIES = min_cals;
		calories = new Random().nextInt(MAX_CALORIES - MIN_CALORIES)
				+ MIN_CALORIES;
		isCarried = false;
	}
	
	@Override
	public void setCalories(final int calories) {
		this.calories = calories;
	}
	
	@Override
	public double getCalories() {
		return calories;
	}
	
	@Override
	public ResourceType getResourceType() {
		return ResourceType.BASIC;
	}
	
	private Image getImage() {
		
		return getResourceImage();
	}
	
	private Image getResourceImage() {
		if (img == null) {
			try {
				
				final URL url = this.getClass().getResource("/redApple.png");
				
				final BufferedImage bi = ImageIO.read(url);
				
				img = bi.getScaledInstance(15, 15, 0);
				
				return img;
			} catch (final Exception e) {
				
				logger.error("Unable to locate Apple Image");
				
			}
		} else {
			return img;
		}
		
		final BufferedImage b2 = new BufferedImage(10, 10,
				BufferedImage.TYPE_INT_ARGB);
		
		final Graphics2D g2 = b2.createGraphics();
		g2.setColor(Color.RED);
		g2.fill(new Ellipse2D.Double(0, 0, 10, 10));
		
		img = b2;
		return img;
		
	}
	
	@Override
	public boolean canBeCarried() {
		return true;
	}
	
	@Override
	public boolean isCarried() {
		return isCarried;
	}
	
	@Override
	public void draw(final Graphics2D g2) {
		g2.drawImage(getImage(), getX(), getY(), null);
		
	}
	
}
