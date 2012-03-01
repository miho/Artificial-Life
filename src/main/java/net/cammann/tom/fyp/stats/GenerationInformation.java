package net.cammann.tom.fyp.stats;

import net.cammann.tom.fyp.utils.BucketList;
import net.cammann.tom.fyp.utils.EasyUtils;

import org.jgap.IChromosome;
import org.jgap.Population;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.impl.GPPopulation;

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
	
	public GenerationInformation(Population pop, String id, int genNum) {
		this.id = id;
		this.genNum = genNum;
		
		pop.sortByFitness();
		
		maxFitness = pop.getChromosome(0).getFitnessValue();
		minFitness = pop.getChromosome((pop.size() - 1)).getFitnessValue();
		
		fitnessBucket = new BucketList<Double>();
		
		bestGene = EasyUtils.getChromosoneArray(pop.getChromosome(0));
		
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
	
	public GenerationInformation(GPPopulation pop, String id, int genNum) {
		this.id = id;
		this.genNum = genNum;
		isGeneticProgram = true;
		pop.sortByFitness();
		
		maxFitness = pop.getGPProgram(0).getFitnessValue();
		minFitness = pop.getGPProgram((pop.size() - 1)).getFitnessValue();
		
		fitnessBucket = new BucketList<Double>();
		
		for (Object i : pop.getGPPrograms()) {
			fitnessBucket.add(((IGPProgram) i).getFitnessValue());
		}
		
		double total = 0;
		sizeOfPop = pop.size();
		
		for (Double i : fitnessBucket) {
			int count = fitnessBucket.getCount(i);
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
	
	public int[] getBestGene() {
		return bestGene;
	}
	
	public int getGenNum() {
		return genNum;
	}
	
	public boolean isGeneticProgram() {
		return isGeneticProgram;
	}
	
	public boolean isGeneticAlgorithm() {
		return !isGeneticProgram;
	}
}
