package net.cammann.tom.fyp.gp.commands;

import net.cammann.tom.fyp.core.Commandable;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

public class Consume extends CommandGene {
	
	public Consume(GPConfiguration a_conf, Class a_returnType)
			throws InvalidConfigurationException {
		super(a_conf, 0, a_returnType);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Cosume";
	}
	
	@Override
	public boolean execute_boolean(ProgramChromosome c, int n, Object[] args) {
		return ((Commandable) args[0]).consume();
	}
	
	@Override
	public double execute_double(ProgramChromosome c, int n, Object[] args) {
		if (((Commandable) args[0]).consume()) {
			return 1;
		} else {
			return 0;
		}
	}
}
