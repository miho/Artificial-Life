package net.cammann.tom.fyp.gp.commands;

import net.cammann.tom.fyp.core.Commandable;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

/**
 * <p>TurnLeft class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public final class TurnLeft extends CommandGene {
	
	/**
	 * <p>Constructor for TurnLeft.</p>
	 *
	 * @param conf a {@link org.jgap.gp.impl.GPConfiguration} object.
	 * @param returnType a {@link java.lang.Class} object.
	 * @throws org.jgap.InvalidConfigurationException if any.
	 */
	public TurnLeft(final GPConfiguration conf, final Class<?> returnType)
			throws InvalidConfigurationException {
		super(conf, 0, returnType);
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "TurnLeft";
	}
	
	/** {@inheritDoc} */
	@Override
	public void execute_void(final ProgramChromosome c, final int n,
			final Object[] args) {
		final Commandable life = (Commandable) args[0];
		life.turnLeft();
	}
	
	/** {@inheritDoc} */
	@Override
	public double execute_double(final ProgramChromosome c, final int n,
			final Object[] args) {
		final Commandable life = (Commandable) args[0];
		life.turnLeft();
		return 1;
	}
}
