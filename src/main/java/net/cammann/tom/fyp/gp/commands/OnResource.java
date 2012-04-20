package net.cammann.tom.fyp.gp.commands;

import net.cammann.tom.fyp.core.Commandable;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

/**
 * <p>OnResource class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public final class OnResource extends CommandGene {
	
	/**
	 * <p>Constructor for OnResource.</p>
	 *
	 * @param conf a {@link org.jgap.gp.impl.GPConfiguration} object.
	 * @param returnType a {@link java.lang.Class} object.
	 * @throws org.jgap.InvalidConfigurationException if any.
	 */
	public OnResource(final GPConfiguration conf, final Class<?> returnType)
			throws InvalidConfigurationException {
		super(conf, 0, returnType);
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "IsOnResource";
	}
	
	/** {@inheritDoc} */
	@Override
	public double execute_double(final ProgramChromosome c, final int n,
			final Object[] args) {
		final Commandable life = (Commandable) args[0];
		
		if (life.getMap().hasResource(life.getPosition())) {
			return 1;
		}
		return 0;
		
	}
}
