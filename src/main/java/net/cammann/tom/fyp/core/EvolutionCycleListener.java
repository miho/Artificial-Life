package net.cammann.tom.fyp.core;

/**
 * <p>EvolutionCycleListener interface.</p>
 *
 * @author tc
 * @version $Id: $
 */
public interface EvolutionCycleListener {
	
	/**
	 * <p>startCycle.</p>
	 *
	 * @param e a {@link net.cammann.tom.fyp.core.EvolutionCycleEvent} object.
	 */
	public void startCycle(EvolutionCycleEvent e);
	
	/**
	 * <p>endCycle.</p>
	 *
	 * @param e a {@link net.cammann.tom.fyp.core.EvolutionCycleEvent} object.
	 */
	public void endCycle(EvolutionCycleEvent e);
	
}
