package net.cammann.tom.fyp.gp.commands;

import net.cammann.tom.fyp.core.Commandable;

import org.apache.log4j.Logger;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

/**
 * <p>MoveForward class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public final class MoveForward extends CommandGene {
	
	
	/**
	 * Logger.
	 */
	private static final Logger logger = Logger.getLogger(MoveForward.class);
	/**
	 * <p>Constructor for MoveForward.</p>
	 *
	 * @param conf a {@link org.jgap.gp.impl.GPConfiguration} object.
	 * @param returnType a {@link java.lang.Class} object.
	 * @throws org.jgap.InvalidConfigurationException if any.
	 */
	public MoveForward(final GPConfiguration conf, final Class<?> returnType)
			throws InvalidConfigurationException {
		super(conf, 1, returnType);
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "MoveForward &1";
	}
	
	/** {@inheritDoc} */
	@Override
	public void execute_void(final ProgramChromosome c, final int n,
			final Object[] args) {
		final Commandable life = (Commandable) args[0];
		
		final double x = c.execute_double(n, 0, args);
		
		
		for ( int i = 0 ; i < x ; i++ ) {
			life.moveForward();
		
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public double execute_double(final ProgramChromosome c, final int n,
			final Object[] args) {
		final Commandable life = (Commandable) args[0];
		final double x = c.execute_double(n, 0, args);
//		logger.info("From: "+life.getPosition());
		for ( int i = 0 ; i < x ; i++ ) {
			life.moveForward();
//			logger.info("Moved: "+life.getPosition());
			life.consume();
		}
		return 1;
	}
}
