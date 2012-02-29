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

public class MoveTowards extends CommandGene {
	
	public MoveTowards(GPConfiguration a_conf, Class a_returnType)
			throws InvalidConfigurationException {
		super(a_conf, 2, a_returnType);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "MoveTo(&1,&2)";
	}
	
	@Override
	public double execute_double(ProgramChromosome c, int n, Object[] args) {
		Commandable life = (Commandable) args[0];
		double x = c.execute_double(n, 0, args);
		double y = c.execute_double(n, 1, args);
		
		execute(life, x, y);
		
		return 1;
	}
	
	public void execute(Commandable life, double x, double y) {
		int dx = (int) x - life.getPosition().x;
		int dy = ((int) y) - life.getPosition().y;
		
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
