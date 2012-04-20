package net.cammann.tom.fyp.gp.commands;

import net.cammann.tom.fyp.core.Commandable;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

/**
 * <p>Orientation class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public final class Orientation extends CommandGene {
	
	/**
	 * <p>Constructor for Orientation.</p>
	 *
	 * @param conf a {@link org.jgap.gp.impl.GPConfiguration} object.
	 * @param returnType a {@link java.lang.Class} object.
	 * @throws org.jgap.InvalidConfigurationException if any.
	 */
	public Orientation(final GPConfiguration conf, final Class<?> returnType)
			throws InvalidConfigurationException {
		super(conf, 0, returnType);
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "orientation";
	}
	
	/** {@inheritDoc} */
	@Override
	public double execute_double(final ProgramChromosome c, final int n,
			final Object[] args) {
		final Commandable life = (Commandable) args[0];
		return life.getOrientation().ordinal();
		
	}
}
