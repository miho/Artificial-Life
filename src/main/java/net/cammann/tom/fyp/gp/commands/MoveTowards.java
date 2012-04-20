package net.cammann.tom.fyp.gp.commands;

import net.cammann.tom.fyp.commands.MoveTowardsDown;
import net.cammann.tom.fyp.commands.MoveTowardsLeft;
import net.cammann.tom.fyp.commands.MoveTowardsRight;
import net.cammann.tom.fyp.commands.MoveTowardsUp;
import net.cammann.tom.fyp.core.Commandable;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

/**
 * <p>MoveTowards class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public final class MoveTowards extends CommandGene {
	
	/**
	 * <p>Constructor for MoveTowards.</p>
	 *
	 * @param conf a {@link org.jgap.gp.impl.GPConfiguration} object.
	 * @param returnType a {@link java.lang.Class} object.
	 * @throws org.jgap.InvalidConfigurationException if any.
	 */
	public MoveTowards(final GPConfiguration conf, final Class<?> returnType)
			throws InvalidConfigurationException {
		super(conf, 2, returnType);
		
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "MoveTo(&1,&2)";
	}
	
	/** {@inheritDoc} */
	@Override
	public double execute_double(final ProgramChromosome c, final int n,
			final Object[] args) {
		final Commandable life = (Commandable) args[0];
		final double x = c.execute_double(n, 0, args);
		final double y = c.execute_double(n, 1, args);
		
		execute(life, x, y);
		
		return 1;
	}
	
	/**
	 * <p>execute.</p>
	 *
	 * @param life a {@link net.cammann.tom.fyp.core.Commandable} object.
	 * @param x a double.
	 * @param y a double.
	 */
	public void execute(final Commandable life, final double x, final double y) {
		final int dx = (int) x - life.getPosition().x;
		final int dy = ((int) y) - life.getPosition().y;
		
		if (dx == 0 && dy == 0) {
			return;
		} else if (dx == 0) {
			if (dy < 0) {
				new MoveTowardsUp().execute(life);
			} else {
				new MoveTowardsDown().execute(life);
			}
			
		} else if (dy == 0) {
			if (dx < 0) {
				new MoveTowardsLeft().execute(life);
			} else {
				new MoveTowardsRight().execute(life);
			}
		} else {
			if (dy > dx) {
				if (dy < 0) {
					new MoveTowardsUp().execute(life);
				} else {
					new MoveTowardsDown().execute(life);
				}
			} else {
				if (dx < 0) {
					new MoveTowardsLeft().execute(life);
				} else {
					new MoveTowardsRight().execute(life);
				}
			}
		}
		
	}
	
}
