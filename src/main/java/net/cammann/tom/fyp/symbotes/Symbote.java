package net.cammann.tom.fyp.symbotes;

import net.cammann.tom.fyp.commands.ConsumeCommand;
import net.cammann.tom.fyp.commands.DropResourceCommand;
import net.cammann.tom.fyp.commands.ForwardCommand;
import net.cammann.tom.fyp.commands.LifeCommand;
import net.cammann.tom.fyp.commands.MoveTowardsDown;
import net.cammann.tom.fyp.commands.MoveTowardsLeft;
import net.cammann.tom.fyp.commands.MoveTowardsRight;
import net.cammann.tom.fyp.commands.MoveTowardsUp;
import net.cammann.tom.fyp.commands.RandomCommand;
import net.cammann.tom.fyp.commands.SubProgram;
import net.cammann.tom.fyp.commands.TurnLeftCommand;
import net.cammann.tom.fyp.commands.TurnRightCommand;
import net.cammann.tom.fyp.core.ABug;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.BasicBrain;
import net.cammann.tom.fyp.core.Commandable;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.Resource;
import net.cammann.tom.fyp.core.Resource.ResourceType;

import org.jgap.IChromosome;

/**
 * Symbote that lays edible resource
 * 
 * @author TC
 * 
 */
public class Symbote extends ABug {
	
	private ResourceType consumable, droppable;
	
	public Symbote() {
		
	}
	
	public Symbote(IChromosome chrome, EnvironmentMap map, ResourceType c,
			ResourceType d) {
		super(chrome, map);
		this.consumable = c;
		this.droppable = d;
		
	}
	
	public Symbote(int[] genes, EnvironmentMap map, ResourceType c,
			ResourceType d) {
		super(genes, map);
		this.consumable = c;
		this.droppable = d;
		
	}
	
	@Override
	public boolean dropResource() {
		if (getMap().hasResource(getPosition())) {
			return false;
		}
		getMap().addResource(new SymbResource(getPosition(), droppable));
		decrementEnegery(70);
		return true;
	}
	
	public Symbote(int genes[], EnvironmentMap map) {
		super(genes, map);
		
	}
	
	@Override
	public boolean canConsumeResource(Resource r) {
		if (r.getResourceType() == consumable) {
			return true;
		}
		return false;
	}
	
	@Override
	public ALife clone() {
		return new Symbote(genes, map, consumable, droppable);
	}
	
	@Override
	public boolean pickUpResource() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void initBrain() {
		setBrain(new BasicBrain(this));
		
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
				new DropResourceCommand(),
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
						new MoveTowardsUp(), new MoveTowardsDown()),
				new SubProgram("Forward and Drop", new DropResourceCommand(),
						new ForwardCommand()),
				new SubProgram("Random and Drop", new RandomCommand(
						"Turn Left or Right", new TurnLeftCommand(),
						new TurnRightCommand())),
				new SubProgram("Eat and Random", new ConsumeCommand(),
						new RandomCommand("Turn Left or Right",
								new TurnLeftCommand(), new TurnRightCommand(),
								new ForwardCommand())) };
		
		return commands;
	}
	
	@Override
	public boolean consume() {
		return consumeResource(map.getResource(getPosition()));
	}
	
}