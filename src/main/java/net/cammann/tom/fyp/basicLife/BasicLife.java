package net.cammann.tom.fyp.basicLife;

import net.cammann.tom.fyp.commands.ConsumeCommand;
import net.cammann.tom.fyp.commands.ForwardCommand;
import net.cammann.tom.fyp.commands.LifeCommand;
import net.cammann.tom.fyp.commands.MoveTowardsDown;
import net.cammann.tom.fyp.commands.MoveTowardsLeft;
import net.cammann.tom.fyp.commands.MoveTowardsRight;
import net.cammann.tom.fyp.commands.MoveTowardsUp;
import net.cammann.tom.fyp.commands.RandomCommand;
import net.cammann.tom.fyp.commands.TurnLeftCommand;
import net.cammann.tom.fyp.commands.TurnRightCommand;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.AbstactLife;
import net.cammann.tom.fyp.core.Commandable;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.GENE_TYPE;
import net.cammann.tom.fyp.core.Resource;

import org.jgap.IChromosome;

/**
 * Life form that uses basic brain as thinking module.
 * 
 * @author TC
 * 
 */
public final class BasicLife extends AbstactLife {
	
	public BasicLife(final IChromosome chrome, final EnvironmentMap map) {
		super(chrome, map);
	}
	
	public BasicLife(final int[] genes, final EnvironmentMap map) {
		super(genes, map);
	}
	
	public BasicLife() {
		// null instance
		//
	}
	
	@Override
	public ALife clone() {
		return new BasicLife(genes, map);
	}
	
	@Override
	public LifeCommand[] getCommandList() {
		
		final LifeCommand doNothing = new LifeCommand("Nothing") {
			
			@Override
			public void execute(final Commandable life) {
				
			}
		};
		
		final LifeCommand[] commands = {
				new ConsumeCommand(),
				new ForwardCommand(),
				new TurnLeftCommand(),
				new TurnRightCommand(),
				
				new MoveTowardsUp(),
				new MoveTowardsRight(),
				new MoveTowardsDown(),
				new RandomCommand("Turn Left or Right", new TurnLeftCommand(),
						new TurnRightCommand()),
				new RandomCommand("Any Turn Toward", new MoveTowardsUp(),
						new MoveTowardsDown(), new MoveTowardsLeft(),
						new MoveTowardsRight()),
				new RandomCommand("Not up", new MoveTowardsDown(),
						new MoveTowardsRight(), new MoveTowardsLeft()),
				new RandomCommand("Not Right", new MoveTowardsDown(),
						new MoveTowardsLeft(), new MoveTowardsUp()),
				new RandomCommand("Not down", new MoveTowardsUp(),
						new MoveTowardsLeft(), new MoveTowardsRight()),
				new RandomCommand("Not Left", new MoveTowardsUp(),
						new MoveTowardsDown(), new MoveTowardsRight()),
				new RandomCommand("Not Up and Not Right",
						new MoveTowardsDown(), new MoveTowardsLeft()),
				new RandomCommand("Not Up and Not Down", new MoveTowardsLeft(),
						new MoveTowardsUp()),
				new RandomCommand("Not Up and Not Left", new MoveTowardsDown(),
						new MoveTowardsRight()),
				new RandomCommand("Not Down and Not Right",
						new MoveTowardsLeft(), new MoveTowardsUp()),
				new RandomCommand("Not Down and Not Left",
						new MoveTowardsRight(), new MoveTowardsUp()),
				new RandomCommand("Not Left and Not Right",
						new MoveTowardsUp(), new MoveTowardsDown()) };
		
		return commands;
	}
	
	@Override
	public void initBrain() {
		setBrain(new BasicBrain(this));
		
	}
	
	@Override
	public int getMemoryLength() {
		return getGene(GENE_TYPE.MEMORY_LENGTH);
	}
	
	@Override
	public boolean dropResource() {
		return false;
	}
	
	@Override
	public boolean canConsumeResource(final Resource r) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	protected int getStartEnergy() {
		return getGene(0);
	}
}
