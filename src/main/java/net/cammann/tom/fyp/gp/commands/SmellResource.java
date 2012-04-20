package net.cammann.tom.fyp.gp.commands;

import java.awt.Point;

import net.cammann.tom.fyp.core.Commandable;
import net.cammann.tom.fyp.core.SimpleMap;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

/**
 * <p>SmellResource class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public class SmellResource extends CommandGene {
	
	/**
	 * <p>Constructor for SmellResource.</p>
	 *
	 * @param conf a {@link org.jgap.gp.impl.GPConfiguration} object.
	 * @param returnType a {@link java.lang.Class} object.
	 * @throws org.jgap.InvalidConfigurationException if any.
	 */
	public SmellResource(final GPConfiguration conf, final Class<?> returnType)
			throws InvalidConfigurationException {
		super(conf, 1, returnType);
		
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "Smell in &1";
	}
	
	/** {@inheritDoc} */
	@Override
	public double execute_double(final ProgramChromosome c, final int n,
			final Object[] args) {
		final Commandable life = (Commandable) args[0];
		final double range = c.execute_double(n, 0, args);
		if (consumableResourcesInRange(life, range)) {
			return 1;
		}
		return 0;
		
	}
	
	private boolean consumableResourcesInRange(final Commandable life,
			final double range) {
		
		final int x = life.getX();
		final int y = life.getY();
		
		for (int i = (int) -(range / 2); i < (range / 2); i++) {
			for (int j = (int) -(range / 2); j < (range / 2); j++) {
				
				if (life.getMap().hasResource(
						new Point(x + i * SimpleMap.STEP_SIZE, y + i
								* SimpleMap.STEP_SIZE))) {
					
					return true;
				}
			}
		}
		return false;
	}
}
