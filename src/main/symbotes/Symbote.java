package symbotes;

import org.jgap.IChromosome;

import commands.ConsumeCommand;
import commands.DropResourceCommand;
import commands.ForwardCommand;
import commands.LifeCommand;
import commands.MoveTowardsDown;
import commands.MoveTowardsLeft;
import commands.MoveTowardsRight;
import commands.MoveTowardsUp;
import commands.RandomCommand;
import commands.SubProgram;
import commands.TurnLeftCommand;
import commands.TurnRightCommand;

import core.ABug;
import core.ALife;
import core.BasicBrain;
import core.Commandable;
import core.EnvironmentMap;
import core.Resource;
import core.Resource.ResourceType;

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
