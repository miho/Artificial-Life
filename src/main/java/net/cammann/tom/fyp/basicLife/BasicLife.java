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
import net.cammann.tom.fyp.core.ABug;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.BasicBrain;
import net.cammann.tom.fyp.core.Commandable;
import net.cammann.tom.fyp.core.EnvironmentMap;

import org.jgap.IChromosome;

public class BasicLife extends ABug {
	
	public BasicLife(IChromosome chrome, EnvironmentMap map) {
		super(chrome, map);
		// TODO Auto-generated constructor stub
	}
	
	public BasicLife(int[] genes, EnvironmentMap map) {
		super(genes, map);
	}
	
	public BasicLife() {
		// null instance
	}
	
	@Override
	public ALife clone() {
		return new BasicLife(genes, map);
	}
	
	@Override
	public LifeCommand[] getCommandList() {
		
		LifeCommand doNothing = new LifeCommand("Nothing") {
			@Override
			public void execute(Commandable life) {
				
			}
		};
		
		LifeCommand commands[] = {
				doNothing,
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
	public boolean consume() {
		return consumeResource(map.getResource(getPosition()));
	}
	
}
