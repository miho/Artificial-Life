package net.cammann.tom.fyp.utils;

import net.cammann.tom.fyp.gui.LoggingFrame;
import net.cammann.tom.fyp.gui.SimulationFrame;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public class Logger {
	
	private int verbosity = 0;
	private final String className;
	
	public Logger(String className) {
		
		this.className = className;
	}
	
	public void setVerbosity(int verbosity) {
		this.verbosity = verbosity;
	}
	
	public void servere(Object string) {
		if (verbosity > -1) {
			out("SEVERE: " + string);
		}
	}
	
	public void error(Object string) {
		if (verbosity > 0) {
			out("ERROR: " + string);
		}
	}
	
	public void warn(Object string) {
		if (verbosity > 1) {
			out("Warning: " + string);
		}
	}
	
	public void info(Object string) {
		if (verbosity > 2) {
			out("Info: " + string);
		}
	}
	
	public void debug(Object string) {
		if (verbosity > 3) {
			out("Debug: " + string);
		}
	}
	
	public void trace(Object string) {
		if (verbosity > 4) {
			out("Trace: " + string);
		}
	}
	
	public void registerFrame(LoggingFrame out) {
		
	}
	
	private void out(String string) {
		if (SimulationFrame.loggingFrame != null) {
			SimulationFrame.loggingFrame.appendLine(className + " - " + string);
		} else {
			System.out.println(className + " - " + string);
		}
	}
	
	public int getVerbosity() {
		// TODO Auto-generated method stub
		return verbosity;
	}
}
