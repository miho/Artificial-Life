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
 * 
 */
public final class Consume extends CommandGene {
	
	/**
	 * 
	 * @param conf
	 *            jgap config
	 * @param returnType
	 *            return type from gp
	 * @throws InvalidConfigurationException
	 *             bad config
	 */
	public Consume(final GPConfiguration conf, final Class<?> returnType)
			throws InvalidConfigurationException {
		super(conf, 0, returnType);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Cosume";
	}
	
	// CHECKSTYLE.OFF: MethodName
	@Override
	public boolean execute_boolean(final ProgramChromosome c, final int n,
			final Object[] args) {
		
		return ((Commandable) args[0]).consume();
	}
	
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
