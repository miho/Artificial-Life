package net.cammann.tom.fyp.symbotes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import net.cammann.tom.fyp.core.Resource.ResourceType;
import net.cammann.tom.fyp.core.SimpleResource;

public class SymbResource extends SimpleResource {
	
	public SymbResource(int x, int y, ResourceType r) {
		super(x, y);
		type = r;
		setCalories(90);
		img = null;
		// TODO Auto-generated constructor stub
	}
	
	public SymbResource(Point p, ResourceType r) {
		super(p);
		type = r;
		setCalories(80);
	}
	
	@Override
	public ResourceType getResourceType() {
		return type;
	}
	
	private Image getResourceImage() {
		if (img == null) {
			
			BufferedImage b2 = new BufferedImage(10, 10,
					BufferedImage.TYPE_INT_ARGB);
			
			Graphics2D g2 = b2.createGraphics();
			if (type == ResourceType.S1) {
				g2.setColor(Color.RED);
			} else if (type == ResourceType.S2) {
				g2.setColor(Color.BLUE);
			} else {
				throw new IllegalArgumentException(
						"Resource Type not supported");
			}
			
			g2.fill(new Ellipse2D.Double(0, 0, 10, 10));
			
			img = b2;
			return img;
		} else {
			return img;
		}
		
	}
	
	@Override
	public Image getImage() {
		
		return getResourceImage();
	}
	
}
