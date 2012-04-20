package net.cammann.tom.fyp.stats;

import net.cammann.tom.fyp.utils.BucketList;
import net.cammann.tom.fyp.utils.EasyUtils;

import org.jgap.IChromosome;
import org.jgap.Population;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.impl.GPPopulation;

/**
 * <p>GenerationInformation class.</p>
 *
 * @author tc
 * @version $Id: $
 */
public class GenerationInformation {
	
	private final double avgFitness;
	private final double minFitness;
	private final double maxFitness;
	private final double sizeOfPop;
	private final String id;
	private boolean isGeneticProgram = false;
	private int[] bestGene;
	private IGPProgram bestGP;
	private final BucketList<Double> fitnessBucket;
	private final int genNum;
	
	/**
	 * <p>Constructor for GenerationInformation.</p>
	 *
	 * @param pop a {@link org.jgap.Population} object.
	 * @param id a {@link java.lang.String} object.
	 * @param genNum a int.
	 */
	public GenerationInformation(final Population pop, final String id,
			final int genNum) {
		this.id = id;
		this.genNum = genNum;
		
		pop.sortByFitness();
		
		maxFitness = pop.getChromosome(0).getFitnessValue();
		minFitness = pop.getChromosome((pop.size() - 1)).getFitnessValue();
		
		fitnessBucket = new BucketList<Double>();
		
		bestGene = EasyUtils.getChromosoneArray(pop.getChromosome(0));
		
		for (final Object i : pop.getChromosomes()) {
			fitnessBucket.add(((IChromosome) i).getFitnessValue());
		}
		
		double total = 0;
		sizeOfPop = pop.size();
		
		for (final Double i : fitnessBucket) {
			final int count = fitnessBucket.getCount(i);
			total += count * i;
		}
		avgFitness = total / sizeOfPop;
		
	}
	
	/**
	 * <p>Constructor for GenerationInformation.</p>
	 *
	 * @param pop a {@link org.jgap.gp.impl.GPPopulation} object.
	 * @param id a {@link java.lang.String} object.
	 * @param genNum a int.
	 */
	public GenerationInformation(final GPPopulation pop, final String id,
			final int genNum) {
		this.id = id;
		this.genNum = genNum;
		isGeneticProgram = true;
		pop.sortByFitness();
		
		maxFitness = pop.getGPProgram(0).getFitnessValue();
		minFitness = pop.getGPProgram((pop.size() - 1)).getFitnessValue();
		
		fitnessBucket = new BucketList<Double>();
		
		for (final Object i : pop.getGPPrograms()) {
			fitnessBucket.add(((IGPProgram) i).getFitnessValue());
		}
		
		double total = 0;
		sizeOfPop = pop.size();
		
		for (final Double i : fitnessBucket) {
			final int count = fitnessBucket.getCount(i);
			total += count * i;
		}
		avgFitness = total / sizeOfPop;
		
	}
	
	// TODO public int[] determineModeGene(Pop pop){
	// ArrayList<BucketList> buckets = ...
	// int[] = new int[gene.length]
	// for(int i = 0 ; i < gene.length; i++){
	// for (IChrome c : pop.getChrom){
	// bucketList.add(c.getGene(i))
	// }
	//
	// }
	
	/**
	 * <p>Getter for the field <code>avgFitness</code>.</p>
	 *
	 * @return a double.
	 */
	public double getAvgFitness() {
		return avgFitness;
	}
	
	/**
	 * <p>Getter for the field <code>minFitness</code>.</p>
	 *
	 * @return a double.
	 */
	public double getMinFitness() {
		return minFitness;
	}
	
	/**
	 * <p>Getter for the field <code>maxFitness</code>.</p>
	 *
	 * @return a double.
	 */
	public double getMaxFitness() {
		return maxFitness;
	}
	
	/**
	 * <p>Getter for the field <code>id</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * <p>Getter for the field <code>fitnessBucket</code>.</p>
	 *
	 * @return a {@link net.cammann.tom.fyp.utils.BucketList} object.
	 */
	public BucketList<Double> getFitnessBucket() {
		return fitnessBucket;
	}
	
	/**
	 * <p>Getter for the field <code>sizeOfPop</code>.</p>
	 *
	 * @return a double.
	 */
	public double getSizeOfPop() {
		return sizeOfPop;
	}
	
	/**
	 * <p>Getter for the field <code>bestGene</code>.</p>
	 *
	 * @return an array of int.
	 */
	public int[] getBestGene() {
		return bestGene;
	}
	
	/**
	 * <p>Getter for the field <code>genNum</code>.</p>
	 *
	 * @return a int.
	 */
	public int getGenNum() {
		return genNum;
	}
	
	/**
	 * <p>isGeneticProgram.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isGeneticProgram() {
		return isGeneticProgram;
	}
	
	/**
	 * <p>isGeneticAlgorithm.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isGeneticAlgorithm() {
		return !isGeneticProgram;
	}
}
