package net.cammann.tom.fyp.stats;

import net.cammann.tom.fyp.utils.BucketList;

import org.jgap.IChromosome;
import org.jgap.Population;

public class PopStat {
	
	private final double avgFitness;
	private final double minFitness;
	private final double maxFitness;
	private final double sizeOfPop;
	private final String id;
	private final BucketList<Double> fitnessBucket;
	
	public PopStat(Population pop, String id) {
		this.id = id;
		
		pop.sortByFitness();
		
		maxFitness = pop.getChromosome(0).getFitnessValue();
		minFitness = pop.getChromosome((pop.size() - 1)).getFitnessValue();
		
		fitnessBucket = new BucketList<Double>();
		
		for (Object i : pop.getChromosomes()) {
			fitnessBucket.add(((IChromosome) i).getFitnessValue());
		}
		
		double total = 0;
		sizeOfPop = pop.size();
		
		for (Double i : fitnessBucket) {
			int count = fitnessBucket.getCount(i);
			total += count * i;
		}
		avgFitness = total / sizeOfPop;
		
	}
	
	public double getAvgFitness() {
		return avgFitness;
	}
	
	public double getMinFitness() {
		return minFitness;
	}
	
	public double getMaxFitness() {
		return maxFitness;
	}
	
	public String getId() {
		return id;
	}
	
	public BucketList<Double> getFitnessBucket() {
		return fitnessBucket;
	}
	
	public double getSizeOfPop() {
		return sizeOfPop;
	}
	
}
