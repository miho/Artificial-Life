package net.cammann.tom.fyp.symbotes;

import net.cammann.tom.fyp.basicLife.BasicFitnessFunction;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.AbstractEvolutionFactory;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.Resource.ResourceType;
import net.cammann.tom.fyp.core.SimpleFitnessFunction;

import org.jgap.IChromosome;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

/**
 * <p>
 * SymboteFactory class.
 * </p>
 * 
 * @author tc
 * @version $Id: $
 */
public class SymboteFactory extends AbstractEvolutionFactory {

	private final ResourceType c, d;

	/**
	 * <p>
	 * Constructor for SymboteFactory.
	 * </p>
	 * 
	 * @param c
	 *            a {@link net.cammann.tom.fyp.core.Resource.ResourceType}
	 *            object.
	 * @param d
	 *            a {@link net.cammann.tom.fyp.core.Resource.ResourceType}
	 *            object.
	 */
	public SymboteFactory(final ResourceType c, final ResourceType d) {
		this.c = c;
		this.d = d;
		fit_func_run_len = 400;
		num_fit_func_cycles = 2;

	}

	/**
	 * <p>
	 * Constructor for SymboteFactory.
	 * </p>
	 */
	public SymboteFactory() {
		this.c = ResourceType.S1;
		this.d = ResourceType.S2;
	}

	/** {@inheritDoc} */
	@Override
	public ALife createLife(final IChromosome chromo, final EnvironmentMap map) {
		return new Symbote(chromo, map, c, d);
	}

	/** {@inheritDoc} */
	@Override
	public ALife createLife(final int[] genes, final EnvironmentMap map) {
		return new Symbote(genes, map, c, d);
	}

	/** {@inheritDoc} */
	@Override
	public SimpleFitnessFunction getFitnessFunction() {
		return new BasicFitnessFunction(this);
	}

	/** {@inheritDoc} */
	@Override
	public ALife createLife(final IGPProgram gp, final EnvironmentMap map) {

		return null;
	}

	/** {@inheritDoc} */
	@Override
	public EnvironmentMap createMap() {
		return new SymboticMap(400, 400);
	}

	/** {@inheritDoc} */
	@Override
	public GPFitnessFunction getGPFitnessFunction() {
		// TODO Add gp fit func in?
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public ALife createLife(final ALife life, final EnvironmentMap map) {
		final ALife second_life = (ALife) life.clone();
		// TODO NOT FIXED
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public ALife nullInstance() {
		return new Symbote();
	}

}
