package net.cammann.tom.fyp.gp.commands;

import net.cammann.tom.fyp.core.Commandable;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

/**
 * Consume resource command.
 *
 * @author TC
 * @version $Id: $
 */
public final class Consume extends CommandGene {
	
	/**
	 * <p>Constructor for Consume.</p>
	 *
	 * @param conf
	 *            jgap config
	 * @param returnType
	 *            return type from gp
	 * @throws org.jgap.InvalidConfigurationException
	 *             bad config
	 */
	public Consume(final GPConfiguration conf, final Class<?> returnType)
			throws InvalidConfigurationException {
		super(conf, 0, returnType);
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "Cosume";
	}
	
	// CHECKSTYLE.OFF: MethodName
	/** {@inheritDoc} */
	@Override
	public boolean execute_boolean(final ProgramChromosome c, final int n,
			final Object[] args) {
		
		return ((Commandable) args[0]).consume();
	}
	
	/** {@inheritDoc} */
	@Override
	public double execute_double(final ProgramChromosome c, final int n,
			final Object[] args) {
		if (((Commandable) args[0]).consume()) {
			return 1;
		} else {
			return 0;
		}
	}
	// CHECKSTYLE.ON: MethodName
}
