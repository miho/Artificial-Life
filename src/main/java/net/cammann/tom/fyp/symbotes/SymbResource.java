package net.cammann.tom.fyp.symbotes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import net.cammann.tom.fyp.core.SimpleResource;

/**
 * <p>SymbResource class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public final class SymbResource extends SimpleResource {
	
	/**
	 * <p>Constructor for SymbResource.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 * @param r a ResourceType object.
	 */
	public SymbResource(final int x, final int y, final ResourceType r) {
		super(x, y);
		type = r;
		setCalories(90);
		img = null;
	}
	
	/**
	 * <p>Constructor for SymbResource.</p>
	 *
	 * @param p a {@link java.awt.Point} object.
	 * @param r a ResourceType object.
	 */
	public SymbResource(final Point p, final ResourceType r) {
		super(p);
		type = r;
		setCalories(80);
	}
	
	/** {@inheritDoc} */
	@Override
	public ResourceType getResourceType() {
		return type;
	}
	
	/**
	 * <p>getImage.</p>
	 *
	 * @return a {@link java.awt.Image} object.
	 */
	protected Image getImage() {
		if (img == null) {
			
			final BufferedImage b2 = new BufferedImage(10, 10,
					BufferedImage.TYPE_INT_ARGB);
			
			final Graphics2D g2 = b2.createGraphics();
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
	
}
